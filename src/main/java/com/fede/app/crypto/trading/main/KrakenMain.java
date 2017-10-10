package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.IKrakenConfig;
import com.fede.app.crypto.trading.config.KrakenConfigImpl;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class KrakenMain {

	private static final ISimpleLog logger = LogService.getLogger(KrakenMain.class);

	public static void main(String[] args) throws IOException {

		IKrakenConfig config = KrakenConfigImpl.getInstance();

		LogService.setLogLevel(Level.ALL);
		LogService.enableConsole(config.getLogLevelConsole());
		LogService.addFileHandler(config.getLogPathAll(), Level.ALL);
		LogService.addFileHandler(config.getLogPathErrors(), Level.SEVERE);

		Logger.getLogger("").setLevel(Level.OFF);
		Logger.getLogger("com.fede").setLevel(Level.ALL);

		logger.config("\n%s", config);

		KrakenEngine engine = new KrakenEngine();
		engine.startKrakenEngine();

	}

}
