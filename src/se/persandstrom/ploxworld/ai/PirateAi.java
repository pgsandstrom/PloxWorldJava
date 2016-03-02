package se.persandstrom.ploxworld.ai;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.locations.property.Tradeable;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.ship.Ship;

public class PirateAi implements Ai {

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);

		tryToSell(person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (person.isOn(Asteroid.class) == false && percentageFree > 0.8) {
				travelToAsteroid(world, person);
			} else if (percentageFree < 0.8) {
				try {
					travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			} else {
				tryToAmbush(world, person);
			}
		} while (e != null);

		Log.pirate("");
	}

	private void tryToAmbush(World world, Person person) {
		person.setDecision(new AmbushDecision(world, person));
	}

	private void tryToSell(Person person) {
		Location location = person.getLocation();
		if (location.getTradeable().isPresent()) {
			Tradeable tradeable = location.getTradeable().get();
			for (ProductionType productionType : ProductionType.values()) {
				Production production = tradeable.getProduction(productionType);
				if (production.getBuyPrice() / production.getBasePrice() > TraderAi.BASE_PRICE_QUOTA_TO_SELL_AT) {
					sellMax(person, tradeable, productionType);
				}
			}
		}
	}

	private void sellMax(Person person, Tradeable tradeable, ProductionType productionType) {
		Production production = tradeable.getProduction(productionType);
		Ship ship = person.getShip();
		int maxBuy = tradeable.getMoney() / production.getBuyPrice();
		int maxSell = ship.getStorage(production.getProductionType());
		int sellAmount = Math.min(maxBuy, maxSell);

		if (sellAmount == 0) {
			return;
		}

		if (sellAmount < 0) {
			throw new RuntimeException();
		}

		int payAmount = tradeable.sellTo(production.getProductionType(), sellAmount);
		person.getShip().addStorage(production.getProductionType(), -sellAmount);
		person.addMoney(payAmount);

		if (ship.getFreeStorage() < 0) {
			throw new RuntimeException();
		}

		Log.pirate(person + " sold " + sellAmount + " " + production + " to " + tradeable
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private void travelToAsteroid(World world, Person person) {
		Asteroid asteroid;
		if (Rand.bool()) {    // most efficient
			asteroid = world.getAsteroidsShuffled().stream()
					.max((o1, o2) -> Double.compare(o1.getMineable().get().getMiningEfficiency(), o2.getMineable().get().getMiningEfficiency()))
					.get();
			Log.pirate(person.getName() + " travels to most efficient asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		} else {    // closest
			asteroid = world.getAsteroidsShuffled().stream()
					.min((o1, o2) -> Double.compare(o1.getDistance(person.getPoint()), o2.getDistance(person.getPoint())))
					.get();
			Log.pirate(person.getName() + " travels to closest asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, asteroid);
		person.setDecision(travelDecision);
	}

	private void travelToSell(World world, Person person) throws ConditionsChangedException {
		Ship ship = person.getShip();

		ProductionType goods = ship.getLargestProductionStorage();

		Tradeable tradeable = world.getPlanetsShuffled().stream().map(Planet::getTradeable)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(p -> p.getMoney() > 500)
				.max((o1, o2) -> o1.getProduction(goods).getBuyPrice() - o2.getProduction(goods).getBuyPrice())
				.get();
		Location location = tradeable.getLocation();

		TravelDecision travelDecision = new TravelDecision(person, location);
		person.setDecision(travelDecision);

		if (person.getLocation() instanceof Planet) {
			Planet currentPlanet = (Planet) person.getLocation();
			if (currentPlanet.equals(location)) {
				Log.trade("selling on same planet...");
				sellMax(person, tradeable, goods);    //Sell the crappy resource and try again
				throw new ConditionsChangedException();
			}
		}

		Log.pirate(person + " travels to " + location
				+ " to sell " + goods + " for " + tradeable.getProduction(goods).getBuyPrice() + " each");
	}

	private class ConditionsChangedException extends Exception {
	}
}
