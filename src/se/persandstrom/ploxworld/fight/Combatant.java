package se.persandstrom.ploxworld.fight;

import java.util.ArrayList;
import java.util.List;

import se.persandstrom.ploxworld.action.fight.FightTransition;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

import com.google.gson.annotations.Expose;

public class Combatant {
	private final Person person;
	@Expose private final Ship ship;
	@Expose private final List<FightTransition> unseenTransitions = new ArrayList<>();
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

	public void clearUnseenTransitions() {
		unseenTransitions.clear();
	}

	public void addUnseenTransition(FightTransition transition) {
		unseenTransitions.add(transition);
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
