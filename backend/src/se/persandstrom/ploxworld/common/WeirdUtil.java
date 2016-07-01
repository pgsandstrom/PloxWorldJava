package se.persandstrom.ploxworld.common;

import java.util.HashMap;
import java.util.Map;

public class WeirdUtil {

	private static final Map<String, Integer> stringToInt = new HashMap<>();

	public static int stringToRandomDeterministicInt(String string) {
		Integer integer = stringToInt.get(string);
		if (integer == null) {
			stringToInt.put(string, Rand.bound());
		}
		return stringToInt.get(string);
	}
}
