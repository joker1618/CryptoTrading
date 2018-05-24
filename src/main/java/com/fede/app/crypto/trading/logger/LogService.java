package com.fede.app.crypto.trading.logger;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.config.CryptoConfigImpl;
import com.fede.app.crypto.trading.config.ICryptoConfig;
import com.fede.app.crypto.trading.exception.TechnicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class LogService {

	private static final LogService INSTANCE = new LogService();

	private final ICryptoConfig config = CryptoConfigImpl.getInstance();


	private LogService() {
		Logger.getLogger("").setLevel(Level.OFF);

		Logger rootLogger = Logger.getLogger("com.fede.app.crypto.trading");
		rootLogger.setLevel(Level.ALL);
		rootLogger.setUseParentHandlers(false);
		Arrays.stream(rootLogger.getHandlers()).forEach(rootLogger::removeHandler);

		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter(true));
		consoleHandler.setLevel(Level.ALL);
		rootLogger.addHandler(consoleHandler);

		List<FileHandler> fileHandlers = new ArrayList<>();
		if(config.isLogsEnabled()) {
			FileHandler fhAll = getFileHandler(Const.LOG_ALL_PATH, Level.ALL);
			rootLogger.addHandler(fhAll);
			fileHandlers.add(fhAll);

			FileHandler fhWarn = getFileHandler(Const.LOG_WARN_PATH, Level.WARNING);
			rootLogger.addHandler(fhWarn);
			fileHandlers.add(fhWarn);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			fileHandlers.forEach(FileHandler::close);
			consoleHandler.close();
		}));
	}


	public static ISimpleLog getLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return new SimpleLogImpl(logger);
	}
	public static ISimpleLog getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	private FileHandler getFileHandler(Path logFilePath, Level level) {
		try {
			Files.createDirectories(logFilePath.getParent());
			FileHandler fh = new FileHandler(logFilePath.toString(), true);
			fh.setLevel(level);
			fh.setFormatter(new LogFormatter(false));
			return fh;
		} catch(IOException ex) {
			throw new TechnicalException(ex);
		}
	}

}
