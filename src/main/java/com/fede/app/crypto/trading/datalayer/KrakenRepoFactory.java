package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.config.IKrakenConfig;
import com.fede.app.crypto.trading.config.KrakenConfigImpl;
import com.fede.app.crypto.trading.dao.impl.csv.AssetsCsvDao;
import com.fede.app.crypto.trading.dao.impl.db.AssetsDBDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.kraken.IKrakenCaller;
import com.fede.app.crypto.trading.kraken.KrakenCallerImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class KrakenRepoFactory {

	public static IKrakenRepo getRepo() {
		KrakenRepoImpl krakenRepo = new KrakenRepoImpl();

		IKrakenConfig config = KrakenConfigImpl.getInstance();
		if(config.isDBEnabled()) {
			Connection conn = createDBConnection(config.getDBUrl(), config.getDBUsername(), config.getDBPassword());
			krakenRepo.setAssetDao(new AssetsDBDao(conn));
		} else {
			krakenRepo.setAssetDao(new AssetsCsvDao(config.getCsvPathAssets()));
		}

		IKrakenCaller krakenCaller = new KrakenCallerImpl(config.getKrakenApiKey(), config.getKrakenApiSecret());
		krakenRepo.setKrakenCaller(krakenCaller);

		return krakenRepo;
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
