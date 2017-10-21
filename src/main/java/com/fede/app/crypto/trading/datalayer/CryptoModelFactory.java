package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.config.ICryptoConfig;
import com.fede.app.crypto.trading.config.CryptoConfigImpl;
import com.fede.app.crypto.trading.dao.impl.AssetPairsDBDao;
import com.fede.app.crypto.trading.dao.impl.AssetsDBDao;
import com.fede.app.crypto.trading.dao.impl.TickersDBDao;
import com.fede.app.crypto.trading.exception.TechnicalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class CryptoModelFactory {

	public static ICryptoModel getModel() {
		CryptoModelImpl model = new CryptoModelImpl();

		ICryptoConfig config = CryptoConfigImpl.getInstance();
		Connection conn = createDBConnection(config.getDBUrl(), config.getDBUsername(), config.getDBPassword());
		model.setAssetDao(new AssetsDBDao(conn));
		model.setAssetPairsDao(new AssetPairsDBDao(conn));
		model.setTickersDao(new TickersDBDao(conn));

		return model;
	}

	private static Connection createDBConnection(String dbURL, String dbUser, String dbPwd) {
		try {
			// Load JDBC driver class
			Class.forName(Const.JDBC_DRIVER_CLASS);
			// Create DB connection
			return DriverManager.getConnection(dbURL, dbUser, dbPwd);
		} catch (ClassNotFoundException | SQLException ex) {
			throw new TechnicalException(ex, "Unable to create DB connection");
		}
	}
}
