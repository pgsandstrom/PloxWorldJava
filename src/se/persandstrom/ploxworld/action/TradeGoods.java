package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.property.Tradeable;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class TradeGoods implements Action {

	private final Person person;
	private final Tradeable tradeable;
	private final int amount;
	private final ProductionType productionType;
	private final TransferType type;

	public TradeGoods(Person person, Tradeable tradeable, int amount, ProductionType productionType, TransferType type) {
		this.person = person;
		this.tradeable = tradeable;
		this.amount = amount;
		this.productionType = productionType;
		this.type = type;
	}

	@Override
	public void execute() {
		if (type == TransferType.BUY) {
			int cost = tradeable.buyFrom(productionType, amount);
			person.getShip().addStorage(productionType, amount);
			person.addMoney(-cost);
			Log.person(person + " bought " + amount + " " + productionType + " from " + tradeable.getLocation()
					+ " for " + tradeable.getProduction(productionType).getBuyPrice() + " each. In total:  " + cost);
		} else if (type == TransferType.SELL) {
			int cost = tradeable.sellTo(productionType, amount);
			person.getShip().addStorage(productionType, -amount);
			person.addMoney(cost);
			Log.person(person + " sold " + amount + " " + productionType + " to " + tradeable
					+ " for " + tradeable.getProduction(productionType).getSellPrice() + " each. In total:  " + cost);
		} else {
			throw new IllegalStateException();
		}

	}

	@Override
	public void saveData(WorldData worldData) {
		worldData.addDataValue(WorldData.KEY_GOODS_TRADED, amount);
	}

	@Override
	public boolean isDecided() {
		return true;
	}

	@Override
	public void setDecision(String decision) {
		throw new IllegalStateException();
	}
}