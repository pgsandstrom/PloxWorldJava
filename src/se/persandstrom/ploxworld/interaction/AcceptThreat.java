package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.action.TransferGoods;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class AcceptThreat {

	private final World world;
	private final Person aggressor;
	private final Person victim;

	public AcceptThreat(World world, Person aggressor, Person victim) {
		this.world = world;
		this.victim = victim;
		this.aggressor = aggressor;
	}

	public void start(Dialog.Threat threat) {

		if (threat == Dialog.Threat.BOARD) {
			board();
		} else {
			pay(threat);
		}
	}

	private void board() {
		Log.pirate(aggressor + " is BOARDING " + victim + "!!!");
	}

	private void pay(Dialog.Threat threat) {
		double quote;
		if (threat == Dialog.Threat.GIVE_ALL) {
			quote = 1;
		} else if (threat == Dialog.Threat.GIVE_RANSOM) {
			quote = 0.25;
		} else {
			throw new IllegalStateException();
		}
		world.executeAction(new TransferGoods(aggressor, victim, quote));
	}
}
