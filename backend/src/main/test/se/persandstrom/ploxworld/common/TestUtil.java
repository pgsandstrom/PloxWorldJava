package se.persandstrom.ploxworld.common;

import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.locations.property.Tradeable;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.person.PersonalityType;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Science;

public class TestUtil {

	public static Location getPlanet() {

		int x = Rand.bound(0, 500);
		int y = Rand.bound(0, 500);
		return getPlanet(new Point(x, y));
	}

	public static Location getPlanet(Point point) {

		return new Location("planet name", point, new Tradeable(10000,
				new Commodity(1, 1, 0), new Material(1, 10, 0), new Construction(2, 2, 0),
				new Crystal(3, 3, 0), new Science(3, 3, 0)), new Civilization(50, 25));
	}

	public static Person getPerson() {
		return new Person(new TraderAi(), PersonalityType.TRADE.getPersonality(), "person name", getPlanet(), 1000);
	}

	public static Person getPerson(Point point) {
		return new Person(new TraderAi(), PersonalityType.TRADE.getPersonality(), "person name", getPlanet(point), 1000);
	}
}
