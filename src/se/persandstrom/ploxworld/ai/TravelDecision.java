package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.person.Person;

public class TravelDecision implements Decision {

	private final Person person;
	private final Planet toPlanet;

	public TravelDecision(Person person, Planet to) {
		this.person = person;
		this.toPlanet = to;
	}

	@Override
	public void execute() {
		Point from = person.getPoint();
		Point to = toPlanet.getPoint();

		int xDiff = from.x - to.x;
		int yDiff = from.y - to.y;

		//TODO: Fixa klart denna...


	}
}
