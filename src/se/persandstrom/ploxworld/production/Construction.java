package se.persandstrom.ploxworld.production;

public class Construction extends Production {

	public Construction() {
	}

	public Construction(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.CONSTRUCTION;
	}
}
