package se.persandstrom.ploxworld.fight;

public class FightAi {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	public FightAi(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;
	}

	public FightAction makeDecision() {
		double acterPower = acter.getShip().getPower();
		double receiverPower = receiver.getShip().getPower();

		if (acterPower / receiverPower > 0.9) {
			return makeAggressiveMove();
		} else {
			return makeDefensiveMove();
		}
	}

	private FightAction makeAggressiveMove() {
		double accuracy = fight.getAccuracy(acter);
		if (accuracy < 0.5 && fight.getDistance() > 1) {
			return FightAction.MOVE_FORWARD;
		} else {
			return FightAction.FIRE;
		}
	}

	private FightAction makeDefensiveMove() {
		return FightAction.ESCAPE;
	}
}
