package se.persandstrom.ploxworld.production;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.persandstrom.ploxworld.common.Rand;

public enum ProductionType {
	COMMODITY, CONSTRUCTION, CRYSTAL, MATERIAL, SCIENCE;

	private static final List<ProductionType> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();

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

	public static ProductionType getRandom() {
		return VALUES.get(Rand.bound(SIZE));
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
