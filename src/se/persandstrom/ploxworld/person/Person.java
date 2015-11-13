package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.TravelDecision;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.ship.Ship;

import com.google.gson.annotations.Expose;

public class Person {

	private final Ai ai;

	@Expose private final String name;

	@Expose private Point point;
	@Expose private Planet planet;

	@Expose private int money = 1000;

	private Ship ship = new Ship();

	private TravelDecision decision;

	public Person(Ai ai, String name, Planet planet) {
		this.ai = ai;
		this.name = name;
		setPlanet(planet);
	}

	public void executeDecision() {
		decision.execute();
	}

	public Ship getShip() {
		return ship;
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

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
		if (planet != null) {
			this.point = planet.getPoint();
		}
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int amount) {
		this.money += amount;
	}

	public String getName() {
		return name;
	}

	public Ai getAi() {
		return ai;
	}
}
