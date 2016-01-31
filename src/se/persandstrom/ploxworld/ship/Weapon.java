package se.persandstrom.ploxworld.ship;

import se.persandstrom.ploxworld.common.Rand;

public enum Weapon {
	SIMPLE("simple", 3, 10, 0.5);

	public final String name;
	public final int minDamage;
	public final int maxDamage;
	public final double accuracy;

	Weapon(String name, int minDamage, int maxDamage, double accuracy) {
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.accuracy = accuracy;
	}

	public double getAverageDamage() {
		return ((minDamage + maxDamage) / 2) * accuracy;
	}

	public int rollDamage() {
		return Rand.bound(minDamage, maxDamage);
	}
}
