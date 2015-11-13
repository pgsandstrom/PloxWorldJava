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

		double xDiff = to.x - from.x;
		double yDiff = to.y - from.y;

		double speed = 25;

		double distance = toPlanet.getDistance(from);
		if (distance < speed) {    // Arrived!
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
		}

		// this crappy code the ugly special that I don't understand, and still don't work for vertical traveling

//		Point from = person.getPoint();
//		Point to = toPlanet.getPoint();
//
//		double speed = 25;
//
//		double distance = toPlanet.getDistance(from);
//		if (distance < speed) {    // Arrived!
//			person.setPlanet(toPlanet);
//		} else {
//			double angle = Math.atan((from.y - to.y) / (double) (from.x - to.x));
//			double diffX = Math.cos(angle) * speed;
//			double diffY = Math.sin(angle) * speed;
//			if ((from.x - to.x) > 0 && (from.y - to.y) > 0) {
//				diffX *= -1;
//				diffY *= -1;
//			}
//			if ((from.x - to.x) > 0 && (from.y - to.y) < 0) {
//				diffX *= -1;
//				diffY *= -1;
//			}
//			person.setPoint(new Point(from, (int) diffX, (int) diffY));
//			person.setPlanet(null);
//		}
	}

	public double getDistance() {
		return toPlanet.getDistance(person.getPoint());
	}
}
