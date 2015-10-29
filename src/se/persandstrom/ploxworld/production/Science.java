package se.persandstrom.ploxworld.production;

public class Science extends Production {

	public Science() {
	}

	public Science(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.SCIENCE;
	}
}
