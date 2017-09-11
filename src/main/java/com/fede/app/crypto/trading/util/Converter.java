package com.fede.app.crypto.trading.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class Converter {

	public static <T> List<T> arrayToList(T[] source) {
		return arrayToArrayList(source);
	}
	public static <T> ArrayList<T> arrayToArrayList(T[] source) {
		return source == null ? null : new ArrayList<>(Arrays.asList(source));
	}

}
