package se.persandstrom.ploxworld.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.action.TransferGoods;
import se.persandstrom.ploxworld.action.fight.MoveBackward;
import se.persandstrom.ploxworld.action.fight.MoveForward;
import se.persandstrom.ploxworld.common.Log;
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
				Action fightAction = new FightAi(this, first, second).makeDecision();
				world.executeAction(fightAction);
			}
			if (isFightOngoing()) {
				Action fightAction = new FightAi(this, second, first).makeDecision();
				world.executeAction(fightAction);
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

	public double getAccuracy(Combatant combatant) {
		return getAccuracy(combatant.getShip().getWeapon(), combatant.getLastMove());
	}

	public int getDistance() {
		return distance;
	}

	public void addDistance(int distanceDelta) {
		this.distance += distanceDelta;
	}

	public double getAccuracy(Weapon weapon, Action lastMove) {
		// fulkodat...
		return weapon.accuracy - distance * 0.05 + (lastMove instanceof MoveForward || lastMove instanceof MoveBackward ? 0 : 0.05);
	}

	private boolean isFightOngoing() {
		return first.isStillFighting() && second.isStillFighting();
	}
}
