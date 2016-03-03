package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.property.Tradeable;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.ship.Ship;

public class AiOperations {

	public static void tryToSell(Person person) {
		Location location = person.getLocation();
		if (location.getTradeable().isPresent()) {
			Tradeable tradeable = location.getTradeable().get();
			for (ProductionType productionType : ProductionType.values()) {
				Production production = tradeable.getProduction(productionType);
				if (production.getBuyPrice() / production.getBasePrice() > TraderAi.BASE_PRICE_QUOTA_TO_SELL_AT) {
					AiOperations.sellMax(person, tradeable, productionType);
				}
			}
		}
	}

	public static void buyMax(Person person, Tradeable tradeable, Production production) {
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
			throw new RuntimeException();    //TODO this is thrown sometimes ;_;
		}

		int payAmount = tradeable.buyFrom(production.getProductionType(), buyAmount);
		ship.addStorage(production.getProductionType(), buyAmount);
		person.addMoney(-payAmount);

		if (ship.getFreeStorage() < 0) {
			throw new RuntimeException();
		}

		Log.person(person + " bought " + buyAmount + " " + production + " from " + tradeable.getLocation()
				+ " for " + production.getSellPrice() + " each. In total:  " + payAmount);
	}

	public static void travelToSell(World world, Person person) throws ConditionsChangedException {
		Ship ship = person.getShip();

		ProductionType goods = ship.getLargestProductionStorage();

		Tradeable tradeable = world.getTradeableShuffled().stream().map(loc -> loc.getTradeable().get())
				.filter(p -> p.getMoney() > 500)
				.max((o1, o2) -> o1.getProduction(goods).getBuyPrice() - o2.getProduction(goods).getBuyPrice())
				.get();
		Location location = tradeable.getLocation();

		TravelDecision travelDecision = new TravelDecision(person, location);
		person.setDecision(travelDecision);

		if (person.getLocation().equals(location)) {
			Log.person("selling on same planet...");
			AiOperations.sellMax(person, tradeable, goods);    //Sell the crappy resource and try again
			throw new ConditionsChangedException();
		}

		Log.person(person + " travels to " + location
				+ " to sell " + goods + " for " + tradeable.getProduction(goods).getBuyPrice() + " each");
	}

	public static void sellMax(Person person, Tradeable tradeable, ProductionType productionType) {
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

		Log.person(person + " sold " + sellAmount + " " + production + " to " + tradeable
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}
}
