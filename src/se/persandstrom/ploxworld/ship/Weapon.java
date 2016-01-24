package se.persandstrom.ploxworld.ship;

public enum Weapon {
	SIMPLE("simple", 3, 10);

	public final String name;
	public final int minDamage;
	public final int maxDamage;

	Weapon(String name, int minDamage, int maxDamage) {
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
}
