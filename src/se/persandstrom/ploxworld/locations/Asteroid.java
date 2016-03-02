package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

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
	public Optional<Mineable> getMineable() {
		return Optional.of(mineable);
	}
}
