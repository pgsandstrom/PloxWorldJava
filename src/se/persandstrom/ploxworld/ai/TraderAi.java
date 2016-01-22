package se.persandstrom.ploxworld.ai;

import java.util.List;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.ship.Ship;

public class TraderAi implements Ai {

	public static final double BASE_PRICE_QUOTA_TO_BUY_AT = 0.95;
	public static final double BASE_PRICE_QUOTA_TO_SELL_AT = 1.2;

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);


		tryToSell(person);
		tryToBuy(person);

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (percentageFree > 0.5 && person.getMoney() > 500) {
				travelToBuy(world, person);
			} else if (percentageFree == 1.0 && person.getMoney() < 200) {
				Log.warning("no monies");
			} else if (percentageFree == 1.0) {
				travelToBuy(world, person);
			} else {
				try {
					travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			}
		} while (e != null);

		Log.trade("");
	}

	private void tryToBuy(Person person) {
		Location location = person.getLocation();
		if (location instanceof Planet) {
			//TODO: Flytta logik från planet till location så vi slipper fula instanceof
			Planet planet = (Planet) location;

			List<Production> productionsCheapestFirst = planet.getProductionsCheapestFirst();

			productionsCheapestFirst.stream()
					.filter(production -> production.getSellPrice() / production.getBasePrice() < BASE_PRICE_QUOTA_TO_BUY_AT)
					.forEach(production -> buyMax(person, planet, production));
		}
	}

	private void buyMax(Person person, Planet planet, Production production) {
		Ship ship = person.getShip();
		int freeStorage = ship.getFreeStorage();
		int afford = person.getMoney() / production.getSellPrice();
		int maxBuy = Math.min(freeStorage, afford);
		int maxSell = production.getStorage();
		int buyAmount = Math.min(maxBuy, maxSell);

		if (buyAmount == 0) {
			return;
		}

		if (buyAmount < 0) {
			throw new RuntimeException();
		}

		int payAmount = planet.buyFrom(production.getProductionType(), buyAmount);
		ship.addStorage(production.getProductionType(), buyAmount);
		person.addMoney(-payAmount);

		if (ship.getFreeStorage() < 0) {
			throw new RuntimeException();
		}

		Log.trade(person.getName() + " bought " + buyAmount + " " + production + " from " + planet.getName()
				+ " for " + production.getSellPrice() + " each. In total:  " + payAmount);
	}

	private void tryToSell(Person person) {
		Location location = person.getLocation();
		if (location instanceof Planet) {
			//TODO: Flytta logik från planet till location så vi slipper fula instanceof
			Planet planet = (Planet) location;
			for (ProductionType productionType : ProductionType.values()) {
				Production production = planet.getProduction(productionType);
				if (production.getBuyPrice() / production.getBasePrice() > BASE_PRICE_QUOTA_TO_SELL_AT) {
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

		Log.trade(person.getName() + " sold " + sellAmount + " " + production + " to " + planet.getName()
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private void travelToBuy(World world, Person person) {

		ProductionType viableGoods = null;
		Planet cheapestSellingPlanet = null;

		for (ProductionType randomGoods : ProductionType.getBaseProductRandomOrder()) {

			cheapestSellingPlanet = world.getCheapestSellingPlanet(randomGoods);
			Planet mostPayingPlanet = world.getMostPayingPlanet(randomGoods);

			int minBuyPrice = cheapestSellingPlanet.getProduction(randomGoods).getSellPrice();
			int maxSellPrice = mostPayingPlanet.getProduction(randomGoods).getBuyPrice();

			if (maxSellPrice - minBuyPrice > 0) {
				viableGoods = randomGoods;
				break;
			}
		}

		TravelDecision travelDecision = new TravelDecision(person, cheapestSellingPlanet);
		person.setDecision(travelDecision);

		Log.trade(person.getName() + " travels to " + cheapestSellingPlanet.getName()
				+ " to buy " + viableGoods + " for " + cheapestSellingPlanet.getProduction(viableGoods).getSellPrice());
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
				System.out.println("selling on same planet...");
				sellMax(person, planet, goods);    //Sell the crappy resource and try again
				throw new ConditionsChangedException();
			}
		}


		Log.trade(person.getName() + " travels to " + planet.getName()
				+ " to sell " + goods + " for " + planet.getProduction(goods).getBuyPrice() + " each");

	}

	private class ConditionsChangedException extends Exception {
	}

}
