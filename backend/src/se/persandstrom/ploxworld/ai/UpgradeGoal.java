package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.action.TradeItem;
import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.BuyableItem;

public class UpgradeGoal implements Goal {

	private final BuyableItem buyableItem;

	public UpgradeGoal(BuyableItem buyableItem) {
		this.buyableItem = buyableItem;
	}

	@Override
	public void execute(World world, Person person) {
		Location location = person.getLocation();
		if (location.getCivilization().isPresent()) {
			Civilization civilization = location.getCivilization().get();
			if (civilization.getBuyableItems().contains(buyableItem) == false) {
				throw new IllegalStateException();
			}

			world.executeAction(new TradeItem(person, civilization, buyableItem, TransferType.BUY));
			//TODO: Sell old weapon. This requires inventory and lots of stuff
		}
	}
}
