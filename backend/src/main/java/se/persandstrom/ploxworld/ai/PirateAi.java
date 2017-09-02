package se.persandstrom.ploxworld.ai;

import java.util.Comparator;

import se.persandstrom.ploxworld.action.Repair;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class PirateAi implements Ai {

	@Override
	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		if (person.getDecision() != null && person.getDecision().getGoal() != null) {
			person.getDecision().getGoal().execute(world, person);
		}

		Ship ship = person.getShip();
		person.setDecision(null);

		if (ship.isDamaged() && person.getLocation().getCivilization().isPresent()) {
			world.executeAction(new Repair(person, person.getLocation().getCivilization().get()));
		}

		AiOperations.tryToSell(world, person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

		if (ship.getHealth() < ship.getMaxHealth() * 0.7) {
			AiOperations.travelToRepair(world, person);
			return;
		}

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (percentageFree < 0.8) {
				try {
					AiOperations.travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			} else if (person.getLocation().getMineable().isPresent() == false) {
				travelToMineableLocation(world, person);
			} else {
				tryToAmbush(world, person);
			}
		} while (e != null);

		Log.pirate("");
	}

	private void tryToAmbush(World world, Person person) {
		person.setDecision(new AmbushDecision(world, person));
	}


	private void travelToMineableLocation(World world, Person person) {
		Location location;
		if (Rand.bool()) {    // most efficient
			location = world.getMineableShuffled().stream()
					.max(Comparator.comparingDouble(o -> o.getMineable().get().getMiningEfficiency()))
					.get();
			Log.pirate(person.getName() + " travels to most efficient asteroid " + location.getName()
					+ " to mine at an efficiancy of " + location.getMineable().get().getMiningEfficiency());
		} else {    // closest
			location = world.getMineableShuffled().stream()
					.min(Comparator.comparingDouble(o -> o.getDistance(person.getPoint())))
					.get();
			Log.pirate(person.getName() + " travels to closest asteroid " + location.getName()
					+ " to mine at an efficiancy of " + location.getMineable().get().getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, location);
		person.setDecision(travelDecision);
	}
}
