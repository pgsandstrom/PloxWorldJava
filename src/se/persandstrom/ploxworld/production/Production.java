package se.persandstrom.ploxworld.production;

import com.google.gson.annotations.Expose;

public abstract class Production {

	@Expose private int storage;
	@Expose private int multiplier;
	@Expose private int workers;
	@Expose private int sellPrice = 0;
	@Expose private int buyPrice = 0;

	public Production() {
	}

	public Production(int storage, int production, int workers) {
		this.storage = storage;
		this.multiplier = production;
		this.workers = workers;
	}

	/**
	 * @return how much was prodcued this turn.
	 */
	public int progressTurn() {
		int produced = this.workers * this.multiplier;
		this.storage += produced;
		return produced;
	}

	public abstract boolean isRawMaterial();

	public abstract ProductionType getProductionType();

	public abstract double getBasePrice();

	public int getProduced() {
		return this.workers * this.multiplier;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public void addStorage(int add) {
		this.storage += add;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getWorkers() {
		return workers;
	}

	public void setWorkers(int workers) {
		this.workers = workers;
	}

	public void addWorkers(int workers) {
		this.workers += workers;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Override
	public String toString() {
		return getProductionType().toString();
	}
}
