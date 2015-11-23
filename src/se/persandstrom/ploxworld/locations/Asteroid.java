package se.persandstrom.ploxworld.locations;

import se.persandstrom.ploxworld.common.Point;

import com.google.gson.annotations.Expose;

public class Asteroid extends Location {

	@Expose private final double miningEfficiency;

	public Asteroid(String name, Point point, double miningEfficiency) {
		super(name, point);
		this.miningEfficiency = miningEfficiency;
	}

	public double getMiningEfficiency() {
		return miningEfficiency;
	}
}
