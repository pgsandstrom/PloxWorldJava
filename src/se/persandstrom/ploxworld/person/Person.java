package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.Decision;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.ship.Ship;

import com.google.gson.annotations.Expose;

public class Person implements Comparable<Person> {

	private final Ai ai;
	private final Personality personality;

	private boolean alive = true;

	@Expose private final String name;

	@Expose private Point point;
	@Expose private Location location;

	@Expose private int money = 1000;

	private Ship ship = new Ship();

	private Decision decision;

	public Person(Ai ai, Personality personality, String name, Location location) {
		this.ai = ai;
		this.personality = personality;
		this.name = name;
		setLocation(location);
	}

	public void executeDecision() {
		if (decision != null) {
			decision.execute();
		}
	}

	public Personality getPersonality() {
		return personality;
	}

	public Ship getShip() {
		return ship;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public Decision getDecision() {
		return decision;
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

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
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

	@Override
	public int compareTo(Person other) {
		return name.compareTo(other.name);
	}
}
