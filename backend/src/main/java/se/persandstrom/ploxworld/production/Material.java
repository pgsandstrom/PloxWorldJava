package se.persandstrom.ploxworld.production;

public class Material extends Production {

	public Material() {
	}

	public Material(int storage, int production, int workers) {
		super(storage, production, workers);
	}

	@Override
	public boolean isRawMaterial() {
		return true;
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.MATERIAL;
	}

	@Override
	public double getBasePrice() {
		return 100;
	}
}
