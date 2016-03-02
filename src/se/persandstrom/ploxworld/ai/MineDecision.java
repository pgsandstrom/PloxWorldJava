package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.person.Person;

public class MineDecision implements Decision {

	private final Person person;

	public MineDecision(Person person) {
		this.person = person;
	}

	@Override
	public void execute() {
		Location location = person.getLocation();
		if (location.getMineable().isPresent()) {
			location.getMineable().get().mine(person);
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public Goal getGoal() {
		return null;
	}
}
