package se.persandstrom.ploxworld.locations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.production.Commodity;
import se.persandstrom.ploxworld.production.Construction;
import se.persandstrom.ploxworld.production.Crystal;
import se.persandstrom.ploxworld.production.Material;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.Science;

import com.google.gson.annotations.Expose;

public class Planet {

	private static final double POPULATION_GROWTH = 1.01;

	@Expose private final String name;
	@Expose private final Point point;

	@Expose private int maxPopulation;
	@Expose private double population;

	@Expose private int money;
	@Expose private Commodity commodity;
	@Expose private Material material;
	@Expose private Construction construction;
	@Expose private Crystal crystal;
	@Expose private Science science;
	private final List<Production> productions = new ArrayList<>();

	public Planet(String name, Point point, int maxPopulation, double population, int money,
			Commodity commodity, Material material, Construction construction, Crystal crystal, Science science) {
		this.name = name;
		this.point = point;
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

	public void progressTurn() {

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
	}

	public void redistributePopulation() {
		productions.forEach(production -> production.setWorkers(0));

		Collections.sort(productions);    // Sort according to multiplier

		int freeWorkers = (int) population;

		int indexOfProduction = 1;

		// If we are not only producing commodity, produce enough to survive:
		if (productions.get(productions.size() - indexOfProduction).getProductionType() != ProductionType.COMMODITY) {
			int remainingCommodity = commodity.getStorage() - getPopulation();
			if (remainingCommodity < 0) {
				commodity.addWorkers(-remainingCommodity);
				freeWorkers += remainingCommodity;
			}
		}

		while (freeWorkers > 0) {
			Production production = productions.get(productions.size() - indexOfProduction);
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

	public void calculateNeed() {
		Collections.sort(productions);
		Production bestProduction = productions.get(productions.size() - 1);
		if (bestProduction.getProductionType() != ProductionType.COMMODITY) {
			int commodityNeed = (bestProduction.getMultiplier() - commodity.getMultiplier()) * (int) population;
			commodity.setNeed(commodityNeed);
		}
		if (bestProduction.getProductionType() == ProductionType.COMMODITY) {
			int commodityNeed = commodity.getMultiplier() * (int) population;
			commodity.setNeed(-commodityNeed);
		}

		if (bestProduction.getProductionType() == ProductionType.SCIENCE) {
			int crystalNeed = (bestProduction.getMultiplier() - crystal.getMultiplier()) * (int) population;
			crystal.setNeed(crystalNeed);
		}
		if (bestProduction.getProductionType() == ProductionType.CONSTRUCTION) {
			int materialNeed = (bestProduction.getMultiplier() - material.getMultiplier()) * (int) population;
			material.setNeed(materialNeed);
		}
		if (bestProduction.getProductionType() == ProductionType.MATERIAL) {
			int materialNeed = bestProduction.getMultiplier() * (int) population;
			material.setNeed(-materialNeed);
		}
		if (bestProduction.getProductionType() == ProductionType.CRYSTAL) {
			int crystalNeed = bestProduction.getMultiplier() * (int) population;
			crystal.setNeed(-crystalNeed);
		}
	}

	public double getDistance(Point point) {
		return Geo.getDistance(this.point, point);
	}

	public Point getPoint() {
		return point;
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

	public String getName() {
		return name;
	}

	public int getPopulation() {
		return (int) population;
	}
}
