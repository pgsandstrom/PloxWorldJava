package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.fight.Fight;

import com.google.gson.annotations.Expose;

public class FightTransition {

	@Expose private final FightAction.ActionType actionType;
	@Expose private final int damage;
	@Expose private int startDistance;
	@Expose private int finishDistance;

	public FightTransition(Fight fight, FightAction.ActionType actionType, int damage) {
		startDistance = fight.getDistance();
		finishDistance = fight.getDistance();
		this.actionType = actionType;
		this.damage = damage;

		if (actionType == FightAction.ActionType.MOVE_FORWARD) {
			finishDistance--;
		} else if (actionType == FightAction.ActionType.MOVE_BACKWARD) {
			finishDistance++;
		}
	}
}
