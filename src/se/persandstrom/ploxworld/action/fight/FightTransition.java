package se.persandstrom.ploxworld.action.fight;

import com.google.gson.annotations.Expose;

public class FightTransition {

	@Expose private final FightAction.ActionType actionType;
	@Expose private int damage;

	public FightTransition(FightAction.ActionType actionType) {
		this.actionType = actionType;
	}

	public FightTransition(FightAction.ActionType actionType, int damage) {
		this.actionType = actionType;
		this.damage = damage;
	}
}
