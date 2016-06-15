package se.persandstrom.ploxworld.ship;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class BuyableItem {


	public final String name;
	public final int purchaseCost;
	public final int researchCost;

	public BuyableItem(String name, int purchaseCost, int researchCost) {
		this.name = name;
		this.purchaseCost = purchaseCost;
		this.researchCost = researchCost;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}

	public int getResearchCost() {
		return researchCost;
	}

	public int getSellCost() {
		return purchaseCost;
	}

	@Override
	public String toString() {
		return name;
	}

	public static List<? extends BuyableItem> getItemsOfType(BuyableItem item) {
		if (item instanceof Weapon) {
			return Weapon.weapons;
		} else if (item instanceof ShipBase) {
			return ShipBase.shipBases;
		} else {
			throw new IllegalStateException();
		}
	}

	public static Optional<BuyableItem> getMissingItemEasistResearched(Set<? extends BuyableItem> heldItems) {
		List<? extends BuyableItem> existingItems = getItemsOfType(heldItems.iterator().next());
		Optional<? extends BuyableItem> first = existingItems.stream()
				.filter(item -> heldItems.contains(item) == false)
				.sorted((o1, o2) -> o1.researchCost - o2.researchCost)
				.findFirst();
		//TODO fult
		if (first.isPresent()) {
			return Optional.of(first.get());
		} else {
			return Optional.empty();
		}
	}

	public static Optional<BuyableItem> getNextItem(BuyableItem currentItem) {
		List<? extends BuyableItem> existingItems = getItemsOfType(currentItem);
		Optional<? extends BuyableItem> first = existingItems.stream()
				.filter(weapon -> weapon.purchaseCost > currentItem.purchaseCost)
				.sorted((o1, o2) -> o1.purchaseCost - o2.purchaseCost)
				.findFirst();
		//TODO fult
		if (first.isPresent()) {
			return Optional.of(first.get());
		} else {
			return Optional.empty();
		}
	}
}
