package com.fede.app.crypto.trading.logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
import com.fede.app.crypto.trading.util.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogFormatter extends Formatter {

	private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String SEP = " - ";

	private boolean simpleStyle;

	private DateTimeFormatter dateTimeFormatter;

	LogFormatter(boolean simpleStyle) {
		this.simpleStyle = simpleStyle;
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);
	}

	@Override
	public String format(LogRecord record) {
		String lineNumber = "";
		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTrace = currentThread.getStackTrace();
		for (int i = 0; i < stackTrace.length && lineNumber.isEmpty(); i++) {
			StackTraceElement elem = stackTrace[i];
			if (elem.getClassName().startsWith(record.getLoggerName())) {
				lineNumber = "(" + elem.getLineNumber() + ")";
			}
		}

		String message = super.formatMessage(record);

		StringBuilder sb = new StringBuilder();

		LocalDateTime ldt = DateUtils.fromMillis(record.getMillis());
		sb.append(dateTimeFormatter.format(ldt)).append(SEP);

		if(!simpleStyle) {
			sb.append(record.getLoggerName()).append(lineNumber).append("\t").append(SEP);

			sb.append("<").append(String.format("%s-%d", currentThread.getName(), currentThread.getId())).append(">");
			sb.append("\t").append(SEP);

			sb.append(String.format("%-6s\t", record.getLevel())).append(SEP);
		}

		sb.append(message).append("\n");

		return sb.toString();
	}

}