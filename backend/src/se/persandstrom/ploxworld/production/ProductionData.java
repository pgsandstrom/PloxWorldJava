package se.persandstrom.ploxworld.production;

import com.google.gson.annotations.Expose;

public class ProductionData {

	@Expose private final ProductionType productionType;
	@Expose int storage = 0;
	@Expose int production = 0;

	public ProductionData(ProductionType productionType) {
		this.productionType = productionType;
	}

	public void addStorage(int storage) {
		this.storage += storage;
	}

	public void addProduction(int production) {
		this.production += production;
	}

	public ProductionType getProductionType() {
		return productionType;
	}

	public int getStorage() {
		return storage;
	}

	public int getProduction() {
		return production;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductionData that = (ProductionData) o;
		if (productionType != that.productionType) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return productionType.hashCode();
	}
}
