package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.ship.Weapon;

public class PlanetAi {

	static void purchase(Planet planet) {
		Optional<Weapon> missingWeapon = Weapon.getMissingWeaponEasistResearched(planet.getWeapons());
		if (missingWeapon.isPresent()) {
			Weapon weapon = missingWeapon.get();
			if (weapon.researchCost < planet.getScience().getStorage()) {
				planet.getScience().addStorage(-weapon.researchCost);
				planet.addWeapon(weapon);
				Log.planet(planet + " bought weapon " + weapon);
			}
		}
	}
}
