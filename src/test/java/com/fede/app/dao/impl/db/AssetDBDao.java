package com.fede.app.dao.impl.db;

import com.fede.app.dao.IAssetDao;

import java.sql.Connection;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class AssetDBDao extends AbstractDBDao implements IAssetDao {

	private static final String Q_INSERT = "INSERT INTO ASSETS (ASSET_NAME, A_CLASS, ALT_NAME, DECIMALS, DISPLAY_DECIMALS, START_TIME, EXPIRE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";

	public AssetDBDao(Connection connection) {
		super(connection);
	}
}
