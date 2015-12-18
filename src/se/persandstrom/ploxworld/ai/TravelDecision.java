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

		//double xDiff = to.x - from.x;
		//double yDiff = to.y - from.y;

		double speed = 25;

		double distance = toPlanet.getDistance(from);
		
		/*if (distance < speed) {    // Arrived!
			person.setPlanet(toPlanet);
		} else if (yDiff == 0) {
			if (xDiff < 0) {
				person.setPoint(new Point(from, (int) -speed, 0));
			} else {
				person.setPoint(new Point(from, (int) speed, 0));
			}
		} else {
			double xRationToY = Math.abs(xDiff) / Math.abs(yDiff);

			double traveled = Math.sqrt(xRationToY * xRationToY + 1);

			double speedAdjust = speed / traveled;

			double yChange = speedAdjust;
			double xChange = speedAdjust * xRationToY;

			if (xDiff < 0) {
				xChange *= -1;
			}
			if (yDiff < 0) {
				yChange *= -1;
			}
			person.setPoint(new Point(from, (int) xChange, (int) yChange));
			person.setPlanet(null);
		}*/

		// this crappy code is not crappy anymore! ;)

		if (distance < speed) {    // Arrived!
			person.setPlanet(toPlanet);
		} else {
			double deltaY = to.y-from.y;
			double deltaX = to.x-from.x;
			
			double xChange = speed/distance * deltaX;
			double yChange = speed/distance * deltaY;
			
			int xChangeRounded = (int) Math.round(xChange);
			int yChangeRounded = (int) Math.round(yChange);

			person.setPoint(new Point(from, xChangeRounded, yChangeRounded));
			person.setPlanet(null);
		}
	}

	public double getDistance() {
		return toPlanet.getDistance(person.getPoint());
	}
}
