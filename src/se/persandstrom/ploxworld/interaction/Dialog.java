package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class Dialog {

	private final World world;
	private final Person aggressor;
	private final Person victim;

	private final double powerRatio;

	public Dialog(World world, Person aggressor, Person victim) {
		this.world = world;
		this.aggressor = aggressor;
		this.victim = victim;
		powerRatio = aggressor.getShip().getPower() / victim.getShip().getPower();
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
						new AcceptThreat(world, aggressor, victim).start(threat);
					case REFUSE:
						ReactThreatRefusal reactThreatRefusal = reactThreatRefusal();
						Log.dialog(aggressor + " reacts to refusal with " + reactThreatRefusal);
						switch (reactThreatRefusal) {
							case ATTACK:
								new Fight(world, aggressor, victim).start();
							case LEAVE:
								return;
						}
				}
			case LEAVE:
				ReactLeave reactLeave = reactLeave();
				Log.dialog(victim + " reacts to leaving with " + reactLeave);
				switch (reactLeave) {
					case ATTACK:
						new Fight(world, victim, aggressor).start();
						return;
					case LEAVE:
						return;
				}
		}

	}


	public Threat getThreat() {
		double aggressionRoll = aggressor.getPersonality().getAggressionRoll(powerRatio);
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
		double aggressionRoll = victim.getPersonality().getAggressionRoll(powerRatio);
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
