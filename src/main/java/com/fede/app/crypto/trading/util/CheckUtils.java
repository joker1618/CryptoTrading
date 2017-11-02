package com.fede.app.crypto.trading.util;

import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class CheckUtils {

	public static <K,V> boolean areEquals(Map<K,V> map1, Map<K,V> map2) {
		if(map1 == null && map2 == null)	return true;
		if(map1 == null || map2 == null)	return false;

		if(map1.size() != map2.size())	return false;

		for(Map.Entry<K,V> entry1 : map1.entrySet()) {
			K key1 = entry1.getKey();
			V val1 = map1.get(key1);
			V val2 = map2.get(key1);
			if(!map2.containsKey(key1))	return false;
			if(val1 != null || val2 != null) {
				if(val1 == null || val2 == null || !val1.equals(val2)) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isInteger(String str) {
		try {
			new Integer(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
