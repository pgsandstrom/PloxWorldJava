package se.persandstrom.ploxworld.common;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Rand {

	private static Random r;

	public static void reset() {
		long seed = System.currentTimeMillis();
		System.out.println("random seed: " + seed);
//		seed = 123132312L;
		setSeed(seed);
	}

	public static void setSeed(long seed) {
		r = new Random(seed);
	}

	public static boolean bool() {
		return r.nextBoolean();
	}

	public static int bound() {
		return r.nextInt();
	}

	public static int bound(int max) {
		return r.nextInt(max);
	}

	public static int bound(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	public static double boundDouble(double min, double max) {
		return r.nextDouble() * (max - min) + min;
	}

	public static double percentage() {
		return r.nextDouble();
	}

	public static double percentage(double min, double max) {
		return (r.nextDouble() * (max - min)) + min;
	}

	public static boolean roll() {
		return 0.5 > r.nextDouble();
	}

	public static boolean roll(double chance) {
		return chance > r.nextDouble();
	}

	public static Random getRandom() {
		return r;
	}

	public static <T> T getRandom(List<T> list) {
		return list.get(r.nextInt(list.size()));
	}

	public static <T> T getRandom(Collection<T> collection) {
		int index = (int) (r.nextDouble() * collection.size());
		for (T t : collection) {
			if (--index < 0) {
				return t;
			}
		}
		throw new AssertionError();
	}
}
