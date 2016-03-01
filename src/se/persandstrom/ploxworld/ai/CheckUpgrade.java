package se.persandstrom.ploxworld.ai;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;
import se.persandstrom.ploxworld.ship.Weapon;

public class CheckUpgrade {

	public boolean willTravelToUpgrade(World world, Person person) {
		Ship ship = person.getShip();
		Weapon currentWeapon = ship.getWeapon();
		Optional<Weapon> nextWeaponOpt = Optional.of(ship.getWeapon());

		do {
			nextWeaponOpt = Weapon.getNextWeapon(nextWeaponOpt.get());
			if (nextWeaponOpt.isPresent() == false) {
				return false;
			}
			Weapon nextWeapon = nextWeaponOpt.get();

			int cost = nextWeapon.purchaseCost - currentWeapon.getSellCost();

			//TODO: How much a person wants left after purchase should depend on AI-type and personality
			if (cost < person.getMoney() / 2) {
				Optional<Planet> weaponPlanetOpt = world.getPlanetsShuffled().stream().filter(planet -> planet.getWeapons().contains(nextWeapon)).findFirst();
				if (weaponPlanetOpt.isPresent()) {
					travelToUpgrade(person, weaponPlanetOpt.get(), nextWeapon);
					return true;
				}
			} else {
				return false;
			}
		} while (true);
	}

	private void travelToUpgrade(Person person, Planet planet, Weapon nextWeapon) {
		person.setDecision(new TravelDecision(person, planet, new UpgradeGoal(nextWeapon)));
		Log.person(person + " is traveling to " + planet + " to buy " + nextWeapon);
	}
}
