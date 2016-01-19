package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.Science;

public class PlanetCreater {

	public static final int PLANET_MIN_DISTANCE = 50;
	public static final int PLANET_BORDER_DISTANCE = 30;
	public static final int PLANET_BORDER_DISTANCE_RIGHT = 60;

	private static final int PLANET_MIN_POPULATION_CAP = 1;
	private static final int PLANET_MAX_POPULATION_CAP = 25;

	private final List<String> names = new ArrayList<>(Arrays.asList("Mercurius", "Venus", "Tellus", "Mars", "Jupiter", "Saturnus", "Neptunus",
			"Uranus", "Pluto", "X", "Xero", "Ygdra", "Jakop", "Crea", "Ando", "Estal", "Zzyr", "Sol", "Tyl", "Mega", "Terra", "Eve", "Ada", "Omega",
			"Orion"));

	private final World world;
	private Set<Planet> planets = new HashSet<>();

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

		int populationMax = Rand.bound(PLANET_MIN_POPULATION_CAP, PLANET_MAX_POPULATION_CAP);
		double population = Rand.percentage() * (populationMax - 1) + 1;

		int money = 10000;

		Commodity commodity = new Commodity();
		initProduction(commodity, population);
		Material material = new Material();
		initProduction(material, population);
		Construction construction = new Construction();
		initProduction(construction, population);
		Crystal crystal = new Crystal();
		initProduction(crystal, population);
		Science science = new Science();
		initProduction(science, population);

		return new Planet(name, position, populationMax, population, money, commodity, material, construction, crystal, science);
	}

	private String getRandomName() {
		int i = Rand.bound(names.size());
		String name = this.names.get(i);
		names.remove(i);
		return name;
	}

	private boolean validPlanetPosition(Point point) {
		List<Location> locations = new ArrayList<>();
		locations.addAll(planets);
		if(world.getAsteroids() != null) {
			locations.addAll(world.getAsteroids());
		}
		for (Location location : locations) {
			if (location.getDistance(point) < PLANET_MIN_DISTANCE) {
				return false;
			}
			// To make sure locations don't cover the planet names on the map:
			if (Math.abs(location.getPoint().y - point.y) < 35 && Math.abs(location.getPoint().x - point.x) < 100) {
				return false;
			}

		}
		return true;
	}

	public void initProduction(Production production, double population) {
		int multiplier;
		if (production.isRawMaterial()) {
			multiplier = Rand.bound(1, 6);	//should be less when we get material from other places
		} else {
			multiplier = Rand.bound(1, 6);
		}
		int storage = multiplier * 10 + (int) population;
		production.setMultiplier(multiplier);
		production.setStorage(storage);
		production.setWorkers(0);
	}
}
