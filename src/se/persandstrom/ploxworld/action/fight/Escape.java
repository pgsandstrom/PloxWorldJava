package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.fight.Combatant;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.WorldData;

public class Escape implements Action {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	public Escape(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		fight.addDistance(1);
		if (fight.getDistance() >= 10 || Rand.roll(0.1)) {
			acter.setStillFighting(false);
			Log.fight(acter.getPerson().getName() + " escaped from combat!");
		}
	}

	@Override
	public void saveData(WorldData worldData) {
	}
}
