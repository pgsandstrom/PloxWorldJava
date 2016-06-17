package se.persandstrom.ploxworld.fight;

import java.util.ArrayList;
import java.util.List;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.action.fight.Wait;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;

public class Combatant {
	private final Person person;
	private final Ship ship;
	private final List<Action> fightActions = new ArrayList<>();
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

	public List<Action> getFightActions() {
		return fightActions;
	}

	public void addAction(Action Action) {
		fightActions.add(Action);
	}

	public Action getLastMove() {
		if (fightActions.size() == 0) {
			return new Wait();
		} else {
			return fightActions.get(fightActions.size() - 1);
		}
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
