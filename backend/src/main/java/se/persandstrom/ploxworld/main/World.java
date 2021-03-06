package se.persandstrom.ploxworld.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.ai.MinerAi;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
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

	private static final int PLANET_NUMBER = 1;
	private static final int ASTEROID_NUMBER = 1;

	private static final int MINER_NUMBER = 0;
	private static final int TRADER_NUMBER = 0;
	private static final int PIRATE_NUMBER = 1;

	private final PersonCreater personCreater;

	private final PlayerInterface playerInterface;

	@Expose private final int height = SIZE_Y;
	@Expose private final int width = SIZE_X;

	@Expose private final List<Location> locations;
	@Expose private List<Person> persons;

	@Expose WorldData worldData;
	@Expose int turn = 0;

	public World() throws IOException, URISyntaxException {
		this(null);
	}

	public World(PlayerInterface playerInterface) throws IOException, URISyntaxException {
		//TODO: make world hold a Rand instead
		Rand.reset();

		this.playerInterface = playerInterface;

		locations = new ArrayList<>();
		PlanetCreater planetCreater = new PlanetCreater(this);
		locations.addAll(planetCreater.createPlanets(PLANET_NUMBER));
		AsteroidCreater asteroidCreater = new AsteroidCreater(this);
		locations.addAll(asteroidCreater.createAsteroids(ASTEROID_NUMBER));
		personCreater = new PersonCreater(this);
		persons = personCreater.createPersons(MINER_NUMBER, PersonalityType.MINER);
		persons.addAll(personCreater.createPersons(TRADER_NUMBER, PersonalityType.TRADE));
		persons.addAll(personCreater.createPersons(PIRATE_NUMBER, PersonalityType.PIRATE));
		if (playerInterface != null) {
			List<Person> players = personCreater.createPersons(1, PersonalityType.PLAYER);
			Location someAsteroid = locations.get(locations.size() - 1);
			players.get(0).setLocation(someAsteroid);
			this.persons.addAll(players);
		}

		locations.forEach(Location::prepareStuff);

		worldData = new WorldData(this);
	}

	public void progressTurn() {
		progressTurn(1);
	}

	public void progressTurn(int turnToProgress) {
		while (turnToProgress > 0) {

			spawnNewPersons();

			persons.forEach(
					person -> {
						if (person.isAlive()) {

							if(person.getAi() == null) {
								return;	//TODO temp
							}

							person.getAi().makeDecision(World.this, person);
							person.executeDecision();
						}
					}
			);
			persons = persons.stream().filter(Person::isAlive).collect(Collectors.toList());

			locations.forEach(Location::progressTurn);
			turn++;
			turnToProgress--;
		}
		worldData.calculateProductionData();
	}

	private void spawnNewPersons() {
		long traderCount = persons.stream().filter(person -> person.getAi() instanceof TraderAi).count();
		if (traderCount < TRADER_NUMBER) {
			persons.add(personCreater.createPerson(PersonalityType.TRADE));
		}
		long minerCount = persons.stream().filter(person -> person.getAi() instanceof MinerAi).count();
		if (minerCount < MINER_NUMBER) {
			persons.add(personCreater.createPerson(PersonalityType.MINER));
		}
		long pirateCount = persons.stream().filter(person -> person.getAi() instanceof PirateAi).count();
		if (pirateCount < PIRATE_NUMBER) {
			persons.add(personCreater.createPerson(PersonalityType.PIRATE));
		}
	}

	public void executeAction(Action action) {
		if(action.isDecided() == false) {
			playerInterface.completeAction(action);
		}
		action.getActor().clearUnseenTransitions();
		action.execute();
		action.saveData(worldData);
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

	public Location getClosestCivilization(Point point) {
		return locations.stream()
				.filter(location -> location.getCivilization().isPresent())
				.min((o1, o2) -> Double.compare(o1.getDistance(point), o2.getDistance(point)))
				.get();
	}

	public List<Location> getLocations() {
		return locations;
	}

	public List<Location> getLocationsShuffled() {
		ArrayList<Location> locations = new ArrayList<>(this.locations);
		Collections.shuffle(locations, Rand.getRandom());
		return locations;
	}

	public List<Location> getTradeableShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getTradeable().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable, Rand.getRandom());
		return tradeable;
	}

	public List<Location> getCivilizationsShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getCivilization().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable, Rand.getRandom());
		return tradeable;
	}

	public List<Location> getMineableShuffled() {
		List<Location> tradeable = this.locations.stream()
				.filter(loc -> loc.getMineable().isPresent()).collect(Collectors.toList());
		Collections.shuffle(tradeable, Rand.getRandom());
		if (tradeable.size() == 0) {
			throw new IllegalStateException();
		}
		return tradeable;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public List<Person> getPersonsShuffled() {
		ArrayList<Person> personList = new ArrayList<>(this.persons);
		Collections.shuffle(personList, Rand.getRandom());
		return personList;
	}

	public Person getPerson(String name) {
		return persons.stream().filter(p -> p.getName().equals(name)).findAny().get();
	}

	public List<String> getPersonLogs(String name) {
		return getPerson(name).getLogs();
	}
}
