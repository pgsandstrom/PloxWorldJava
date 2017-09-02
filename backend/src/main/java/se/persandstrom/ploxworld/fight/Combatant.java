package se.persandstrom.ploxworld.fight;

import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

import com.google.gson.annotations.Expose;

public class Combatant {

	@Expose private final Person person;
	@Expose private final Ship ship;
	private boolean stillFighting = true;

	public Combatant(Person person) {
		this.person = person;
		this.ship = person.getShip();
	}

	public Person getPerson() {
		return person;
	}

	public Ship getShip() {
		return ship;
	}

	@Override
	public String toString() {
		return person.getName();
	}

	public boolean isStillFighting() {
		return stillFighting;
	}

	public void setStillFighting(boolean stillFighting) {
		this.stillFighting = stillFighting;
	}
}
