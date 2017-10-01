package com.fede.app.crypto.trading.logger;

import java.io.IOException;
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
		this.rootLogger = Logger.getLogger("");
		this.rootLogger.setUseParentHandlers(false);
		Arrays.stream(rootLogger.getHandlers()).forEach(rootLogger::removeHandler);

		this.consoleHandler = new ConsoleHandler();
		this.consoleHandler.setFormatter(new LogFormatter());

		this.fileHandlerMap = new HashMap<>();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			fileHandlerMap.values().forEach(FileHandler::close);
			consoleHandler.close();
		}));
	}


	public static SimpleLog getLogger(String loggerName) {
		synchronized (INSTANCE) {
			Logger logger = Logger.getLogger(loggerName);
			return new SimpleLogImpl(logger);
		}
	}
	public static SimpleLog getLogger(Class<?> clazz) {
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
				INSTANCE.rootLogger.addHandler(INSTANCE.consoleHandler);
				INSTANCE.consoleEnabled = true;
			}
			INSTANCE.consoleHandler.setLevel(level);
		}
	}
	public static void disableConsole(Level level) {
		synchronized (INSTANCE) {
			if(INSTANCE.consoleEnabled) {
				INSTANCE.rootLogger.removeHandler(INSTANCE.consoleHandler);
				INSTANCE.consoleEnabled = false;
			}
		}
	}

	public static void addFileHandler(Path logFilePath, Level level) throws IOException {
		synchronized (INSTANCE) {
			FileHandler fh = INSTANCE.fileHandlerMap.get(logFilePath);
			if(fh == null) {
				fh = new FileHandler(logFilePath.toString(), true);
				fh.setFormatter(new LogFormatter());
				INSTANCE.fileHandlerMap.put(logFilePath, fh);
				INSTANCE.rootLogger.addHandler(fh);
			}
			fh.setLevel(level);
		}
	}

}
