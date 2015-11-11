package se.persandstrom.ploxworld.ship;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import se.persandstrom.ploxworld.production.ProductionType;

public class Ship {

	public static final int MAX_STORAGE = 50;

	private Map<ProductionType, Integer> storage = new HashMap<>();

	public Ship() {
		for (ProductionType productionType : ProductionType.values()) {
			storage.put(productionType, 0);
		}
	}

	public void addStorage(ProductionType productionType, int amount) {
		storage.compute(productionType, (productionType1, integer) -> integer + amount);
	}

	public int getMaxStorage() {
		return MAX_STORAGE;
	}

	public int getStorage(ProductionType productionType) {
		return storage.get(productionType);
	}

	public int getFreeStorage() {
		return MAX_STORAGE - storage.entrySet().stream()
				.mapToInt(Map.Entry::getValue).sum();
	}

	public ProductionType getLargestProductionStorage() {
		Optional<Map.Entry<ProductionType, Integer>> max = storage.entrySet().stream()
				.max((o1, o2) -> o1.getValue() - o2.getValue());
		return max.get().getKey();
	}

}
