package se.persandstrom.ploxworld.locations;

import java.util.Optional;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Mineable;
import se.persandstrom.ploxworld.locations.property.Tradeable;

import com.google.gson.annotations.Expose;

public class Planet extends Location implements Comparable<Planet> {


	@Expose private Civilization civilization;
	@Expose private Tradeable tradeable;

	public Planet(String name, Point point, Civilization civilization, Tradeable tradeable) {
		super(name, point);
		this.civilization = civilization;
		this.civilization.constructorContinued(this, tradeable);
		this.tradeable = tradeable;
		this.tradeable.constructorContinued(this);
	}

	public void prepareStuff() {
		civilization.redistributePopulation();
		civilization.calculateNeed();
	}

	public void progressTurn() {
		civilization.progressTurn();
		tradeable.progressTurn(this);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Planet planet = (Planet) o;

		if (!name.equals(planet.name)) return false;

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
	public int compareTo(Planet other) {
		return name.compareTo(other.name);
	}

	@Override
	public Optional<Civilization> getCivilization() {
		return Optional.of(civilization);
	}

	@Override
	public Optional<Mineable> getMineable() {
		return Optional.empty();
	}

	@Override
	public Optional<Tradeable> getTradeable() {
		return Optional.of(tradeable);
	}
}
