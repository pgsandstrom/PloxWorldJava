package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class TransferGoods implements Action {

	private final Person aggressor;
	private final Person victim;
	private final double quote;

	private int totalTransfered = 0;

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

			totalTransfered += amount;

			Log.dialog(victim + " transferring " + amount + " of " + storage + " " + productionType + " to " + aggressor);
		}
	}

	@Override
	public Person getActor() {
		return aggressor;
	}

	@Override
	public void saveData(WorldData worldData) {
		worldData.addDataValue(WorldData.KEY_GOODS_STOLEN, totalTransfered);
	}

	@Override
	public void setDecision(String decision) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isDecided() {
		return true;
	}
}