package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.person.Person;

public class Dialog {

	private final Person aggressor;
	private final Person victim;

	public Dialog(Person aggressor, Person victim) {
		this.aggressor = aggressor;
		this.victim = victim;
	}

	public void start() {
		Threat threat = getThreat();

		Log.dialog("");
		Log.dialog(aggressor + " is threatening " + victim + " with " + threat);
		switch (threat) {
			case BOARD:
			case GIVE_ALL:
			case GIVE_RANSOM:
				ReactThreat reactThreat = reactThreat(threat);
				Log.dialog(victim + " decides to " + reactThreat);
				switch (reactThreat) {
					case ACCEPT:
						new AcceptThreat(aggressor, victim).start(threat);
					case REFUSE:
						ReactThreatRefusal reactThreatRefusal = reactThreatRefusal();
						Log.dialog(aggressor + " reacts to refusal with " + reactThreatRefusal);
						switch (reactThreatRefusal) {
							case ATTACK:
								new Fight(aggressor, victim).start();
							case LEAVE:
								return;
						}
				}
			case LEAVE:
				ReactLeave reactLeave = reactLeave();
				Log.dialog(victim + " reacts to leaving with " + reactLeave);
				switch (reactLeave) {
					case ATTACK:
						new Fight(victim, aggressor).start();
						return;
					case LEAVE:
						return;
				}
		}

	}


	public Threat getThreat() {
		int aggressionRoll = aggressor.getPersonality().getAggressionRoll();
		if (aggressionRoll > 90) {
			return Threat.BOARD;
		} else if (aggressionRoll > 80) {
			return Threat.GIVE_ALL;
		} else if (aggressionRoll > 50) {
			return Threat.GIVE_RANSOM;
		} else {
			return Threat.LEAVE;
		}
	}

	private ReactThreat reactThreat(Threat threat) {
		int aggressionRoll = victim.getPersonality().getAggressionRoll();
		int choiceLimit;
		switch (threat) {
			case BOARD:
				choiceLimit = 10;
				break;
			case GIVE_ALL:
				choiceLimit = 20;
				break;
			case GIVE_RANSOM:
				choiceLimit = 50;
				break;
			default:
				throw new IllegalStateException();
		}
		if (aggressionRoll > choiceLimit) {
			return ReactThreat.REFUSE;
		} else {
			return ReactThreat.ACCEPT;
		}
	}

	private ReactLeave reactLeave() {
		return ReactLeave.LEAVE;
	}

	private ReactThreatRefusal reactThreatRefusal() {
		return ReactThreatRefusal.LEAVE;
	}

	public enum Threat {
		GIVE_ALL, GIVE_RANSOM, BOARD, LEAVE
	}

	public enum ReactThreat {
		ACCEPT, REFUSE
	}

	public enum ReactLeave {
		ATTACK, LEAVE
	}

	public enum ReactThreatRefusal {
		ATTACK, LEAVE
	}
}