package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.WeirdUtil;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.Science;
import se.persandstrom.ploxworld.ship.Weapon;

import com.google.gson.annotations.Expose;

public class Planet extends Location implements Comparable<Planet> {

	private static final double POPULATION_GROWTH = 1.005;

	@Expose private int maxPopulation;
	@Expose private double population;

	@Expose private int money;
	@Expose private Commodity commodity;
	@Expose private Material material;
	@Expose private Construction construction;
	@Expose private Crystal crystal;
	@Expose private Science science;
	private final List<Production> productions = new ArrayList<>();

	private Set<Weapon> weapons = new HashSet<>();

	{
		weapons.add(Weapon.SIMPLE);
	}

	public Planet(String name, Point point, int maxPopulation, double population, int money,
			Commodity commodity, Material material, Construction construction, Crystal crystal, Science science) {
		super(name, point);
		this.maxPopulation = maxPopulation;
		this.population = population;
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

	public void prepareStuff() {
		redistributePopulation();
		calculateNeed();
	}

	public void progressTurn() {

		money += population * 100;

		population = population * POPULATION_GROWTH;
		if (population > maxPopulation) {
			population = maxPopulation;
		}

		redistributePopulation();

		this.commodity.progressTurn();
		this.commodity.addStorage(-getPopulation());

		this.material.progressTurn();
		int materialUsed = this.construction.progressTurn();
		this.material.addStorage(-materialUsed);
		this.crystal.progressTurn();
		int crystalUsed = this.science.progressTurn();
		this.crystal.addStorage(-crystalUsed);

		calculateNeed();

		if (this.commodity.getStorage() < 0) {
			throw new IllegalStateException("Commodity is " + this.commodity.getStorage() + " at " + this.name + ".");
		}
		if (this.material.getStorage() < 0) {
			throw new IllegalStateException("Material is " + this.material.getStorage() + " at " + this.name + ".");
		}
		if (this.crystal.getStorage() < 0) {
			throw new IllegalStateException("Crystal is " + this.crystal.getStorage() + " at " + this.name + ".");
		}

		PlanetAi.purchase(this);
	}

	private void redistributePopulation() {
		List<Production> sortedProd = getProductionsSortedByMultiplier();
		sortedProd.forEach(production -> production.setWorkers(0));

		int freeWorkers = (int) population;

		int indexOfProduction = 1;

		// If we are not only producing commodity, produce enough to survive:
		if (sortedProd.get(sortedProd.size() - indexOfProduction).getProductionType() != ProductionType.COMMODITY) {
			int remainingCommodity = commodity.getStorage() - getPopulation();
			if (remainingCommodity < 0) {
				commodity.addWorkers(-remainingCommodity);
				freeWorkers += remainingCommodity;
			}
		}

		while (freeWorkers > 0) {
			Production production = sortedProd.get(sortedProd.size() - indexOfProduction);
			int productionMaxWorkers = getProductionMaxWorkers(production);
			int productionWorkers = Math.min(freeWorkers, productionMaxWorkers);
			production.addWorkers(productionWorkers);
			freeWorkers -= productionWorkers;
			indexOfProduction++;
		}
	}

	private int getProductionMaxWorkers(Production production) {
		if (production instanceof Construction) {
			return material.getStorage() / production.getMultiplier();
		} else if (production instanceof Science) {
			return crystal.getStorage() / production.getMultiplier();
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private void calculateNeed() {
		List<Production> sortedProd = getProductionsSortedByMultiplier();
		Production bestProduction = sortedProd.get(sortedProd.size() - 1);

		// If we need commodity
		if (bestProduction.getProductionType() != ProductionType.COMMODITY) {
//			int need = 1 + (bestProduction.getMultiplier() - commodity.getMultiplier());	//TODO: Care about need again
			double buyQuota = getQuota(commodity.getStorage(), 1.1, 2.0, population * 5, population * 50);
			commodity.setBuyPrice((int) (commodity.getBasePrice() * buyQuota));
			commodity.setSellPrice((int) (commodity.getBasePrice() * 2.1));
		} else {    // If we produce commodity
			commodity.setBuyPrice((int) (commodity.getBasePrice() * 0.5));
			double sellQuota = getQuota(commodity.getStorage(), 0.5, 0.9, 0, 1000);
			commodity.setSellPrice((int) (commodity.getBasePrice() * sellQuota));
		}

		// Crystal
		if (bestProduction.getProductionType() == ProductionType.SCIENCE) {
//			int need = 1 + (bestProduction.getMultiplier() - crystal.getMultiplier());	//TODO: Care about need again
			double buyQuota = getQuota(crystal.getStorage(), 1.1, 2.0, population * 5, population * 50);
			crystal.setBuyPrice((int) (crystal.getBasePrice() * buyQuota));
			crystal.setSellPrice((int) (crystal.getBasePrice() * 2.1));
		} else {
			crystal.setBuyPrice((int) (crystal.getBasePrice() * 0.9));
			crystal.setSellPrice((int) (crystal.getBasePrice() * 1.1));
		}

		if (bestProduction.getProductionType() == ProductionType.CRYSTAL) {
			crystal.setBuyPrice((int) (crystal.getBasePrice() * 0.3));
			double sellQuota = getQuota(crystal.getStorage(), 0.5, 0.9, 0, 1000);
			crystal.setSellPrice((int) (crystal.getBasePrice() * sellQuota));
		}

		// Science
		science.setBuyPrice((int) (science.getBasePrice() * 0.9));
		double scienceSellQuota = getQuota(science.getStorage(), 1.1, 1.3, 0, 1000);
		science.setSellPrice((int) (science.getBasePrice() * scienceSellQuota));


		// Material
		if (bestProduction.getProductionType() == ProductionType.CONSTRUCTION) {
//			int need = 1 + (bestProduction.getMultiplier() - material.getMultiplier());	//TODO: Care about need again
			double buyQuota = getQuota(material.getStorage(), 1.1, 2.0, population * 5, population * 50);
			material.setBuyPrice((int) (material.getBasePrice() * buyQuota));
			material.setSellPrice((int) (material.getBasePrice() * 2.1));
		} else {
			material.setBuyPrice((int) (material.getBasePrice() * 0.9));
			material.setSellPrice((int) (material.getBasePrice() * 1.1));
		}

		if (bestProduction.getProductionType() == ProductionType.MATERIAL) {
			material.setBuyPrice((int) (material.getBasePrice() * 0.3));
			double sellQuota = getQuota(material.getStorage(), 0.5, 0.9, 0, 1000);
			material.setSellPrice((int) (material.getBasePrice() * sellQuota));
		}

		// Construction
		construction.setBuyPrice((int) (construction.getBasePrice() * 0.9));
		double constructionSellQuota = getQuota(construction.getStorage(), 1.1, 1.3, 0, 1000);
		construction.setSellPrice((int) (construction.getBasePrice() * constructionSellQuota));
	}

	private List<Production> getProductionsSortedByMultiplier() {
		Collections.sort(productions, (o1, o2) -> {
			int diff = o1.getMultiplier() - o2.getMultiplier();
			if (diff == 0) {
				// TODO: Rewrite this sometimes... for realz. I need a determinstic way to sort those productions
				// that differens between planets.
				return WeirdUtil.stringToRandomDeterministicInt(name + o1.getProductionType()) -
						WeirdUtil.stringToRandomDeterministicInt(name + o1.getProductionType());
			} else {
				return diff;
			}
		});    // Sort according to multiplier
		return productions;
	}

	double getQuota(double storage, double lowestQuota, double highestQuota, double lowerStorageLimit, double higherStorageLimit) {
		if (storage < lowerStorageLimit) {
			return highestQuota;
		} else if (storage > higherStorageLimit) {
			return lowestQuota;
		} else {
			double wayBetweenLimitsPercentage = (storage - lowerStorageLimit) / (higherStorageLimit - lowerStorageLimit);
			double quotaRange = highestQuota - lowestQuota;
			return highestQuota - quotaRange * (wayBetweenLimitsPercentage);
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

	public int getPopulation() {
		return (int) population;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public Construction getConstruction() {
		return construction;
	}

	public Science getScience() {
		return science;
	}

	public Crystal getCrystal() {
		return crystal;
	}

	public Material getMaterial() {
		return material;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Planet planet = (Planet) o;

		if (!name.equals(planet.name)) return false;

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
	public int compareTo(Planet other) {
		return name.compareTo(other.name);
	}
}
