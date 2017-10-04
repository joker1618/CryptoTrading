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

	private boolean simpleClassName;
	private boolean showThreadPool = false;		//review

	private DateTimeFormatter dateTimeFormatter;

	LogFormatter(boolean simpleClassName) {
		this.simpleClassName = simpleClassName;
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

		String loggerName;
		if (simpleClassName) {
			loggerName = record.getLoggerName().substring(record.getLoggerName().lastIndexOf(".") + 1);
		} else {
			loggerName = record.getLoggerName();
		}
		sb.append(loggerName).append(lineNumber).append("\t").append(SEP);

		String threadName;
		String threadPool = "";
		Pattern pattern = Pattern.compile("pool-(\\d)+-thread-(\\d)+");
		Matcher matcher = pattern.matcher(currentThread.getName());
		if(matcher.matches()) {
			threadName = "thread";
			if(showThreadPool) {
				threadPool = String.format("-[%s.%s]", matcher.group(1), matcher.group(2));
			}
		} else {
			threadName = currentThread.getName();
		}
		sb.append("<").append(String.format("%s-%d%s", threadName, currentThread.getId(), threadPool)).append(">");
		sb.append("\t").append(SEP);

		sb.append(String.format("%-6s\t", record.getLevel())).append(SEP);

		sb.append(message).append("\n");

		return sb.toString();
	}

}