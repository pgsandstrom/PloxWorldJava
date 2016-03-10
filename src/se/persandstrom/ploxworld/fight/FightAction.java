package se.persandstrom.ploxworld.fight;

public enum FightAction {

	MOVE_FORWARD, MOVE_BACKWARD, ESCAPE, WAIT, FIRE;

	public boolean isMovement() {
		return this == MOVE_FORWARD || this == MOVE_BACKWARD;
	}
}
