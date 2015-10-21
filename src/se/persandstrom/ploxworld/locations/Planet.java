package se.persandstrom.ploxworld.locations;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;

import com.google.gson.annotations.Expose;

public class Planet {

	@Expose private final String name;
	@Expose private final Point point;

	public Planet(String name, Point point) {
		this.name = name;
		this.point = point;
	}

	public double getDistance(Point point) {
		return Geo.getDistance(this.point, point);
	}
}
