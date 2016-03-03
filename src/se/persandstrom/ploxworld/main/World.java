package se.persandstrom.ploxworld.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.AsteroidCreater;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.PlanetCreater;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.person.PersonCreater;
import se.persandstrom.ploxworld.person.PersonalityType;
import se.persandstrom.ploxworld.production.ProductionType;

import com.google.gson.annotations.Expose;

public class World {

	private static final int SIZE_X = 800;
	private static final int SIZE_Y = 500;

	@Expose private final int height = SIZE_Y;
	@Expose private final int width = SIZE_X;

	@Expose private final List<Location> locations;
	@Expose private List<Person> persons;

	@Expose WorldData worldData;
	@Expose int turn = 0;

	public World() {
		locations = new ArrayList<>();
		PlanetCreater planetCreater = new PlanetCreater(this);
		locations.addAll(planetCreater.createPlanets(25));
		AsteroidCreater asteroidCreater = new AsteroidCreater(this);
		locations.addAll(asteroidCreater.createAsteroids(5));
		PersonCreater characterCreater = new PersonCreater(this);
		persons = characterCreater.createPersons(5, PersonalityType.MINER);
		persons.addAll(characterCreater.createPersons(5, PersonalityType.TRADE));
		persons.addAll(characterCreater.createPersons(5, PersonalityType.PIRATE));

		locations.forEach(Location::prepareStuff);
	}

	public void progressTurn() {
		persons.stream().forEach(
				person -> {
					if (person.isAlive()) {
						person.getAi().makeDecision(World.this, person);
						person.executeDecision();
					}
				}
		);
		//TODO: Return name to name-pool when person dies?
		persons = persons.stream().filter(Person::isAlive).collect(Collectors.toList());

		locations.forEach(Location::progressTurn);
		turn++;
		worldData = new WorldData(this);
	}

	public Point getRandomPoint(int border) {
		return getRandomPoint(border, border, border, border);
	}

	public Point getRandomPoint(int borderTop, int borderRight, int borderBottom, int borderLeft) {
		return new Point(Rand.bound(SIZE_X - borderLeft - borderRight) + borderLeft, Rand.bound(SIZE_Y - borderTop - borderBottom) + borderBottom);
	}

	public Location getCheapestSellingLocation(ProductionType productionType) {
		return getTradeableShuffled().stream()
				.min((o1, o2) -> o1.getTradeable().get().getProduction(productionType).getSellPrice() - o2.getTradeable().get().getProduction(productionType).getSellPrice()).get();
	}

	public Location getMostPayingLocation(ProductionType productionType) {
		return getTradeableShuffled().stream()
				.max((o1, o2) -> o1.getTradeable().get().getProduction(productionType).getBuyPrice() - o2.getTradeable().get().getProduction(productionType).getBuyPrice()).get();
	}

	public List<Location> getLocations() {
		return locations;
	}

	public List<Location> getLocationsShuffled() {
		ArrayList<Location> locations = new ArrayList<>(this.locations);
		Collections.shuffle(locations);
		return locations;
	}

	public List<Location> getTradeableShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getTradeable().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable);
		return tradeable;
	}

	public List<Location> getCivilizationsShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getCivilization().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable);
		return tradeable;
	}

	public List<Location> getMineableShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getMineable().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable);
		if(tradeable.size() == 0) {
			throw new IllegalStateException();
		}
		return tradeable;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public List<Person> getPersonsShuffled() {
		ArrayList<Person> personList = new ArrayList<>(this.persons);
		Collections.shuffle(personList);
		return personList;
	}
}
