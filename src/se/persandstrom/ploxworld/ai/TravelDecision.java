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

		double speed = 25;

		double distance = toPlanet.getDistance(from);
		if (distance < speed) {    // Arrived!
			person.setPlanet(toPlanet);
		} else {
			double angle = Math.atan((from.y - to.y) / (double) (from.x - to.x));
			double diffX = Math.cos(angle) * speed;
			double diffY = Math.sin(angle) * speed;
			person.setPoint(new Point(from, (int) diffX, (int) diffY));
		}
	}
}
