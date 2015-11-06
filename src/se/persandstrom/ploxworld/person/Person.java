package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.TravelDecision;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Planet;

import com.google.gson.annotations.Expose;

public class Person {

	@Expose private final String name;

	@Expose private Point point;
	@Expose private Planet planet;

	private TravelDecision decision;

	public Person(String name, Planet planet) {
		this.name = name;
		setPlanet(planet);
	}

	public void executeDecision() {
//		decision.execute();
	}

	public void setDecision(TravelDecision decision) {
		this.decision = decision;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
		this.point = planet.getPoint();
	}

	public String getName() {
		return name;
	}
}
