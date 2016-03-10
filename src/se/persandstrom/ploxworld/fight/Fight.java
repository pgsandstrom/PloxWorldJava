package se.persandstrom.ploxworld.fight;

import se.persandstrom.ploxworld.action.TransferGoods;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

public class Fight {

	private final World world;
	private final Combatant first;
	private final Combatant second;

	private int distance = 6;

	public Fight(World world, Person first, Person second) {
		this.world = world;
		this.first = new Combatant(first);
		this.second = new Combatant(second);
	}

	public void start() {
		Log.pirate("fight between " + first + " and " + second);

		while (true) {
			if (isFightOngoing()) {
				FightAction fightAction = new FightAi(this, first, second).makeDecision();
				applyAction(fightAction, first, second);
			}
			if (isFightOngoing()) {
				FightAction fightAction = new FightAi(this, second, first).makeDecision();
				applyAction(fightAction, second, first);
			}

			if (first.getPerson().isAlive() == false) {
				Log.fight(first.getPerson().getName() + " DIED in combat!!!");
				world.executeAction(new TransferGoods(second.getPerson(), first.getPerson(), 0.8));
				return;
			}

			if (second.getPerson().isAlive() == false) {
				Log.fight(second.getPerson().getName() + " DIED in combat!!!");
				world.executeAction(new TransferGoods(first.getPerson(), second.getPerson(), 0.8));
				return;
			}

			if (isFightOngoing() == false) {
				return;
			}
		}
	}

	private void applyAction(FightAction fightAction, Combatant acter, Combatant receiver) {
		switch (fightAction) {
			case MOVE_FORWARD:
				moveForward();
				break;
			case MOVE_BACKWARD:
				moveBackward();
				break;
			case ESCAPE:
				escape(acter);
				break;
			case FIRE:
				fire(acter, receiver);
				break;
			case WAIT:
				break;
		}
	}

	private void moveForward() {
		distance -= 1;
	}

	private void moveBackward() {
		distance -= 1;
	}

	private void escape(Combatant acter) {
		moveBackward();
		if (distance >= 10) {
			acter.setStillFighting(false);
		} else if (Rand.roll(0.1)) {
			acter.setStillFighting(false);
		}
	}

	private void fire(Combatant acter, Combatant receiver) {
		Weapon weapon = acter.getShip().getWeapon();
		boolean hit = hitOrMiss(weapon, acter.getLastMove());
		if (hit) {
			int damage = weapon.rollDamage();
			receiver.getShip().damage(damage);
			if (receiver.getShip().isDead()) {
				receiver.getPerson().setAlive(false);
				receiver.setStillFighting(false);
			}
		}
	}

	public double getAccuracy(Combatant combatant) {
		return getAccuracy(combatant.getShip().getWeapon(), combatant.getLastMove());
	}

	public int getDistance() {
		return distance;
	}

	private boolean hitOrMiss(Weapon weapon, FightAction lastMove) {
		return Rand.roll(getAccuracy(weapon, lastMove));
	}

	private double getAccuracy(Weapon weapon, FightAction lastMove) {
		return weapon.accuracy - distance * 0.05 + (lastMove.isMovement() ? 0 : 0.05);
	}

	private boolean isFightOngoing() {
		return first.isStillFighting() && second.isStillFighting();
	}
}
