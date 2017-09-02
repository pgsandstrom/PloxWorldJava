package se.persandstrom.ploxworld.action.fight;

import com.google.gson.annotations.Expose;

public class MoveTransition implements FightTransition {

	@Expose private final int startDistance;
	@Expose private final int finishDistance;

	public MoveTransition(int startDistance, int finishDistance) {
		this.startDistance = startDistance;
		this.finishDistance = finishDistance;

	}
}
