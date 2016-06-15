package se.persandstrom.ploxworld.ship;

import java.util.ArrayList;
import java.util.List;

import se.persandstrom.ploxworld.common.Rand;

public class Weapon extends BuyableItem {

	public static List<Weapon> weapons = new ArrayList<>();
	static {
		weapons.add(new Weapon("simple",		3,	10,	0.5,	3000,	500));
		weapons.add(new Weapon("x-long",		3,	8,	1,		4000,	1000));
		weapons.add(new Weapon("BARR",			5,	12,	0.5,	7000,	2000));
		weapons.add(new Weapon("BARR-X",		7,	14,	0.55,	9000,	3000));
		weapons.add(new Weapon("Assault MC1",	10,	15,	0.6,	14000,	4000));
		weapons.add(new Weapon("Assault MC2",	12,	17,	0.6,	20000,	10000));
		weapons.add(new Weapon("Assault MC3",	14,	19,	0.65,	25000,	15000));
	}

	public static Weapon getFirst() {
		return weapons.get(0);
	}

	public final int minDamage;
	public final int maxDamage;
	public final double accuracy;

	Weapon(String name, int minDamage, int maxDamage, double accuracy, int purchaseCost, int researchCost) {
		super(name, purchaseCost, researchCost);
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
