package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class AcceptThreat {

	private final Person aggressor;
	private final Person victim;

	public AcceptThreat( Person aggressor, Person victim) {
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
		System.out.println(aggressor + " is BOARDING " + victim + "!!!");
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

		for (ProductionType productionType : ProductionType.values()) {
			int storage = victim.getShip().getStorage(productionType);
			int payment = (int) (storage * quote);
			victim.getShip().addStorage(productionType, -payment);
			aggressor.getShip().addStorage(productionType, payment);

			System.out.println(victim + " paying " + payment + " of " + storage + " " + productionType + " to " + aggressor);
		}
	}
}