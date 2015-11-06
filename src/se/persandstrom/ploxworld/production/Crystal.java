package se.persandstrom.ploxworld.production;

public class Crystal extends Production {

	public Crystal() {
	}

	public Crystal(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public boolean isRawMaterial() {
		return true;
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.CRYSTAL;
	}

	@Override
	public double getBasePrice() {
		return 100;
	}
}
