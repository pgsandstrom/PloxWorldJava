package se.persandstrom.ploxworld.locations;

import com.google.gson.annotations.Expose;

public class Asteroid {

	@Expose private final double miningEfficiency;

	public Asteroid(double miningEfficiency) {
		this.miningEfficiency = miningEfficiency;
	}

	public double getMiningEfficiency() {
		return miningEfficiency;
	}
}
