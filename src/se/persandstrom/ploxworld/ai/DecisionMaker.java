package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class DecisionMaker {

	static Planet planet;

	public static void makeDecision(World world, Person person) {

		if (planet == null) {
			planet = Rand.getRandom(world.getPlanets());
			System.out.println("travel to: " + planet.getName());
		}

		TravelDecision travelDecision = new TravelDecision(person, planet);
		person.setDecision(travelDecision);
	}
}
