package com.fede.app.crypto.trading.logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
import com.fede.app.crypto.trading.util.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {

	private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String SEP = " - ";

	private boolean showDate;
	private boolean showMethod;
	private boolean showClass;
	private boolean simpleClassName;
	private boolean showThread;
	private boolean showLevel;

	private DateTimeFormatter dateTimeFormatter;

	LogFormatter() {
		this.showDate = true;
		this.showMethod = true;
		this.showClass = true;
		this.simpleClassName = true;
		this.showThread = true;
		this.showLevel = true;
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);
	}

	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}

	public void setShowMethod(boolean showMethod) {
		this.showMethod = showMethod;
	}

	public void setShowClass(boolean showClass) {
		this.showClass = showClass;
	}

	public void setSimpleClassName(boolean simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	public void setShowThread(boolean showThread) {
		this.showThread = showThread;
	}

	public void setShowLevel(boolean showLevel) {
		this.showLevel = showLevel;
	}

	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	@Override
	public String format(LogRecord record) {
		String methodName = "";
		String lineNumber = "";
		Thread currentThread = Thread.currentThread();

		if (showMethod || showClass) {
			StackTraceElement[] stackTrace = currentThread.getStackTrace();
			for (int i = 0; i < stackTrace.length && methodName.isEmpty(); i++) {
				StackTraceElement elem = stackTrace[i];
				if (elem.getClassName().startsWith(record.getLoggerName())) {
					methodName = elem.getMethodName();
					lineNumber = "(" + elem.getLineNumber() + ")";
				}
			}
		}

		String message = super.formatMessage(record);

		StringBuilder sb = new StringBuilder();
		if (showDate) {
			LocalDateTime ldt = DateUtils.fromMillis(record.getMillis());
			sb.append(dateTimeFormatter.format(ldt)).append(SEP);
		}
		if (showMethod || showClass) {
			String loggerName;
			if (simpleClassName) {
				loggerName = record.getLoggerName().substring(record.getLoggerName().lastIndexOf(".") + 1);
			} else {
				loggerName = record.getLoggerName();
			}
			sb.append(loggerName).append(lineNumber).append("\t").append(SEP);
			if (showMethod) {
				sb.append(methodName).append("\t").append(SEP);
			}
		}
		if (showThread) {
			sb.append("<").append(currentThread.getName()).append(">");
			sb.append("[").append(currentThread.getId()).append("]");
			sb.append("\t").append(SEP);
		}
		if (showLevel) {
			sb.append(String.format("%-7s\t", record.getLevel())).append(SEP);
		}

		sb.append(message).append("\n");

		return sb.toString();
	}
}