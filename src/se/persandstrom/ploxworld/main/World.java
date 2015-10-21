package se.persandstrom.ploxworld.main;

import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.locations.PlanetCreater;

import com.google.gson.annotations.Expose;

public class World {

	private static final int SIZE_X = 500;
	private static final int SIZE_Y = 500;

	@Expose Set<Planet> planets;

	public World() {
		PlanetCreater planetCreater = new PlanetCreater(this);
		planets = planetCreater.createPlanets(20);
	}

	public Point getRandomPoint(int border) {
		return new Point(Rand.bound(SIZE_X - border * 2) + border, Rand.bound(SIZE_Y - border * 2) + border);
	}

}
