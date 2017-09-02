package se.persandstrom.ploxworld.fight;

import se.persandstrom.ploxworld.action.TransferGoods;
import se.persandstrom.ploxworld.action.fight.FightAction;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

import com.google.gson.annotations.Expose;

public class Fight {

	private final World world;
	private final Combatant first;
	private final Combatant second;

	@Expose private int distance = 6;

	public Fight(World world, Person first, Person second) {
		this.world = world;
		this.first = new Combatant(first);
		this.second = new Combatant(second);
	}

	public void start() {
		Log.pirate("fight between " + first + " and " + second);

		while (true) {
			if (isFightOngoing()) {
				FightAction fightAction = new FightAction(this, first, second);
				world.executeAction(fightAction);
			}
			if (isFightOngoing()) {
				FightAction fightAction = new FightAction(this, second, first);
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

	public double getAccuracy(Combatant shooter) {
		return getAccuracy(shooter.getShip().getWeapon());
	}

	public int getDistance() {
		return distance;
	}

	public void addDistance(int distanceDelta) {
		this.distance += distanceDelta;
	}

	public double getAccuracy(Weapon weapon) {
		return weapon.accuracy - distance * 0.05;
	}

	private boolean isFightOngoing() {
		return first.isStillFighting() && second.isStillFighting();
	}
}
