package se.persandstrom.ploxworld.common;

public class Log {

	private static final boolean TRADE = false;
	private static final boolean MINE = false;
	private static final boolean PIRATE = false;
	private static final boolean DIALOG = true;

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

	public static void dialog(String string) {
		if (DIALOG) {
			System.out.println(string);
		}
	}

	public static void warning(String string) {
		System.out.println(string);
	}
}
