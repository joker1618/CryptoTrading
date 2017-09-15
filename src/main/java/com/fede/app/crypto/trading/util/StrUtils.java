package com.fede.app.crypto.trading.util;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class StrUtils {

	public static String[] splitAllFields(String source, String separatorString) {
		return splitAllFields(source, separatorString, false);
	}
	public static String[] splitAllFields(String source, String separatorString, boolean trimValues) {
		return splitAllFields(source, separatorString, false, true);
	}
	public static String[] splitAllFields(String source, String separatorString, boolean trimValues, boolean removeSeparator) {
		if(source.isEmpty()) {
			return new String[0];
		}
		
		String[] splitted = source.split(Pattern.quote(separatorString));
		int numFields = StringUtils.countMatches(source, separatorString) + 1;

		String[] toRet = new String[numFields];

		int pos = 0;
		for(; pos < splitted.length; pos++) {
			String str = removeSeparator ? splitted[pos] : splitted[pos] + separatorString;
			toRet[pos] = trimValues ? str.trim() : str;
		}
		for(; pos < numFields; pos++) {
			toRet[pos] = "";
		}

		return toRet;
	}

	public static List<String> splitFieldsList(String source, String separatorString) {
		return splitFieldsList(source, separatorString, false);
	}
	public static List<String> splitFieldsList(String source, String separatorString, boolean trimValues) {
		return splitFieldsList(source, separatorString, trimValues, true);
	}
	public static List<String> splitFieldsList(String source, String separatorString, boolean trimValues, boolean removeSeparator) {
		return Converter.arrayToList(splitAllFields(source, separatorString, trimValues, removeSeparator));
	}

	public static String leftPadLines(String source, char padChar, int padSize) {
		String[] lines = splitAllFields(source, "\n");
		List<String> padded = leftPadLines(Converter.arrayToArrayList(lines), padChar, padSize);
		return padded.stream().collect(Collectors.joining("\n"));
	}
	public static List<String> leftPadLines(List<String> list, char padChar, int padSize) {
		return list.stream().map(str -> StringUtils.repeat(padChar, padSize) + str).collect(Collectors.toList());
	}

	public static int maxLineLength(String str) {
		return maxLineLength(splitAllFields(str, "\n", false));
	}
	public static int maxLineLength(List<String> list) {
		OptionalInt max = list.stream().mapToInt(String::length).max();
		return max.isPresent() ? max.getAsInt() : 0;
	}
	public static int maxLineLength(String[] strArr) {
		return maxLineLength(Converter.arrayToList(strArr));
	}

}
