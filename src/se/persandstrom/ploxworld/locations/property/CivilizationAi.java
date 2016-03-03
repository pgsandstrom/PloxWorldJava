package se.persandstrom.ploxworld.locations.property;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.ship.Weapon;

public class CivilizationAi {

	static void purchase(Civilization civilization) {
		Optional<Weapon> missingWeapon = Weapon.getMissingWeaponEasistResearched(civilization.getWeapons());
		if (missingWeapon.isPresent()) {
			Weapon weapon = missingWeapon.get();
			if (weapon.researchCost < civilization.getTradeable().getScience().getStorage()) {
				civilization.getTradeable().getScience().addStorage(-weapon.researchCost);
				civilization.addWeapon(weapon);
				Log.planet(civilization + " bought weapon " + weapon);
			}
		}
	}
}
