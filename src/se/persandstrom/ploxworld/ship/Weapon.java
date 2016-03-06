package se.persandstrom.ploxworld.ship;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import se.persandstrom.ploxworld.common.Rand;

public enum Weapon {

	SIMPLE(			"simple",		3,	10,	0.5,	3000,	500),
	X_LONG(			"x-long",		3,	8,	1,		4000,	1000),
	BARR(			"BARR",			5,	12,	0.5,	7000,	2000),
	BARR_X(			"BARR-X",		7,	14,	0.55,	9000,	3000),
	ASSAULT_MC1(	"Assault MC1",	10,	15,	0.6,	14000,	4000),
	ASSAULT_MC2(	"Assault MC2",	12,	17,	0.6,	20000,	10000),
	ASSAULT_MC3(	"Assault MC3",	14,	19,	0.65,	25000,	15000);

	public final String name;
	public final int minDamage;
	public final int maxDamage;
	public final double accuracy;
	public final int purchaseCost;
	public final int researchCost;

	Weapon(String name, int minDamage, int maxDamage, double accuracy, int purchaseCost, int researchCost) {
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.accuracy = accuracy;
		this.purchaseCost = purchaseCost;
		this.researchCost = researchCost;
	}

	public double getAverageDamage() {
		return ((minDamage + maxDamage) / 2) * accuracy;
	}

	public int rollDamage() {
		return Rand.bound(minDamage, maxDamage);
	}

	public int getSellCost() {
		return purchaseCost;
	}

	public static Optional<Weapon> getMissingWeaponEasistResearched(Set<Weapon> existingWeapons) {
		return Arrays.asList(Weapon.values()).stream()
				.filter(weapon -> existingWeapons.contains(weapon) == false)
				.sorted((o1, o2) -> o1.researchCost - o2.researchCost)
				.findFirst();
	}

	public static Optional<Weapon> getNextWeapon(Weapon currentWeapon) {
		return Arrays.asList(Weapon.values()).stream()
				.filter(weapon -> weapon.purchaseCost > currentWeapon.purchaseCost)
				.sorted((o1, o2) -> o1.purchaseCost - o2.purchaseCost)
				.findFirst();
	}

	@Override
	public String toString() {
		return name;
	}
}
