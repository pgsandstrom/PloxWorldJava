package se.persandstrom.ploxworld.locations;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;

import com.google.gson.annotations.Expose;

public class Planet {

	@Expose private final String name;
	@Expose private final Point point;

	@Expose private int maxPopulation;
	@Expose private double population;

	public Planet(String name, Point point, int maxPopulation, double population) {
		this.name = name;
		this.point = point;
	}

	public void progressTurn() {

	}

	public double getDistance(Point point) {
		return Geo.getDistance(this.point, point);
	}

	public Point getPoint() {
		return point;
	}

	public String getName() {
		return name;
	}
}
