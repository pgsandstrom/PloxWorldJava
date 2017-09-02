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

	AmbushType reaction;

	public AmbushAction(Ambush ambush, Person acter, Person receiver) {
		this.ambush = ambush;
		this.acter = acter;
		this.receiver = receiver;
		this.powerRatio = acter.getShip().getPower() / receiver.getShip().getPower();

		reaction = react();
	}

	@Override
	public void execute() {
		ambush.setAmbushType(reaction);
		Log.fight("Ambush. " + acter + " decides to " + reaction);
	}

	@Override
	public Person getActor() {
		return acter;
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

	@Override
	public boolean isDecided() {
		return reaction != null;
	}

	@Override
	public void setDecision(String decision) {
		reaction = AmbushType.valueOf(decision);
	}

	public enum AmbushType {
		ATTACK, DIALOG, LEAVE
	}
}
