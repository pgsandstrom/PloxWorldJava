package se.persandstrom.ploxworld.person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;

public class PersonCreater {

	private final World world;
	private final Set<String> maleNames;
	private final Set<String> femaleNames;
	private final Set<String> surnames;

	private final Set<String> usedFullNames;

	public PersonCreater(World world) throws URISyntaxException, IOException {
		this.world = world;
		maleNames = new HashSet<>();
		femaleNames = new HashSet<>();
		surnames = new HashSet<>();

		usedFullNames = new HashSet<>();

		Path maleNamesPath = Paths.get(PersonCreater.class.getResource("/names/male.txt").toURI());
		Files.lines(maleNamesPath).forEach(maleNames::add);

		Path femaleNamesPath = Paths.get(PersonCreater.class.getResource("/names/female.txt").toURI());
		Files.lines(femaleNamesPath).forEach(femaleNames::add);

		Path surnamesPath = Paths.get(PersonCreater.class.getResource("/names/surname.txt").toURI());
		Files.lines(surnamesPath).forEach(surnames::add);
	}

	public List<Person> createPersons(int number, PersonalityType personalityType) {
		List<Person> persons = new ArrayList<>();
		while (number-- > 0) {
			persons.add(createPerson(personalityType));
		}
		Collections.sort(persons);
		return persons;
	}

	public Person createPerson(PersonalityType personalityType) {
		String name = getRandomName();
		Location location = Rand.getRandom(world.getLocations());
		int money = 0;
		switch (personalityType) {
			case MINER:
				money = 1000;
				break;
			case TRADE:
				money = 1000;
				break;
			case PIRATE:
				money = 10000;
				break;
		}
		return new Person(personalityType.getAi(), personalityType.getPersonality(), name, location, money);
	}

	private String getRandomName() {
		Set<String> names;
		if (Rand.bool()) {
			names = maleNames;
		} else {
			names = femaleNames;
		}

		String fullName;
		int attempts = 0;

		do {
			fullName = Rand.getRandom(names) + " " + Rand.getRandom(surnames);
			attempts++;
			if (attempts == 20) {
				throw new RuntimeException("too many name creations failed...");
			}
		} while (usedFullNames.contains(fullName));

		usedFullNames.add(fullName);
		return fullName;
	}

}
