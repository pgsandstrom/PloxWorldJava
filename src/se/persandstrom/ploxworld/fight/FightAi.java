package se.persandstrom.ploxworld.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.action.fight.Escape;
import se.persandstrom.ploxworld.action.fight.Fire;
import se.persandstrom.ploxworld.action.fight.MoveForward;

public class FightAi {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	public FightAi(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;
	}

	public Action makeDecision() {
		double acterPower = acter.getShip().getPower();
		double receiverPower = receiver.getShip().getPower();

		if (acterPower / receiverPower > 0.9) {
			return makeAggressiveMove();
		} else {
			return makeDefensiveMove();
		}
	}

	private Action makeAggressiveMove() {
		double accuracy = fight.getAccuracy(acter);
		if (accuracy < 0.5 && fight.getDistance() > 1) {
			return new MoveForward(fight, acter, receiver);
		} else {
			return new Fire(fight, acter, receiver);
		}
	}

	private Action makeDefensiveMove() {
		return new Escape(fight, acter, receiver);
	}
}
