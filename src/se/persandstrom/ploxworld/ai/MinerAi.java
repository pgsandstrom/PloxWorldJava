package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.action.Repair;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class MinerAi implements Ai {

	public void makeDecision(World world, Person person) {

		if (person.getLocation() == null) {
			return;
		}

		if (person.getDecision() != null && person.getDecision().getGoal() != null) {
			person.getDecision().getGoal().execute(person);
		}

		Ship ship = person.getShip();
		person.setDecision(null);

		if(ship.isDamaged() && person.getLocation().getCivilization().isPresent()) {
			new Repair(person, person.getLocation().getCivilization().get()).execute();
		}

		AiOperations.tryToSell(person);

		if (new CheckUpgrade().willTravelToUpgrade(world, person)) {
			return;
		}

		ConditionsChangedException e;
		do {
			e = null;

			double percentageFree = ship.getFreeStorage() / (double) ship.getMaxStorage();
			if (person.getLocation().getMineable().isPresent() == false && percentageFree >= 0.3) {
				travelToMineableLocation(world, person);
			} else if(percentageFree < 0.3){
				try {
					AiOperations.travelToSell(world, person);
				} catch (ConditionsChangedException e1) {
					e = e1;
				}
			} else {
				mine(person);
			}
		} while (e != null);

		Log.mine("");

		if (person.getLocation() instanceof Location == false && person.getDecision() instanceof MineDecision) {
			throw new IllegalStateException();
		}
	}

	private void mine(Person person) {
		person.setDecision(new MineDecision(person));
	}

	private void travelToMineableLocation(World world, Person person) {
		Location location;
		if (Rand.bool()) {    // most efficient
			location = world.getMineableShuffled().stream()
					.max((o1, o2) -> Double.compare(o1.getMineable().get().getMiningEfficiency(), o2.getMineable().get().getMiningEfficiency()))
					.get();
			Log.mine(person.getName() + " travels to most efficient asteroid " + location.getName()
					+ " to mine at an efficiancy of " + location.getMineable().get().getMiningEfficiency());
		} else {    // closest
			location = world.getMineableShuffled().stream()
					.min((o1, o2) -> Double.compare(o1.getDistance(person.getPoint()), o2.getDistance(person.getPoint())))
					.get();
			Log.mine(person.getName() + " travels to closest asteroid " + location.getName()
					+ " to mine at an efficiancy of " + location.getMineable().get().getMiningEfficiency());
		}

		TravelDecision travelDecision = new TravelDecision(person, location);
		person.setDecision(travelDecision);
	}
}
