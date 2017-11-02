package com.fede.app.crypto.trading.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class Utils {

	public static <T> String join(Collection<T> source) {
		return join(source, ",");
	}
	public static <T> String join(Collection<T> source, String separator) {
		return join(source, separator, String::valueOf);
	}
	public static <T> String join(Collection<T> source, String separator, Function<T,String> mapFunc) {
		return source.stream().map(mapFunc).collect(Collectors.joining(separator));
	}

	public static <T> List<T> filter(Collection<T> source, Predicate<T> filter) {
		return source.stream().filter(filter).collect(Collectors.toList());
	}
	public static <T,U> List<U> map(Collection<T> source, Function<T,U> mapper) {
		return source.stream().map(mapper).collect(Collectors.toList());
	}
	public static <T,U> List<U> filterAndMap(Collection<T> source, Predicate<T> filter, Function<T,U> mapper) {
		return source.stream().filter(filter).map(mapper).collect(Collectors.toList());
	}

	public static String toString(Double d) {
		if(d == null)	return "-";
		return getNumberFormat().format(d);
	}
	public static String toString(Integer num) {
		if(num == null)	return "-";
		return String.valueOf(num);
	}
	public static String toString(Long num) {
		if(num == null)	return "-";
		return String.valueOf(num);
	}
	public static Double toDouble(String str) {
		if(str.equals("-"))	return null;

		try {
			return getNumberFormat().parse(str).doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	public static Integer toInteger(String str) {
		if(str.equals("-"))	return null;
		return Integer.parseInt(str);
	}
	public static Long toLong(String str) {
		if(str.equals("-"))	return null;
		return Long.parseLong(str);
	}
	private static NumberFormat getNumberFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(12);
		nf.setGroupingUsed(false);
		return nf;
	}

	public static <V,K> Map<K,V> toMapSingle(Collection<V> source, Function<V,K> keyMapper) {
		Map<K,V> map = new HashMap<>();

		source.forEach(v -> {
			K key = keyMapper.apply(v);
			map.put(key, v);
		});

		return map;
	}
	public static <V,K> Map<K,List<V>> toMap(Collection<V> source, Function<V,K> keyMapper) {
		Map<K,List<V>> map = new HashMap<>();

		source.forEach(v -> {
			K key = keyMapper.apply(v);
			List<V> value = map.get(key);
			if(value == null) {
				value = new ArrayList<>();
				map.put(key, value);
			}
			value.add(v);
		});

		return map;
	}

	public static String humanElapsed(long elapsed, boolean showMilli) {
		WTime td = new WTime(elapsed);

		String strMilli = showMilli ? String.format(".%03d", td.getMilli()) : "";

		if(td.getHour() > 0) {
			return String.format("%02d:%02d:%02d%s", td.getHour(), td.getMinute(), td.getSecond(), strMilli);
		} else if(td.getMinute() > 0) {
			return String.format("%02d:%02d%s", td.getMinute(), td.getSecond(), strMilli);
		} else {
			return String.format("%d%s", td.getSecond(), strMilli);
		}
	}


	private static class WTime {

		private long totalMillis;

		private long day;
		private long hour;
		private long minute;
		private long second;
		private long milli;


		public WTime() {
			this(System.currentTimeMillis());
		}

		public WTime(long totalMillis) {
			this.totalMillis = totalMillis;
			this.milli = totalMillis % 1000;

			long rem = (totalMillis - this.milli) / 1000;

			long daySec = DAYS.getDuration().getSeconds();
			this.day = rem / daySec;
			rem -= daySec * this.day;

			long hourSec = HOURS.getDuration().getSeconds();
			this.hour = rem / hourSec;
			rem -= hourSec * this.hour;

			long minuteSec = MINUTES.getDuration().getSeconds();
			this.minute = rem / minuteSec;
			rem -= minuteSec * this.minute;

			this.second =  rem;
		}

		/* GETTERS and SETTERS */
		public long getTotalMillis() {
			return totalMillis;
		}
		public long getDay() {
			return day;
		}
		public long getHour() {
			return hour;
		}
		public long getMinute() {
			return minute;
		}
		public long getSecond() {
			return second;
		}
		public long getMilli() {
			return milli;
		}
		public long getTotalHour() {
			return hour + 24 * day;
		}
		public long getTotalMinute() {
			return minute + 60 * getTotalHour();
		}
		public long getTotalSecond() {
			return second + 60 * getTotalMinute();
		}

	}
}
