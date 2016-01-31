package se.persandstrom.ploxworld.ship;

public enum ShipBase {
	SIMPLE("Simple", 30, 30, 1);

	public final String name;
	public final int health;
	public final int storage;
	public final int weaponSlots;

	ShipBase(String name, int health, int storage, int weaponSlots) {
		this.name = name;
		this.health = health;
		this.storage = storage;
		this.weaponSlots = weaponSlots;
	}

}
