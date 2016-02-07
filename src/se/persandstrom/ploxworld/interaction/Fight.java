package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Ship;
import se.persandstrom.ploxworld.ship.Weapon;

public class Fight {

	private final Combatant first;
	private final Combatant second;

	private int distance = 6;

	public Fight(Person first, Person second) {
		this.first = new Combatant(first, first.getShip());
		this.second = new Combatant(second, second.getShip());
	}

	public void start() {
		Log.pirate("fight between " + first + " and " + second);
		//TODO currently both are aggressive

		while (true) {
			if (first.person.isAlive() && first.person.isAlive()) {
				makeAggressiveMove(first, second);
			}
			if (first.person.isAlive() && first.person.isAlive()) {
				makeAggressiveMove(second, first);
			}

			if (first.person.isAlive() == false) {
				Log.fight(first.person.getName() + " DIED in combat!!!");
				new TransferResources(second.person, first.person).execute(0.8);
				return;
			}

			if (second.person.isAlive() == false) {
				Log.fight(second.person.getName() + " DIED in combat!!!");
				new TransferResources(first.person, second.person).execute(0.8);
				return;
			}
		}
	}

	private void makeAggressiveMove(Combatant person, Combatant otherPerson) {
		fire(person, otherPerson);
		double accuracy = getAccuracy(person.ship, person.lastMove);
		if (accuracy < 0.8 && distance > 1) {
			moveForward();
		}
	}

	private void moveForward() {
		distance -= 1;
	}

	private void fire(Combatant aggressor, Combatant receiver) {
		Weapon weapon = aggressor.ship.getWeapon();
		boolean hit = hitOrMiss(weapon, aggressor.lastMove);
		if (hit) {
			int damage = weapon.rollDamage();
			receiver.ship.damage(damage);
			if (receiver.ship.isDead()) {
				receiver.person.setAlive(false);
			}
		}
	}

	private double getAccuracy(Ship ship, LastMove lastMove) {
		return getAccuracy(ship.getWeapon(), lastMove);
	}

	private boolean hitOrMiss(Weapon weapon, LastMove lastMove) {
		return Rand.roll(getAccuracy(weapon, lastMove));
	}

	private double getAccuracy(Weapon weapon, LastMove lastMove) {
		return weapon.accuracy - distance * 0.05 + (lastMove == LastMove.STILL ? 0.05 : 0);
	}

	private class Combatant {
		private final Person person;
		private final Ship ship;
		private LastMove lastMove = LastMove.MOVE;

		public Combatant(Person person, Ship ship) {
			this.person = person;
			this.ship = ship;
		}

		@Override
		public String toString() {
			return person.getName();
		}
	}

	private enum LastMove {
		MOVE, STILL
	}
}
