package se.persandstrom.ploxworld.production;

public class Commodity extends Production {

	public Commodity() {
	}

	public Commodity(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.COMMODITY;
	}
}
