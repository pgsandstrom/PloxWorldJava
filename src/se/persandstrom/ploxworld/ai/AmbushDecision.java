package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.person.Person;

public class AmbushDecision implements Decision {

	private final Person person;

	public AmbushDecision(Person person) {
		this.person = person;
	}

	@Override
	public void execute() {
		//TODO
	}
}
