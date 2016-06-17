package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.fight.Combatant;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.WorldData;

public class MoveForward implements Action {

	private final Fight fight;
	private final Combatant acter;
	private final Combatant receiver;

	public MoveForward(Fight fight, Combatant acter, Combatant receiver) {
		this.fight = fight;
		this.acter = acter;
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		fight.addDistance(-1);
	}

	@Override
	public void saveData(WorldData worldData) {
	}
}
