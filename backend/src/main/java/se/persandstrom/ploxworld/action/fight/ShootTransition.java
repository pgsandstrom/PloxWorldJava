package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.fight.Fight;

import com.google.gson.annotations.Expose;

public class ShootTransition implements FightTransition {

	@Expose private final FightAction.ActionType actionType;
	@Expose private int startDistance;
	@Expose private int finishDistance;

	public ShootTransition(Fight fight, FightAction.ActionType actionType) {
		startDistance = fight.getDistance();
		finishDistance = fight.getDistance();
		this.actionType = actionType;

		if (actionType == FightAction.ActionType.MOVE_FORWARD) {
			startDistance++;
		} else if (actionType == FightAction.ActionType.MOVE_BACKWARD || actionType == FightAction.ActionType.ESCAPE) {
			startDistance--;
		}
	}
}
