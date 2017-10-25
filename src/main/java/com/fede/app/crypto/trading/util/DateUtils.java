package com.fede.app.crypto.trading.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class DateUtils {

	public static LocalDateTime fromSeconds(long sec) {
		return fromMillis(sec*1000);
	}
	public static LocalDateTime fromMillis(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static long toMillis(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static String toString(long millis, String pattern) {
		return toString(fromMillis(millis), pattern);
	}
	public static String toString(LocalDateTime ldt, String pattern) {
		return DateTimeFormatter.ofPattern(pattern).format(ldt);
	}

}
