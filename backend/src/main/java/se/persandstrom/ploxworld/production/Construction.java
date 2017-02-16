package se.persandstrom.ploxworld.production;

public class Construction extends Production {

	public Construction() {
	}

	public Construction(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public boolean isRawMaterial() {
		return false;
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.CONSTRUCTION;
	}

	@Override
	public double getBasePrice() {
		return 300;
	}
}
