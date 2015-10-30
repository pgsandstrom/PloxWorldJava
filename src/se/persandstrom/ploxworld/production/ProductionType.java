package se.persandstrom.ploxworld.production;

public enum ProductionType {
	COMMODITY, CONSTRUCTION, CRYSTAL, MATERIAL, SCIENCE;

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
				throw new IllegalStateException();
		}
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
