package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Tradeable;
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


	public PlanetCreater(World world) {
		this.world = world;
	}

	public List<Location> createPlanets(int number) {
		List<Location> planets = new ArrayList<>();
		while (number-- > 0) {
			planets.add(createLocation(planets));
		}
		Collections.sort(planets);
		return planets;
	}

	private Location createLocation(List<Location> existingLocations) {
		String name = getRandomName();

		Point position;
		do {
			position = world.getRandomPoint(PLANET_BORDER_DISTANCE, PLANET_BORDER_DISTANCE_RIGHT, PLANET_BORDER_DISTANCE, PLANET_BORDER_DISTANCE);
		} while (!validLocationPosition(position, existingLocations));

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

		return new Location(name, position,
				new Tradeable(money, commodity, material, construction, crystal, science), new Civilization(populationMax, population));
	}

	private String getRandomName() {
		int i = Rand.bound(names.size());
		String name = this.names.get(i);
		names.remove(i);
		return name;
	}

	private boolean validLocationPosition(Point point, List<Location> existingLocations) {
		List<Location> locations = new ArrayList<>();
		locations.addAll(existingLocations);
		if (world.getLocations() != null) {
			locations.addAll(world.getLocations());
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
			multiplier = Rand.bound(1, 6);    //should be less when we get material from other places
		} else {
			multiplier = Rand.bound(1, 6);
		}
		int storage = multiplier * 10 + (int) population;
		production.setMultiplier(multiplier);
		production.setStorage(storage);
		production.setWorkers(0);
	}
}
