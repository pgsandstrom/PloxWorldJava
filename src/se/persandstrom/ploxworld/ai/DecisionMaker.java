package se.persandstrom.ploxworld.ai;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.ship.Ship;

public class DecisionMaker {

	public static void makeDecision(World world, Person person) {

		if (person.getPlanet() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);


		tryToSell(person);
		tryToBuy(person);

		double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
		if (percentageFree > 0.5 && person.getMoney() > 500) {
			travelToBuy(world, person);
		} else if (percentageFree == 1.0 && person.getMoney() < 200) {
			System.out.println("no monies");
		} else if (percentageFree == 1.0) {
			travelToBuy(world, person);
		} else {
			travelToSell(world, person);
		}

		System.out.println();
	}

	private static void tryToBuy(Person person) {
		Planet planet = person.getPlanet();

		List<Production> productionsCheapestFirst = planet.getProductionsCheapestFirst();

		// 1.4 is very much, but right now pricing is weird...
		productionsCheapestFirst.stream()
				.filter(production -> production.getSellPrice() / production.getBasePrice() < 1.4)
				.forEach(production -> buyMax(person, planet, production));
	}

	private static void buyMax(Person person, Planet planet, Production production) {
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

		System.out.println(person.getName() + " bought " + buyAmount + " " + production + " from " + planet.getName()
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private static void tryToSell(Person person) {
		Planet planet = person.getPlanet();
		for (ProductionType productionType : ProductionType.values()) {
			Production production = planet.getProduction(productionType);
			if (production.getBuyPrice() / production.getBasePrice() > 1.5) {
				sellMax(person, planet, production);
			}
		}
	}

	private static void sellMax(Person person, Planet planet, Production production) {
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

		System.out.println(person.getName() + " sold " + sellAmount + " " + production + " to " + planet.getName()
				+ " for " + production.getBuyPrice() + " each. In total:  " + payAmount);
	}

	private static void travelToBuy(World world, Person person) {

		ProductionType randomGoods = ProductionType.getRandomBaseProduct();

		Planet planet = world.getPlanetMin((o1, o2) ->
				o1.getProduction(randomGoods).getSellPrice() - o2.getProduction(randomGoods).getSellPrice());

		TravelDecision travelDecision = new TravelDecision(person, planet);
		person.setDecision(travelDecision);

		System.out.println(person.getName() + " travels to " + planet.getName()
				+ " to buy " + randomGoods + " for " + planet.getProduction(randomGoods).getSellPrice());
	}

	private static void travelToSell(World world, Person person) {
		Ship ship = person.getShip();

		ProductionType goods = ship.getLargestProductionStorage();

		Planet planet = world.getPlanets().stream()
				.filter(p -> p.getMoney() > 500)
				.max((o1, o2) -> o1.getProduction(goods).getBuyPrice() - o2.getProduction(goods).getBuyPrice())
				.get();

		TravelDecision travelDecision = new TravelDecision(person, planet);
		person.setDecision(travelDecision);

		if (person.getPlanet().equals(planet)) {
			throw new RuntimeException();
		}

		System.out.println(person.getName() + " travels to " + planet.getName()
				+ " to sell " + goods + " for " + planet.getProduction(goods).getSellPrice() + " each");
	}

}
