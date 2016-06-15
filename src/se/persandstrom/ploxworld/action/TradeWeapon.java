package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.BuyableItem;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TradeWeapon implements Action {

	private final Person person;
	private final Civilization civilization;
	private final BuyableItem item;
	private final TransferType type;

	public TradeWeapon(Person person, Civilization civilization, BuyableItem item, TransferType type) {
		this.person = person;
		this.civilization = civilization;
		this.item = item;
		this.type = type;
	}

	@Override
	public void execute() {
		switch (type) {
			case BUY:
				//auto sell item:
				person.addMoney(person.getShip().getItemOfType(item).getSellCost());
				person.addMoney(-item.purchaseCost);
				civilization.getTradeable().addMoney(item.purchaseCost);

				person.getShip().setItem(item);
				break;
			case SELL:
				//TODO
				throw new NotImplementedException();
		}

	}

	@Override
	public void saveData(WorldData worldData) {
		worldData.addDataValue(WorldData.KEY_WEAPONS_BOUGHT, 1);
	}
}
