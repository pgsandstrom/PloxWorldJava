package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.main.World;

public class PlanetCreater {

	private static final int PLANET_MIN_DISTANCE = 50;
	private static final int PLANET_BORDER_DISTANCE = 30;
	private static final int PLANET_BORDER_DISTANCE_RIGHT = 60;

	private final List<String> names = new ArrayList<>(Arrays.asList("Mercurius", "Venus", "Tellus", "Mars", "Jupiter", "Saturnus", "Neptunus",
			"Uranus", "Pluto", "X", "Xero", "Ygdra", "Jakop", "Crea", "Ando", "Estal", "Zzyr", "Sol", "Tyl", "Mega", "Terra", "Eve", "Ada", "Omega",
			"Orion"));

	private final World world;
	private Set<Planet> planets = new HashSet<Planet>();

	public PlanetCreater(World world) {
		this.world = world;
	}

	public Set<Planet> createPlanets(int number) {
		while (number-- > 0) {
			planets.add(createPlanet());
		}
		return planets;
	}

	private Planet createPlanet() {
		String name = getRandomName();

		Point position;
		do {
			position = world.getRandomPoint(PLANET_BORDER_DISTANCE, PLANET_BORDER_DISTANCE_RIGHT, PLANET_BORDER_DISTANCE, PLANET_BORDER_DISTANCE);
		} while (!validPlanetPosition(position));

		return new Planet(name, position);
	}

	private String getRandomName() {
		int i = Rand.bound(names.size());
		String name = this.names.get(i);
		names.remove(i);
		return name;
	}

	private boolean validPlanetPosition(Point point) {
		for (Planet planet : planets) {
			if (planet.getDistance(point) < PLANET_MIN_DISTANCE) {
				return false;
			}
			// To make sure planets don't cover the planet names on the map:
			if (Math.abs(planet.getPoint().y - point.y) < 35 && Math.abs(planet.getPoint().x - point.x) < 100) {
				return false;
			}

		}
		return true;
	}
}
