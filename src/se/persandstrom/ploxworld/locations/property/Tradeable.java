package se.persandstrom.ploxworld.locations.property;

import java.util.ArrayList;
import java.util.List;

import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.Science;

import com.google.gson.annotations.Expose;

public class Tradeable {

	private Location location;

	@Expose private int money;
	@Expose private Commodity commodity;
	@Expose private Material material;
	@Expose private Construction construction;
	@Expose private Crystal crystal;
	@Expose private Science science;

	private final List<Production> productions = new ArrayList<>();

	public Tradeable(int money, Commodity commodity, Material material, Construction construction, Crystal crystal, Science science) {

		this.money = money;
		this.commodity = commodity;
		this.material = material;
		this.construction = construction;
		this.crystal = crystal;
		this.science = science;

		this.productions.add(commodity);
		this.productions.add(material);
		this.productions.add(construction);
		this.productions.add(crystal);
		this.productions.add(science);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void progressTurn(Location location) {
		money += (location.getPopulation() * 100);

		this.commodity.progressTurn();
		this.commodity.addStorage(-location.getPopulation());

		this.material.progressTurn();
		int materialUsed = this.construction.progressTurn();
		this.material.addStorage(-materialUsed);
		this.crystal.progressTurn();
		int crystalUsed = this.science.progressTurn();
		this.crystal.addStorage(-crystalUsed);

		if (this.commodity.getStorage() < 0) {
			throw new IllegalStateException("Commodity is " + this.commodity.getStorage() + " at " + location + ".");
		}
		if (this.material.getStorage() < 0) {
			throw new IllegalStateException("Material is " + this.material.getStorage() + " at " + location + ".");
		}
		if (this.crystal.getStorage() < 0) {
			throw new IllegalStateException("Crystal is " + this.crystal.getStorage() + " at " + location + ".");
		}
	}

	public Production getProduction(ProductionType productionType) {
		switch (productionType) {
			case COMMODITY:
				return commodity;
			case MATERIAL:
				return material;
			case CONSTRUCTION:
				return construction;
			case CRYSTAL:
				return crystal;
			case SCIENCE:
				return science;
			default:
				throw new AssertionError();
		}
	}

	public List<Production> getProductionsCheapestFirst() {
		productions.sort((o1, o2) -> {
			double sellQuota1 = o1.getSellPrice() / o1.getBasePrice();
			double sellQuota2 = o2.getSellPrice() / o2.getBasePrice();
			return sellQuota1 - sellQuota2 > 0 ? 1 : -1;
		});
		return productions;
	}

	/**
	 * @return amount planet payed
	 */
	public int sellTo(ProductionType productionType, int amount) {
		Production production = getProduction(productionType);
		production.addStorage(amount);
		int money = production.getBuyPrice() * amount;
		this.money -= money;
		return money;
	}

	/**
	 * @return amount person payed
	 */
	public int buyFrom(ProductionType productionType, int amount) {
		Production production = getProduction(productionType);
		production.addStorage(-amount);
		int payed = production.getSellPrice() * amount;
		money += payed;
		return payed;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public Material getMaterial() {
		return material;
	}

	public Construction getConstruction() {
		return construction;
	}

	public Crystal getCrystal() {
		return crystal;
	}

	public Science getScience() {
		return science;
	}

	public List<Production> getProductions() {
		return productions;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return location.getName();
	}
}
