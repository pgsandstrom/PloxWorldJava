package se.persandstrom.ploxworld.character;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Planet;

import com.google.gson.annotations.Expose;

public class Character {

	@Expose private final String name;

	@Expose private Point point;
	@Expose private Planet planet;

	public Character(String name, Planet planet) {
		this.name = name;
		this.point = planet.getPoint();
		this.planet = planet;
	}
}
