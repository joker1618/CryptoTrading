package com.fede.app.crypto.trading.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class LogService {

	private static final LogService INSTANCE = new LogService();

	private final Logger rootLogger;
	private final ConsoleHandler consoleHandler;
	private boolean consoleEnabled;
	private Map<Path, FileHandler> fileHandlerMap;


	private LogService() {
		Logger.getLogger("").setLevel(Level.OFF);

		this.rootLogger = Logger.getLogger("com.fede.app.crypto.trading");
		this.rootLogger.setLevel(Level.ALL);
		this.rootLogger.setUseParentHandlers(false);
		Arrays.stream(rootLogger.getHandlers()).forEach(rootLogger::removeHandler);

		this.consoleHandler = new ConsoleHandler();
		this.consoleHandler.setFormatter(new LogFormatter(true));

		this.fileHandlerMap = new HashMap<>();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			fileHandlerMap.values().forEach(FileHandler::close);
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

	public static void setLogLevel(Level level) {
		synchronized (INSTANCE) {
			INSTANCE.rootLogger.setLevel(level);
		}
	}

	public static void enableConsole(Level level) {
		synchronized (INSTANCE) {
			if(!INSTANCE.consoleEnabled) {
				INSTANCE.consoleEnabled = true;
				INSTANCE.rootLogger.addHandler(INSTANCE.consoleHandler);
			}
			INSTANCE.consoleHandler.setLevel(level);
		}
	}

	public static void addFileHandler(Path logFilePath, Level level) throws IOException {
		synchronized (INSTANCE) {
			Files.createDirectories(logFilePath.getParent());
			FileHandler fh = new FileHandler(logFilePath.toString(), true);
			fh.setLevel(level);
			fh.setFormatter(new LogFormatter(false));
			INSTANCE.fileHandlerMap.put(logFilePath, fh);
			INSTANCE.rootLogger.addHandler(fh);
		}
	}

}
