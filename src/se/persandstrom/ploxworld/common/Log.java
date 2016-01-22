package se.persandstrom.ploxworld.common;

public class Log {

	public static final boolean TRADE = false;
	public static final boolean MINE = false;
	public static final boolean PIRATE = true;

	public static void trade(String string) {
		if (TRADE) {
			System.out.println(string);
		}
	}

	public static void mine(String string) {
		if (MINE) {
			System.out.println(string);
		}
	}

	public static void pirate(String string) {
		if (PIRATE) {
			System.out.println(string);
		}
	}

	public static void warning(String string) {
		System.out.println(string);
	}
}
