package se.persandstrom.ploxworld.action.ambush;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.interaction.Ambush;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public class AmbushAction implements Action {

	private final Ambush ambush;
	private final Person acter;
	private final Person receiver;

	private final double powerRatio;

	public AmbushAction(Ambush ambush, Person acter, Person receiver) {
		this.ambush = ambush;
		this.acter = acter;
		this.receiver = receiver;
		this.powerRatio = acter.getShip().getPower() / receiver.getShip().getPower();
	}

	@Override
	public void execute() {
		AmbushType reaction = react();
		ambush.setAmbushType(reaction);
		Log.fight("Ambush. " + acter + " decides to " + reaction);
	}

	private AmbushType react() {
		double aggressionRoll = acter.getPersonality().getAggressionRoll(powerRatio);

		if (aggressionRoll > 100) {
			return AmbushType.ATTACK;
		} else if (aggressionRoll > 50) {
			return AmbushType.DIALOG;
		} else {
			return AmbushType.LEAVE;
		}
	}

	@Override
	public void saveData(WorldData worldData) {
		//XXX NOT IMPLEMENTED
	}

	public enum AmbushType {
		ATTACK, DIALOG, LEAVE
	}
}
