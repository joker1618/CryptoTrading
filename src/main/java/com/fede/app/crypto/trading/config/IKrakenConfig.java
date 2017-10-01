package com.fede.app.crypto.trading.config;

import java.nio.file.Path;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface IKrakenConfig {

	// Api token
	String getKrakenApiKey();
	String getKrakenApiSecret();

	// Run parameters
	boolean isDBEnabled();
	boolean isCallMidnightStartAssets();
	boolean isCallMidnightStartAssetPairs();
	int getCallSecondsRateAssets();
	int getCallSecondsRateAssetPairs();
	int getCallSecondsRateTickers();
	int getCallSecondsRateSpreadData();

	// DB config
	String getDBUrl();
	String getDBUsername();
	String getDBPassword();

	// CSV files config
	Path getPathCsvAssets();
	Path getPathCsvAssetPairs();
	Path getPathCsvTickers();
	Path getPathCsvSpreadData();
	Path getPathCsvAccountBalance();


}
