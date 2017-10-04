package com.fede.app.crypto.trading.dao.impl.db;

import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.model._public.Asset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class AssetsDBDao extends AbstractDBDao implements IAssetsDao {

	private static final String Q_GET_VALIDS = "SELECT * FROM ASSETS WHERE EXPIRE_TIME = 0";
//	private static final String Q_INSERT = "INSERT INTO ASSETS (ASSET_NAME, A_CLASS, ALT_NAME, DECIMALS, DISPLAY_DECIMALS, START_TIME, EXPIRE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";

	public AssetsDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public List<Asset> getAssets() {
		try {
			PreparedStatement ps = connection.prepareStatement(Q_GET_VALIDS);
			ResultSet rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
