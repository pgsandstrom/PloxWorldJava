package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.action.Repair;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class RepairGoal implements Goal {

	public RepairGoal() {
	}

	@Override
	public void execute(Person person) {
		Ship ship = person.getShip();
		Location location = person.getLocation();
		if (location.getCivilization().isPresent()) {
			Civilization civilization = location.getCivilization().get();
			new Repair(person, civilization).execute();
		}
	}
}
