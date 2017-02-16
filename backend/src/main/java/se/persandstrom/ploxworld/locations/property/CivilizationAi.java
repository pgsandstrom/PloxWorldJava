package se.persandstrom.ploxworld.locations.property;

import java.util.Optional;
import java.util.Set;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.ship.BuyableItem;
import se.persandstrom.ploxworld.ship.Weapon;

import static se.persandstrom.ploxworld.ship.BuyableItem.getMissingItemEasistResearched;

public class CivilizationAi {

	static void purchase(Civilization civilization) {
		//TODO: choose weapon or shipbase intelligently

		Set<? extends BuyableItem> items;
		if (Rand.roll()) {
			items = civilization.getWeapons();
		} else {
			items = civilization.getShipBases();
		}

		Optional<BuyableItem> missingItem = getMissingItemEasistResearched(items);
		if (missingItem.isPresent()) {
			BuyableItem item = missingItem.get();
			if (item.researchCost < civilization.getTradeable().getScience().getStorage()) {
				civilization.getTradeable().getScience().addStorage(-item.researchCost);
				civilization.addItem(item);
				Log.planet(civilization + " bought " + item);
			}
		}
	}
}
