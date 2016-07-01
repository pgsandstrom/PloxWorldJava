package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Mineable;
import se.persandstrom.ploxworld.locations.property.Tradeable;

import com.google.gson.annotations.Expose;

public class Location implements Comparable<Location> {

	@Expose final String name;
	@Expose final Point point;

	@Expose private final Tradeable tradeable;
	@Expose private final Civilization civilization;
	@Expose private final Mineable mineable;
	@Expose private final LocationStyle locationStyle;

	public Location(String name, Point point, Mineable mineable) {
		this(name, point, null, null, mineable, LocationStyle.ASTEROID);
	}

	public Location(String name, Point point, Tradeable tradeable) {
		this(name, point, tradeable, null, null, LocationStyle.PLANET);
	}

	public Location(String name, Point point, Tradeable tradeable, Civilization civilization) {
		this(name, point, tradeable, civilization, null, LocationStyle.PLANET);
	}

	public Location(String name, Point point,
			Tradeable tradeable, Civilization civilization, Mineable mineable,
			LocationStyle locationStyle) {
		this.name = name;
		this.point = point;

		this.tradeable = tradeable;
		if (tradeable != null) {
			this.tradeable.constructorContinued(this);
		}
		this.civilization = civilization;
		if (this.civilization != null) {
			this.civilization.constructorContinued(this, tradeable);
		}
		this.mineable = mineable;

		this.locationStyle = locationStyle;
	}

	public void prepareStuff() {
		if (civilization != null) {
			civilization.redistributePopulation();
			civilization.calculateNeed();
		}
	}

	public void progressTurn() {
		if (civilization != null) {
			civilization.progressTurn();
		}
		if (tradeable != null) {
			tradeable.progressTurn(this);
		}
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

	public Optional<Civilization> getCivilization() {
		return Optional.ofNullable(civilization);
	}

	public Optional<Mineable> getMineable() {
		return Optional.ofNullable(mineable);
	}

	public Optional<Tradeable> getTradeable() {
		return Optional.ofNullable(tradeable);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Location location = (Location) o;

		if (!name.equals(location.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Location other) {
		return name.compareTo(other.name);
	}
}
