package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.ship.Ship;

public class MinerAi implements Ai {

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);


		tryToSell(person);
		tryToMine(person);

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (person.isOn(Asteroid.class) == false && percentageFree >= 0.3) {
				travelToAsteroid(world, person);
			} else if (percentageFree < 0.3) {
				try {
					travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			}
		} while (e != null);

		Log.mine("");

		if (person.getLocation() instanceof Asteroid == false && person.getDecision() instanceof MineDecision) {
			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			System.out.println("wtf");
		}
	}

	private void tryToMine(Person person) {
		person.setDecision(new MineDecision(person));
	}

	private void tryToSell(Person person) {
		Location location = person.getLocation();
		if (location instanceof Planet) {
			//TODO: Flytta logik från planet till location så vi slipper fula instanceof
			Planet planet = (Planet) location;
			for (ProductionType productionType : ProductionType.values()) {
				Production production = planet.getProduction(productionType);
				if (production.getBuyPrice() / production.getBasePrice() > TraderAi.BASE_PRICE_QUOTA_TO_SELL_AT) {
					sellMax(person, planet, productionType);
				}
			}
		}
	}

	private void sellMax(Person person, Planet planet, ProductionType productionType) {
		Production production = planet.getProduction(productionType);
		Ship ship = person.getShip();
		int maxBuy = planet.getMoney() / production.getBuyPrice();
		int maxSell = ship.getStorage(production.getProductionType());
		int sellAmount = Math.min(maxBuy, maxSell);

		if (sellAmount == 0) {
			return;
		}

		if (sellAmount < 0) {
			throw new RuntimeException();
		}

		int payAmount = planet.sellTo(production.getProductionType(), sellAmount);
		person.getShip().addStorage(production.getProductionType(), -sellAmount);
		person.addMoney(payAmount);

		if (ship.getFreeStorage() < 0) {
			throw new RuntimeException();
		}

		Log.mine(person.getName() + " sold " + sellAmount + " " + production + " to " + planet.getName()
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private void travelToAsteroid(World world, Person person) {
		Asteroid asteroid;
		if (Rand.bool()) {    // most efficient
			asteroid = world.getAsteroidsShuffled().stream()
					.max((o1, o2) -> Double.compare(o1.getMiningEfficiency(), o2.getMiningEfficiency()))
					.get();
			Log.mine(person.getName() + " travels to most efficient asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMiningEfficiency());
		} else {    // closest
			asteroid = world.getAsteroidsShuffled().stream()
					.min((o1, o2) -> Double.compare(o1.getDistance(person.getPoint()), o2.getDistance(person.getPoint())))
					.get();
			Log.mine(person.getName() + " travels to closest asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, asteroid);
		person.setDecision(travelDecision);
	}

	private void travelToSell(World world, Person person) throws ConditionsChangedException {
		Ship ship = person.getShip();

		ProductionType goods = ship.getLargestProductionStorage();

		Planet planet = world.getPlanetsShuffled().stream()
				.filter(p -> p.getMoney() > 500)
				.max((o1, o2) -> o1.getProduction(goods).getBuyPrice() - o2.getProduction(goods).getBuyPrice())
				.get();

		TravelDecision travelDecision = new TravelDecision(person, planet);
		person.setDecision(travelDecision);

		if (person.getLocation() instanceof Planet) {
			Planet currentPlanet = (Planet) person.getLocation();
			if (currentPlanet.equals(planet)) {
				Log.mine("selling on same planet...");
				sellMax(person, planet, goods);    //Sell the crappy resource and try again
				throw new ConditionsChangedException();
			}
		}

		Log.mine(person.getName() + " travels to " + planet.getName()
				+ " to sell " + goods + " for " + planet.getProduction(goods).getBuyPrice() + " each");
	}

	private class ConditionsChangedException extends Exception {
	}

}
