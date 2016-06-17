package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.fight.Combatant;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.ship.Weapon;

public class Fire implements Action {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	public Fire(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;
	}

	@Override
	public void execute() {
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

	private boolean hitOrMiss(Weapon weapon, Action lastMove) {
		return Rand.roll(fight.getAccuracy(weapon, lastMove));
	}

	@Override
	public void saveData(WorldData worldData) {
	}
}
