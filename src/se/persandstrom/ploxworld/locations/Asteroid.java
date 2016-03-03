package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Mineable;
import se.persandstrom.ploxworld.locations.property.Tradeable;

import com.google.gson.annotations.Expose;

public class Asteroid extends Location implements Comparable<Asteroid> {

	@Expose private final Mineable mineable;

	public Asteroid(String name, Point point, double miningEfficiency) {
		super(name, point);
		this.mineable = new Mineable(miningEfficiency);
	}

	@Override
	public int compareTo(Asteroid other) {
		return name.compareTo(other.name);
	}

	@Override
	public Optional<Civilization> getCivilization() {
		return null;
	}

	@Override
	public Optional<Mineable> getMineable() {
		return Optional.of(mineable);
	}

	@Override
	public Optional<Tradeable> getTradeable() {
		return Optional.empty();
	}
}
