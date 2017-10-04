package com.fede.app.logger;

import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class LoggerTest {

	@Test
	public void testLevels() throws IOException, InterruptedException {
//		LogService.addFileHandler(Paths.get("testLogs/error.log"), Level.SEVERE);
//		LogService.addFileHandler(Paths.get("testLogs/warning.log"), Level.WARNING);
//		LogService.addFileHandler(Paths.get("testLogs/all.log"), Level.ALL);
		LogService.enableConsole(Level.FINE);
		LogService.setLogLevel(Level.FINER);

		ISimpleLog logger = LogService.getLogger(LoggerTest.class);

		ExecutorService es1 = Executors.newFixedThreadPool(2);
		ExecutorService es2 = Executors.newFixedThreadPool(2);
		es1.execute(() -> doLog(logger));
		es2.execute(() -> doLog(logger));
		doLog(logger);
		logger.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		es1.execute(() -> doLog(logger));
		es2.execute(() -> doLog(logger));
		es1.shutdown();
		es2.shutdown();
		while(!es1.isTerminated() || !es2.isTerminated()) {
			Thread.sleep(1000);
		}
	}

	private void doLog(ISimpleLog logger) {
		logger.error("ERROR");
		logger.warning("WARNING");
		logger.info("INFO");
		logger.config("CONFIG");
		logger.fine("FINE");
		logger.finer("FINER");
		logger.finest("FINEST");
	}
}
