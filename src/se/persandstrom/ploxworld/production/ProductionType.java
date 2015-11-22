package se.persandstrom.ploxworld.production;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.persandstrom.ploxworld.common.Rand;

public enum ProductionType {
	COMMODITY("commodity"), CONSTRUCTION("construction"), CRYSTAL("crystal"), MATERIAL("material"), SCIENCE("science");

	private static final List<ProductionType> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();

	private final String name;

	ProductionType(String name) {
		this.name = name;
	}

	public static Production getProduction(ProductionType productionType) {
		switch (productionType) {
			case COMMODITY:
				return new Commodity();
			case MATERIAL:
				return new Material();
			case CONSTRUCTION:
				return new Construction();
			case CRYSTAL:
				return new Crystal();
			case SCIENCE:
				return new Science();
			default:
				throw new AssertionError();
		}
	}

	public static ProductionType getRandomBaseProduct() {
		// ugly code... dont buy/sell "end products"
		ProductionType randomGoods;
		do {
			randomGoods = ProductionType.getRandom();
		} while (randomGoods == ProductionType.CONSTRUCTION || randomGoods == ProductionType.SCIENCE);
		return randomGoods;
	}

	public static List<ProductionType> getBaseProductRandomOrder() {
		List<ProductionType> result = new ArrayList<>();
		result.add(ProductionType.COMMODITY);
		result.add(Rand.bound(2), ProductionType.MATERIAL);
		result.add(Rand.bound(3), ProductionType.CRYSTAL);
		return result;
	}

	public static ProductionType getRandom() {
		return VALUES.get(Rand.bound(SIZE));
	}

	@Override
	public String toString() {
		return name;
	}
}
