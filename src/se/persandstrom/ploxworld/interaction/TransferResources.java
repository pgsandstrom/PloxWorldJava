package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class TransferResources {

	private final Person aggressor;
	private final Person victim;

	public TransferResources(Person aggressor, Person victim) {
		this.aggressor = aggressor;
		this.victim = victim;
	}

	public void execute(double quote) {
		for (ProductionType productionType : ProductionType.values()) {
			int storage = victim.getShip().getStorage(productionType);
			int payment = (int) (storage * quote);
			victim.getShip().addStorage(productionType, -payment);
			aggressor.getShip().addStorage(productionType, payment);

			Log.dialog(victim + " transferring " + payment + " of " + storage + " " + productionType + " to " + aggressor);
		}
	}
}
