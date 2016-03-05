package se.persandstrom.ploxworld.ship;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import se.persandstrom.ploxworld.production.ProductionType;

import com.google.gson.annotations.Expose;

public class Ship {

	@Expose private ShipBase shipBase = ShipBase.SIMPLE;
	@Expose private Weapon weapon = Weapon.SIMPLE;

	@Expose private int health = shipBase.health;
	@Expose private int maxHealth = shipBase.health;

	@Expose private Map<ProductionType, Integer> storage = new HashMap<>();

	public Ship() {
		for (ProductionType productionType : ProductionType.values()) {
			storage.put(productionType, 0);
		}
	}

	public void addStorage(ProductionType productionType, int amount) {
		storage.compute(productionType, (productionType1, integer) -> integer + amount);
	}

	public int getMaxStorage() {
		return shipBase.storage;
	}

	public int getStorage(ProductionType productionType) {
		return storage.get(productionType);
	}

	public int getFreeStorage() {
		return getMaxStorage() - storage.entrySet().stream()
				.mapToInt(Map.Entry::getValue).sum();
	}

	public ProductionType getLargestProductionStorage() {
		Optional<Map.Entry<ProductionType, Integer>> max = storage.entrySet().stream()
				.max((o1, o2) -> o1.getValue() - o2.getValue());
		return max.get().getKey();
	}

	public void damage(int damage) {
		health -= damage;
	}

	public boolean isDead() {
		return health <= 0;
	}

	public double getPower() {
		return (shipBase.health * weapon.getAverageDamage());
	}


	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public ShipBase getShipBase() {
		return shipBase;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public void addHealth(int incHealth) {
		this.health += incHealth;
		if (health > maxHealth) {
			throw new IllegalStateException();
		}
	}

	public boolean isDamaged() {
		return maxHealth > health;
	}
}
