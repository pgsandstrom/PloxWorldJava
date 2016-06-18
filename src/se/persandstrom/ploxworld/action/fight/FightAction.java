package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.fight.Combatant;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.ship.Weapon;

public class FightAction implements Action {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	private ActionType actionType;

	public FightAction(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;

		actionType = makeDecision();
	}

	public ActionType makeDecision() {
		if (acter.getPerson().getAi() == null) {
			return null;
		}

		double acterPower = acter.getShip().getPower();
		double receiverPower = receiver.getShip().getPower();

		if (acterPower / receiverPower > 0.9) {
			return makeAggressiveMove();
		} else {
			return makeDefensiveMove();
		}
	}

	private ActionType makeAggressiveMove() {
		double accuracy = fight.getAccuracy(acter);
		if (accuracy < 0.5 && fight.getDistance() > 1) {
			return ActionType.MOVE_FORWARD;
		} else {
			return ActionType.FIRE;
		}
	}

	private ActionType makeDefensiveMove() {
		return ActionType.ESCAPE;
	}

	@Override
	public void execute() {
		switch (actionType) {
			case FIRE:
				fire();
				return;
			case MOVE_FORWARD:
				moveForward();
				return;
			case MOVE_BACKWARD:
				moveBackward();
				return;
			case WAIT:
				waitLol();
				return;
			case ESCAPE:
				escape();
				return;
			default:
				throw new IllegalStateException();
		}
	}

	private void escape() {
		fight.addDistance(1);
		if (fight.getDistance() >= 10 || Rand.roll(0.1)) {
			acter.setStillFighting(false);
			Log.fight(acter.getPerson().getName() + " escaped from combat!");
		}
	}

	public void moveBackward() {
		fight.addDistance(1);
	}

	public void moveForward() {
		fight.addDistance(-1);
	}

	public void waitLol() {
		// do nothing...
	}

	public void fire() {
		Weapon weapon = acter.getShip().getWeapon();
		boolean hit = hitOrMiss(weapon);
		if (hit) {
			int damage = weapon.rollDamage();
			receiver.getShip().damage(damage);
			if (receiver.getShip().isDead()) {
				receiver.getPerson().setAlive(false);
				receiver.setStillFighting(false);
			}
		}
	}


	private boolean hitOrMiss(Weapon weapon) {
		return Rand.roll(fight.getAccuracy(weapon));
	}

	@Override
	public void saveData(WorldData worldData) {
		//XXX NOT IMPLEMENTED
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public enum ActionType {
		FIRE, MOVE_BACKWARD, MOVE_FORWARD, WAIT, ESCAPE;
	}
}
