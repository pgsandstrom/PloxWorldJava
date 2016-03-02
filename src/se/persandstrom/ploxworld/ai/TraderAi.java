package se.persandstrom.ploxworld.ai;

import java.util.List;
import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.locations.property.Tradeable;
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

		if (person.getDecision() != null && person.getDecision().getGoal() != null) {
			person.getDecision().getGoal().execute(person);
		}

		Ship ship = person.getShip();
		person.setDecision(null);


		tryToSell(person);
		tryToBuy(person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

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
		if (location.getTradeable().isPresent()) {
			Tradeable tradeable = location.getTradeable().get();
			List<Production> productionsCheapestFirst = tradeable.getProductionsCheapestFirst();

			productionsCheapestFirst.stream()
					.filter(production -> production.getSellPrice() / production.getBasePrice() < BASE_PRICE_QUOTA_TO_BUY_AT)
					.forEach(production -> buyMax(person, tradeable, production));
		}
	}

	private void buyMax(Person person, Tradeable tradeable, Production production) {
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
			throw new RuntimeException();	//TODO this is thrown sometimes ;_;
		}

		int payAmount = tradeable.buyFrom(production.getProductionType(), buyAmount);
		ship.addStorage(production.getProductionType(), buyAmount);
		person.addMoney(-payAmount);

		if (ship.getFreeStorage() < 0) {
			throw new RuntimeException();
		}

		Log.trade(person + " bought " + buyAmount + " " + production + " from " + tradeable.getLocation()
				+ " for " + production.getSellPrice() + " each. In total:  " + payAmount);
	}

	private void tryToSell(Person person) {
		Location location = person.getLocation();
		if (location.getTradeable().isPresent()) {
			Tradeable tradeable = location.getTradeable().get();
			for (ProductionType productionType : ProductionType.values()) {
				Production production = tradeable.getProduction(productionType);
				if (production.getBuyPrice() / production.getBasePrice() > BASE_PRICE_QUOTA_TO_SELL_AT) {
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

		Log.trade(person + " sold " + sellAmount + " " + production + " to " + tradeable
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private void travelToBuy(World world, Person person) {

		ProductionType viableGoods = null;
		Planet cheapestSellingPlanet = null;

		for (ProductionType randomGoods : ProductionType.getBaseProductRandomOrder()) {

			cheapestSellingPlanet = world.getCheapestSellingPlanet(randomGoods);
			Planet mostPayingPlanet = world.getMostPayingPlanet(randomGoods);

			int minBuyPrice = cheapestSellingPlanet.getTradeable().get().getProduction(randomGoods).getSellPrice();
			int maxSellPrice = mostPayingPlanet.getTradeable().get().getProduction(randomGoods).getBuyPrice();

			if (maxSellPrice - minBuyPrice > 0) {
				viableGoods = randomGoods;
				break;
			}
		}

		TravelDecision travelDecision = new TravelDecision(person, cheapestSellingPlanet);
		person.setDecision(travelDecision);

		Log.trade(person + " travels to " + cheapestSellingPlanet
				+ " to buy " + viableGoods + " for " + cheapestSellingPlanet.getTradeable().get().getProduction(viableGoods).getSellPrice());
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

		Log.trade(person + " travels to " + location
				+ " to sell " + goods + " for " + tradeable.getProduction(goods).getBuyPrice() + " each");
	}

	private class ConditionsChangedException extends Exception {
	}

}
