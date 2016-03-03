package se.persandstrom.ploxworld.ai;

import java.util.List;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.locations.Location;
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


		AiOperations.tryToSell(person);
		tryToBuyCheap(person);

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
					AiOperations.travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			}
		} while (e != null);

		Log.trade("");
	}

	private void tryToBuyCheap(Person person) {
		Location location = person.getLocation();
		if (location.getTradeable().isPresent()) {
			Tradeable tradeable = location.getTradeable().get();
			List<Production> productionsCheapestFirst = tradeable.getProductionsCheapestFirst();

			productionsCheapestFirst.stream()
					.filter(production -> production.getSellPrice() / production.getBasePrice() < BASE_PRICE_QUOTA_TO_BUY_AT)
					.forEach(production -> AiOperations.buyMax(person, tradeable, production));
		}
	}

	private void travelToBuy(World world, Person person) {

		ProductionType viableGoods = null;
		Location cheapestSellingLocation = null;

		for (ProductionType randomGoods : ProductionType.getBaseProductRandomOrder()) {

			cheapestSellingLocation = world.getCheapestSellingLocation(randomGoods);
			Location mostPayingLocation = world.getMostPayingLocation(randomGoods);

			int minBuyPrice = cheapestSellingLocation.getTradeable().get().getProduction(randomGoods).getSellPrice();
			int maxSellPrice = mostPayingLocation.getTradeable().get().getProduction(randomGoods).getBuyPrice();

			if (maxSellPrice - minBuyPrice > 0) {
				viableGoods = randomGoods;
				break;
			}
		}

		TravelDecision travelDecision = new TravelDecision(person, cheapestSellingLocation);
		person.setDecision(travelDecision);

		Log.trade(person + " travels to " + cheapestSellingLocation
				+ " to buy " + viableGoods + " for " + cheapestSellingLocation.getTradeable().get().getProduction(viableGoods).getSellPrice());
	}
}
