package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class DecisionMaker {

	public static void makeDecision(World world, Person person) {

		System.out.println("hej");

		ProductionType productionType = ProductionType.getRandom();

		Planet planetMax = world.getPlanetMin((o1, o2) ->
				o1.getProduction(productionType).getBuyPrice() - o2.getProduction(productionType).getBuyPrice());

		System.out.println(planetMax);

		TravelDecision travelDecision = new TravelDecision(person, planetMax);
		person.setDecision(travelDecision);

		System.out.println(person.getName() + " travels to " + planetMax.getName());
	}
}
