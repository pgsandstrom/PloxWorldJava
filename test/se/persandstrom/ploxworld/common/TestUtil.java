package se.persandstrom.ploxworld.common;

import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Science;

public class TestUtil {

	public static Planet getPlanet() {

		int x = Rand.bound(0, 500);
		int y = Rand.bound(0, 500);
		return getPlanet(new Point(x, y));
	}

	public static Planet getPlanet(Point point) {

		return new Planet("planet name", point, 50, 25, 10000,
				new Commodity(1, 1, 0), new Material(1, 10, 0), new Construction(2, 2, 0),
				new Crystal(3, 3, 0), new Science(3, 3, 0));
	}

	public static Person getPerson() {
		return new Person(new TraderAi(), "person name", getPlanet());
	}

	public static Person getPerson(Point point) {
		return new Person(new TraderAi(), "person name", getPlanet(point));
	}
}
