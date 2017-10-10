package com.fede.app.crypto.trading.config;

import java.nio.file.Path;
import java.util.logging.Level;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface IKrakenConfig {

	// Api token
	String getKrakenApiKey();
	String getKrakenApiSecret();

	// Run parameters
	boolean isDBEnabled();
	int getCallSecondsRateAssets();
	int getCallSecondsRateAssetPairs();
	int getCallSecondsRateTickers();
	int getCallSecondsRateSpreadData();

	// DB config
	String getDBUrl();
	String getDBUsername();
	String getDBPassword();

	// CSV files config
	Path getCsvPathAssets();
	Path getCsvPathAssetPairs();
	Path getCsvPathTickers();
	Path getCsvPathSpreadData();
	Path getCsvPathAccountBalance();

	// Logs config
	Path getLogPathErrors();
	Path getLogPathAll();
	Level getLogLevelConsole();

}
