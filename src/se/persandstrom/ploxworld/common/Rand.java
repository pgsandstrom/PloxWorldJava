package se.persandstrom.ploxworld.common;

import java.util.Random;

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
		return (r.nextDouble() * (max-min))+min;
	}

}
