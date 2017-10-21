package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.ICryptoConfig;
import com.fede.app.crypto.trading.config.CryptoConfigImpl;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class CryptoMain {

	private static final ISimpleLog logger = LogService.getLogger(CryptoMain.class);

	public static void main(String[] args) throws IOException {

		logger.config("\n%s", CryptoConfigImpl.getInstance());

		CryptoEngine engine = new CryptoEngine();
		engine.startKrakenEngine();

	}

}
