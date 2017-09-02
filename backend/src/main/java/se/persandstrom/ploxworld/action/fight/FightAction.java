package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.action.Transition;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.fight.Combatant;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

import com.google.gson.annotations.Expose;

public class FightAction implements Action {

	@Expose private final Fight fight;
	@Expose private final Combatant acter;
	@Expose private final Combatant receiver;

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
				break;
			case MOVE_FORWARD:
				moveForward();
				break;
			case MOVE_BACKWARD:
				moveBackward();
				break;
			case WAIT:
				waitLol();
				break;
			case ESCAPE:
				escape();
				break;
			default:
				throw new IllegalStateException();
		}
	}

	@Override
	public Person getActor() {
		return acter.getPerson();
	}

	private void escape() {
		fight.addDistance(1);
		if (fight.getDistance() >= 10 || Rand.roll(0.1)) {
			acter.setStillFighting(false);
			Log.fight(acter.getPerson().getName() + " escaped from combat!");
		}
		// TODO
	}

	public void moveBackward() {
		int distanceChange = 1;
		acter.getPerson().addUnseenTransition(new MoveTransition(acter.getPerson(), fight.getDistance(), fight.getDistance() + distanceChange));
		receiver.getPerson().addUnseenTransition(new MoveTransition(acter.getPerson(), fight.getDistance(), fight.getDistance() + distanceChange));
		fight.addDistance(distanceChange);
	}

	public void moveForward() {
		int distanceChange = -1;
		acter.getPerson().addUnseenTransition(new MoveTransition(acter.getPerson(), fight.getDistance(), fight.getDistance() + distanceChange));
		receiver.getPerson().addUnseenTransition(new MoveTransition(acter.getPerson(), fight.getDistance(), fight.getDistance() + distanceChange));
		fight.addDistance(distanceChange);
	}

	public void waitLol() {
		// do nothing...
		// TODO add transition
	}

	public void fire() {
		Weapon weapon = acter.getShip().getWeapon();
		boolean hit = hitOrMiss(weapon);
		int damage = 0;
		if (hit) {
			damage = weapon.rollDamage();
			receiver.getShip().damage(damage);
			if (receiver.getShip().isDead()) {
				receiver.getPerson().setAlive(false);
				receiver.setStillFighting(false);
			}
		}
		acter.getPerson().addUnseenTransition(new ShootTransition(acter.getPerson(), hit, damage));
		receiver.getPerson().addUnseenTransition(new ShootTransition(acter.getPerson(), hit, damage));
	}


	private boolean hitOrMiss(Weapon weapon) {
		return Rand.roll(fight.getAccuracy(weapon));
	}

	@Override
	public void saveData(WorldData worldData) {
		//XXX NOT IMPLEMENTED
	}

	@Override
	public boolean isDecided() {
		return actionType != null;
	}

	@Override
	public void setDecision(String decision) {
		actionType = ActionType.valueOf(decision);
	}

	public enum ActionType {
		FIRE, MOVE_BACKWARD, MOVE_FORWARD, WAIT, ESCAPE;
	}
}
