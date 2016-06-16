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
		if(person.getMoney() > 40000 ){
			System.out.println("plox");
		}
		if(person.getPersonality().getAggressionRoll() > 60) {
			return willTravelToUpgradeItem(world, person, person.getShip().getWeapon());
		} else {
			return willTravelToUpgradeItem(world, person, person.getShip().getShipBase());
		}
	}

	public boolean willTravelToUpgradeItem(World world, Person person, BuyableItem currentItem) {
		Optional<BuyableItem> nextItemOpt = Optional.of(currentItem);

		do {
			nextItemOpt = BuyableItem.getNextItem(nextItemOpt.get());
			if (nextItemOpt.isPresent() == false) {
				return false;
			}
			BuyableItem nextItem = nextItemOpt.get();

			int cost = nextItem.purchaseCost - currentItem.getSellCost();

			//TODO: How much a person wants left after purchase should depend on AI-type and personality
			if (cost < person.getMoney() / 2) {
				Optional<Location> itemLocationOpt = world.getCivilizationsShuffled().stream()
						.filter(planet -> planet.getCivilization().get().getBuyableItems().contains(nextItem)).findFirst();
				if (itemLocationOpt.isPresent()) {
					travelToUpgrade(person, itemLocationOpt.get(), nextItem);
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
