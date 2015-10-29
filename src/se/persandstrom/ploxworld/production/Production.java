package se.persandstrom.ploxworld.production;

import java.util.Comparator;

import com.google.gson.annotations.Expose;

public abstract class Production implements Comparable<Production> {

	@Expose private int storage;
	@Expose private int multiplier;
	@Expose private int workers;

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

	public abstract ProductionType getProductionType();

	@Override
	public int compareTo(Production other) {
		return this.multiplier - other.multiplier;
	}
}
