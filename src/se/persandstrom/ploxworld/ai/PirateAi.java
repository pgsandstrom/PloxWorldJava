package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class PirateAi implements Ai {

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);

		AiOperations.tryToSell(person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (person.isOn(Asteroid.class) == false && percentageFree > 0.8) {
				travelToAsteroid(world, person);
			} else if (percentageFree < 0.8) {
				try {
					AiOperations.travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			} else {
				tryToAmbush(world, person);
			}
		} while (e != null);

		Log.pirate("");
	}

	private void tryToAmbush(World world, Person person) {
		person.setDecision(new AmbushDecision(world, person));
	}



	private void travelToAsteroid(World world, Person person) {
		Asteroid asteroid;
		if (Rand.bool()) {    // most efficient
			asteroid = world.getAsteroidsShuffled().stream()
					.max((o1, o2) -> Double.compare(o1.getMineable().get().getMiningEfficiency(), o2.getMineable().get().getMiningEfficiency()))
					.get();
			Log.pirate(person.getName() + " travels to most efficient asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		} else {    // closest
			asteroid = world.getAsteroidsShuffled().stream()
					.min((o1, o2) -> Double.compare(o1.getDistance(person.getPoint()), o2.getDistance(person.getPoint())))
					.get();
			Log.pirate(person.getName() + " travels to closest asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, asteroid);
		person.setDecision(travelDecision);
	}
}
