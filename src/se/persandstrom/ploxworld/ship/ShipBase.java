package se.persandstrom.ploxworld.ship;

public enum ShipBase {
	SIMPLE("Simple", 30, 30);

	ShipBase(String name, int health, int storage) {
		this.name = name;
		this.health = health;
		this.storage = storage;
	}

	public final String name;
	public final int health;
	public final int storage;
}
