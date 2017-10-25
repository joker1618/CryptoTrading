package com.fede.app.crypto.trading.dao.impl;

import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class AssetsDBDao extends AbstractDBDao implements IAssetsDao {

	private static final ISimpleLog logger = LogService.getLogger(AssetsDBDao.class);

	private static final String Q_GET_VALID_ASSETS = "SELECT ASSET_NAME, A_CLASS, ALT_NAME, DECIMALS, DISPLAY_DECIMALS FROM ASSETS WHERE EXPIRE_TIME = 0 ORDER BY ASSET_NAME";
	private static final String Q_UPDATE_EXPIRE_TIME = "UPDATE ASSETS SET EXPIRE_TIME = ? WHERE EXPIRE_TIME = 0";
	private static final String Q_INSERT_ASSETS = "INSERT INTO ASSETS (ASSET_NAME, A_CLASS, ALT_NAME, DECIMALS, DISPLAY_DECIMALS, START_TIME, EXPIRE_TIME) VALUES @ASSET_LIST@";

	private static final String PH_ASSET_LIST = "@ASSET_LIST@";

	public AssetsDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public List<Asset> getAssets() {
		try (PreparedStatement ps = connection.prepareStatement(Q_GET_VALID_ASSETS);
			 ResultSet rs = ps.executeQuery()){

			List<Asset> assets = new ArrayList<>();
			if(rs != null) {
				while(rs.next()) {
					Asset asset = new Asset();
					asset.setAssetName(rs.getString("ASSET_NAME"));
					asset.setAClass(rs.getString("A_CLASS"));
					asset.setAltName(rs.getString("ALT_NAME"));
					asset.setDecimals(rs.getInt("DECIMALS"));
					asset.setDisplayDecimals(rs.getInt("DISPLAY_DECIMALS"));
					assets.add(asset);
				}
			}
			return assets;

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	@Override
	public void persistAssets(Long callTime, List<Asset> newAssets) {
		// Update expire time of actual valid assets
		int numUpdated = super.performUpdate(Q_UPDATE_EXPIRE_TIME, callTime);
		logger.fine("Updated expire time for %d assets (from 0 to %d)", numUpdated, callTime);
		// Insert new assets
		String strValues = Utils.join(newAssets, ",", a -> assetToValues(a, callTime));
		String query = Q_INSERT_ASSETS.replace(PH_ASSET_LIST, strValues);
		int numInsert = super.performUpdate(query);
		logger.fine("Insert %d new assets (callTime=%d)", numInsert, callTime);
	}

	private String assetToValues(Asset asset, Long callTime) {
		return String.format("('%s', '%s', '%s', %d, %d, %d, 0)",
			asset.getAssetName(),
			asset.getAClass(),
			asset.getAltName(),
			asset.getDecimals(),
			asset.getDisplayDecimals(),
			callTime
		);
	}
}
