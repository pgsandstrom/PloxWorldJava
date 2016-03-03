package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Mineable;
import se.persandstrom.ploxworld.locations.property.Tradeable;

import com.google.gson.annotations.Expose;

public abstract class Location {

	@Expose final String name;
	@Expose final Point point;

	public Location(String name, Point point) {
		this.name = name;
		this.point = point;
	}

	public double getDistance(Point point) {
		return Geo.getDistance(this.point, point);
	}

	public String getName() {
		return name;
	}

	public Point getPoint() {
		return point;
	}

	public abstract Optional<Civilization> getCivilization();

	public abstract Optional<Mineable> getMineable();

	public abstract Optional<Tradeable> getTradeable();

	@Override
	public String toString() {
		return name;
	}
}
