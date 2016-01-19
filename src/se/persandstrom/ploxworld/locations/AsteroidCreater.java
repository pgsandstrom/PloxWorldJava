package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.main.World;

public class AsteroidCreater {

	private static final int ASTEROID_MIN_DISTANCE = 50;
	private static final int ASTEROID_BORDER_DISTANCE = 30;
	private static final int ASTEROID_BORDER_DISTANCE_RIGHT = 60;

	//TODO: add more names
	private final List<String> names = new ArrayList<>(Arrays.asList("X-92", "AB-14", "DD-1", "XA-11", "AB-A", "KC-98", "MM-14", "LL-10"));

	private final World world;
	private Set<Asteroid> asteroids = new HashSet<>();

	public AsteroidCreater(World world) {
		this.world = world;
	}

	public Set<Asteroid> createAsteroids(int number) {
		//TODO check so they don't collide with planets
		while (number-- > 0) {
			asteroids.add(createAsteroid());
		}
		return asteroids;
	}

	private Asteroid createAsteroid() {
		String name = getRandomName();

		Point position;
		do {
			position = world.getRandomPoint(ASTEROID_BORDER_DISTANCE, ASTEROID_BORDER_DISTANCE_RIGHT, ASTEROID_BORDER_DISTANCE, ASTEROID_BORDER_DISTANCE);
		} while (!validAsteroidPosition(position));

		double miningEfficiency = Rand.boundDouble(0.3, 3);

		return new Asteroid(name, position, miningEfficiency);
	}

	private String getRandomName() {
		int i = Rand.bound(names.size());
		String name = this.names.get(i);
		names.remove(i);
		return name;
	}

	private boolean validAsteroidPosition(Point point) {
		List<Location> locations = new ArrayList<>();
		locations.addAll(asteroids);
		locations.addAll(world.getPlanets());
		for (Location location : locations) {
			if (location.getDistance(point) < ASTEROID_MIN_DISTANCE) {
				return false;
			}
			// To make sure locations don't cover the planet names on the map:
			if (Math.abs(location.getPoint().y - point.y) < 35 && Math.abs(location.getPoint().x - point.x) < 100) {
				return false;
			}

		}
		return true;
	}
}
