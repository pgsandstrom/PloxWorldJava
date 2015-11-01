package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class DecisionMaker {

	public static void makeDecision(World world, Person person) {
		Planet planet = Rand.getRandom(world.getPlanets());
		TravelDecision travelDecision = new TravelDecision(person, planet);
		person.setDecision(travelDecision);
	}
}
