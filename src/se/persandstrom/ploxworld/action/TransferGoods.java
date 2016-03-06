package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class TransferGoods implements Action {

	private final Person aggressor;
	private final Person victim;
	private final double quote;

	public TransferGoods(Person aggressor, Person victim, double quote) {
		this.aggressor = aggressor;
		this.victim = victim;
		this.quote = quote;
	}

	@Override
	public void execute() {
		for (ProductionType productionType : ProductionType.values()) {
			int storage = victim.getShip().getStorage(productionType);
			int payment = (int) (storage * quote);
			victim.getShip().addStorage(productionType, -payment);
			aggressor.getShip().addStorage(productionType, payment);

			Log.dialog(victim + " transferring " + payment + " of " + storage + " " + productionType + " to " + aggressor);
		}
	}
}