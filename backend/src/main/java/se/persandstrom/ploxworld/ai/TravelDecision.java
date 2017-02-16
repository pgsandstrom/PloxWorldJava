package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.person.Person;

public class TravelDecision implements Decision {

	private final Person person;
	private final Location toLocation;
	private final Goal goal;

	public TravelDecision(Person person, Location to) {
		this(person, to, null);
	}

	public TravelDecision(Person person, Location to, Goal goal) {
		this.person = person;
		this.toLocation = to;
		this.goal = goal;
	}

	@Override
	public void execute() {
		Point from = person.getPoint();
		Point to = toLocation.getPoint();

		double speed = 25;

		double distance = toLocation.getDistance(from);

		if (distance < speed) {    // Arrived!
			person.setLocation(toLocation);
		} else {
			double deltaY = to.y - from.y;
			double deltaX = to.x - from.x;

			double xChange = speed / distance * deltaX;
			double yChange = speed / distance * deltaY;

			int xChangeRounded = (int) Math.round(xChange);
			int yChangeRounded = (int) Math.round(yChange);

			person.setPoint(new Point(from, xChangeRounded, yChangeRounded));
			person.setLocation(null);
		}
	}

	public double getDistance() {
		return toLocation.getDistance(person.getPoint());
	}

	@Override
	public Goal getGoal() {
		return goal;
	}
}
