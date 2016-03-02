package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class MinerAi implements Ai {

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		Ship ship = person.getShip();
		person.setDecision(null);


		AiOperations.tryToSell(person);
		tryToMine(person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (person.isOn(Asteroid.class) == false && percentageFree >= 0.3) {
				travelToAsteroid(world, person);
			} else if (percentageFree < 0.3) {
				try {
					AiOperations.travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			}
		} while (e != null);

		Log.mine("");

		if (person.getLocation() instanceof Asteroid == false && person.getDecision() instanceof MineDecision) {
			throw new IllegalStateException();
		}
	}

	private void tryToMine(Person person) {
		person.setDecision(new MineDecision(person));
	}

	private void travelToAsteroid(World world, Person person) {
		Asteroid asteroid;
		if (Rand.bool()) {    // most efficient
			asteroid = world.getAsteroidsShuffled().stream()
					.max((o1, o2) -> Double.compare(o1.getMineable().get().getMiningEfficiency(), o2.getMineable().get().getMiningEfficiency()))
					.get();
			Log.mine(person.getName() + " travels to most efficient asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		} else {    // closest
			asteroid = world.getAsteroidsShuffled().stream()
					.min((o1, o2) -> Double.compare(o1.getDistance(person.getPoint()), o2.getDistance(person.getPoint())))
					.get();
			Log.mine(person.getName() + " travels to closest asteroid " + asteroid.getName()
					+ " to mine at an efficiancy of " + asteroid.getMineable().get().getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, asteroid);
		person.setDecision(travelDecision);
	}
}
