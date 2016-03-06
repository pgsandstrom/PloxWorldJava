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
			int amount = (int) (storage * quote);
			victim.getShip().addStorage(productionType, -amount);
			aggressor.getShip().addStorage(productionType, amount);

			Log.dialog(victim + " transferring " + amount + " of " + storage + " " + productionType + " to " + aggressor);
		}
	}
}