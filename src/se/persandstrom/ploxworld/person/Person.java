package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.Decision;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.ship.Ship;

import com.google.gson.annotations.Expose;

public class Person {

	private final Ai ai;

	@Expose private final String name;

	@Expose private Point point;
	@Expose private Location location;

	@Expose private int money = 1000;

	private Ship ship = new Ship();

	private Decision decision;

	public Person(Ai ai, String name, Location location) {
		this.ai = ai;
		this.name = name;
		setLocation(location);
	}

	public void executeDecision() {
		if (decision != null) {
			decision.execute();
		}
	}

	public Ship getShip() {
		return ship;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Location getLocation() {
		return location;
	}

	public boolean isOn(Class<? extends Location> clazz) {
		return clazz.isInstance(location);
	}

	public void setLocation(Location location) {
		this.location = location;
		if (location != null) {
			this.point = location.getPoint();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		if (!name.equals(person.name)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
