package se.persandstrom.ploxworld.ai;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.BuyableItem;

public class CheckUpgrade {

	public boolean willTravelToUpgrade(World world, Person person) {
		// TODO: Should not buy expensive weapons if the ship is shitty...
		if(person.getPersonality().getAggressionRoll() > 60) {
			return willTravelToUpgradeItem(world, person, person.getShip().getWeapon());
		} else {
			return willTravelToUpgradeItem(world, person, person.getShip().getShipBase());
		}
	}

	public boolean willTravelToUpgradeItem(World world, Person person, BuyableItem currentItem) {
		Optional<BuyableItem> nextWeaponOpt = Optional.of(currentItem);

		do {
			nextWeaponOpt = BuyableItem.getNextItem(nextWeaponOpt.get());
			if (nextWeaponOpt.isPresent() == false) {
				return false;
			}
			BuyableItem nextWeapon = nextWeaponOpt.get();

			int cost = nextWeapon.purchaseCost - currentItem.getSellCost();

			//TODO: How much a person wants left after purchase should depend on AI-type and personality
			if (cost < person.getMoney() / 2) {
				Optional<Location> weaponLocationOpt = world.getCivilizationsShuffled().stream()
						.filter(planet -> planet.getCivilization().get().getWeapons().contains(nextWeapon)).findFirst();
				if (weaponLocationOpt.isPresent()) {
					travelToUpgrade(person, weaponLocationOpt.get(), nextWeapon);
					return true;
				}
			} else {
				return false;
			}
		} while (true);
	}

	private void travelToUpgrade(Person person, Location planet, BuyableItem buyableItem) {
		person.setDecision(new TravelDecision(person, planet, new UpgradeGoal(buyableItem)));
		Log.person(person + " is traveling to " + planet + " to buy " + buyableItem);
	}
}
