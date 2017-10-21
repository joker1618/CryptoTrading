package com.fede.app.crypto.trading.config;

import java.nio.file.Path;
import java.util.logging.Level;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface ICryptoConfig {

	// Configs
	boolean isLogsEnabled();

	// Api token
	String getKrakenApiKey();
	String getKrakenApiSecret();

	// Run parameters
	int getCallSecondsRateAssets();
	int getCallSecondsRateAssetPairs();
	int getCallSecondsRateTickers();
	int getCallSecondsRateSpreadData();

	// DB config
	String getDBUrl();
	String getDBUsername();
	String getDBPassword();

}
