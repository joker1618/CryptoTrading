package com.fede.app.crypto.trading.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

}
