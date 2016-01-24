package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.person.Person;

public class Fight {

	private final Person first;
	private final Person second;

	public Fight(Person first, Person second) {
		this.first = first;
		this.second = second;
	}

	public void start() {
		System.out.println("fight between " + first + " and " + second);
	}

}
