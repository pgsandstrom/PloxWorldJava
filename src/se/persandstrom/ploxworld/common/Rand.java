package se.persandstrom.ploxworld.common;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Rand {

	private static final Random r;

	static {
		long seed = System.currentTimeMillis();
		System.out.println("r seed: " + seed);
		r = new Random(seed);
	}

	public static int bound(int max) {
		return r.nextInt(max);
	}

	public static int bound(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	public static double percentage() {
		return r.nextDouble();
	}

	public static double percentage(double min, double max) {
		return (r.nextDouble() * (max - min)) + min;
	}

	public static boolean bool() {
		return r.nextBoolean();
	}

	public static <T> T getRandom(List<T> list) {
		return list.get(r.nextInt(list.size()));
	}

	public static <T> T getRandom(Collection<T> collection) {
		int index = (int) (Math.random() * collection.size());
		for(T t: collection) {
			if (--index < 0) {
				return t;
			}
		}
		throw new AssertionError();
	}
}
