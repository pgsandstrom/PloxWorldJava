package se.persandstrom.ploxworld.locations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.WeirdUtil;
import se.persandstrom.ploxworld.locations.property.Mineable;
import se.persandstrom.ploxworld.locations.property.Tradeable;
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

	@Expose private Tradeable tradeable;

	private Set<Weapon> weapons = new HashSet<>();

	{
		weapons.add(Weapon.SIMPLE);
	}

	public Planet(String name, Point point, int maxPopulation, double population, Tradeable tradeable) {
		super(name, point);
		this.maxPopulation = maxPopulation;
		this.population = population;
		this.tradeable = tradeable;
		this.tradeable.setLocation(this);
	}

	public void prepareStuff() {
		redistributePopulation();
		calculateNeed();
	}

	public void progressTurn() {


		population = population * POPULATION_GROWTH;
		if (population > maxPopulation) {
			population = maxPopulation;
		}

		redistributePopulation();

		tradeable.progressTurn(this);


		calculateNeed();


		PlanetAi.purchase(this);
	}

	private void redistributePopulation() {

		Commodity commodity = tradeable.getCommodity();

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
			return tradeable.getMaterial().getStorage() / production.getMultiplier();
		} else if (production instanceof Science) {
			return tradeable.getCrystal().getStorage() / production.getMultiplier();
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private void calculateNeed() {
		Commodity commodity = tradeable.getCommodity();
		Material material = tradeable.getMaterial();
		Construction construction = tradeable.getConstruction();
		Crystal crystal = tradeable.getCrystal();
		Science science = tradeable.getScience();


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
		List<Production> productions = tradeable.getProductions();
		Collections.sort(productions, (o1, o2) -> {
			int diff = o1.getMultiplier() - o2.getMultiplier();
			if (diff == 0) {
				return WeirdUtil.stringToRandomDeterministicInt(name + o1.getProductionType()) -
						WeirdUtil.stringToRandomDeterministicInt(name + o1.getProductionType());
			} else {
				return diff;
			}
		});
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

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	@Override
	public int getPopulation() {
		return (int) population;
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

	@Override
	public Optional<Mineable> getMineable() {
		return Optional.empty();
	}

	@Override
	public Optional<Tradeable> getTradeable() {
		return Optional.of(tradeable);
	}
}
