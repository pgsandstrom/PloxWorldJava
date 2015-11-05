package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Production;

public class DecisionMaker {

	public static void makeDecision(World world, Person person) {

		System.out.println("hej");

		Planet planetMax = world.getPlanetMax((o1, o2) -> o1.getMostNeeded().getNeed() - o2.getMostNeeded().getNeed());

		System.out.println(planetMax);

		TravelDecision travelDecision = new TravelDecision(person, planetMax);
		person.setDecision(travelDecision);
		Production mostNeeded = planetMax.getMostNeeded();
		mostNeeded.setNeed(mostNeeded.getNeed() - 3);

		System.out.println(person.getName() + " travels to " + planetMax.getName());
	}
}
