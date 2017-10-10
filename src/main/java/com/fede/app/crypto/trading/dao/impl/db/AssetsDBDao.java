package com.fede.app.crypto.trading.dao.impl.db;

import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.Asset;

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

	private static final String Q_GET_VALIDS = "SELECT * FROM ASSETS WHERE EXPIRE_TIME = 0";
	private static final String Q_UPDATE_EXPIRE_TIME = "UPDATE ASSETS SET EXPIRE_TIME = ? WHERE EXPIRE_TIME = 0";
	private static final String Q_INSERT_NEW = "INSERT INTO ASSETS (ASSET_NAME, A_CLASS, ALT_NAME, DECIMALS, DISPLAY_DECIMALS, START_TIME, EXPIRE_TIME) VALUES @ASSET_LIST@";

	private static final String PH_ASSET_LIST = "@ASSET_LIST@";

	public AssetsDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public List<Asset> getAssets() {
		try (PreparedStatement ps = connection.prepareStatement(Q_GET_VALIDS);
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
	public void persistNewAssets(Long callTime, List<Asset> newAssets) {
		List<Asset> actualAssets = getAssets();
		if(!actualAssets.equals(newAssets)) {
			logger.info("New assets downloaded!");

			PreparedStatement ps = null;

			try {
				ps = connection.prepareStatement(Q_UPDATE_EXPIRE_TIME);
				ps.setLong(1, callTime);
				int numUpdated = ps.executeUpdate();
				ps.close();
				if(numUpdated > 0) {
					logger.info("%d assets updated: expire time changed from 0 to %d", numUpdated, callTime);
				}

				StringBuilder sb = new StringBuilder();
				for (Asset asset : newAssets) {
					if (sb.length() > 0) sb.append(",");
					sb.append(assetToValues(asset, callTime));
				}
				String query = Q_INSERT_NEW.replace(PH_ASSET_LIST, sb.toString());
				ps = connection.prepareStatement(query);
				int numInsert = ps.executeUpdate();
				logger.info("%d new assets inserted in DB", numInsert);

			} catch (SQLException e) {
				logger.error(e);
				throw new TechnicalException(e);
			} finally {
				try {
					if (ps != null && !ps.isClosed()) 	ps.close();
				} catch (SQLException e) {
					logger.error(e, "Unable to close JDBC statement");
					throw new TechnicalException(e, "Unable to close JDBC statements");
				}
			}

		} else {
			logger.info("Assets downloaded are equals to the assets saved in DB: insert query not performed.");
		}

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
