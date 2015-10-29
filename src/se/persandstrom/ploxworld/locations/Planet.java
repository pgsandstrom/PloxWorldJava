package se.persandstrom.ploxworld.locations;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Point;

import com.google.gson.annotations.Expose;

public class Planet {

	private static final double POPULATION_GROWTH = 1.01;

	@Expose private final String name;
	@Expose private final Point point;

	@Expose private int maxPopulation;
	@Expose private double population;

	@Expose private int money;
	@Expose private int commodity;
	@Expose private int commodityMultiplier;
	@Expose private int commodityWorkers;
	@Expose private int production;
	@Expose private int productionMultiplier;
	@Expose private int productionWorkers;
	@Expose private int material;
	@Expose private int materialMultiplier;
	@Expose private int materialWorkers;
	@Expose private int science;
	@Expose private int scienceMultiplier;
	@Expose private int scienceWorkers;
	@Expose private int crystal;
	@Expose private int crystalMultiplier;
	@Expose private int crystalWorkers;

	public Planet(String name, Point point, int maxPopulation, double population, int money, int commodity, int commodityMultiplier,
			int production, int productionMultiplier, int material, int materialMultiplier,
			int science, int scienceMultiplier, int crystal, int crystalMultiplier) {
		this.name = name;
		this.point = point;
		this.maxPopulation = maxPopulation;
		this.population = population;
		this.money = money;
		this.commodity = commodity;
		this.commodityMultiplier = commodityMultiplier;
		this.production = production;
		this.productionMultiplier = productionMultiplier;
		this.material = material;
		this.materialMultiplier = materialMultiplier;
		this.science = science;
		this.scienceMultiplier = scienceMultiplier;
		this.crystal = crystal;
		this.crystalMultiplier = crystalMultiplier;
	}

	public void progressTurn() {
		population = population * POPULATION_GROWTH;
		if (population > maxPopulation) {
			population = maxPopulation;
		}
		this.commodity += this.commodityWorkers * this.commodityMultiplier;
		this.material += this.materialWorkers * this.materialMultiplier;
		int productionProduced = this.productionWorkers * this.productionMultiplier;
		this.production += productionProduced;
		this.material -= productionProduced;
		this.crystal += this.crystalWorkers * this.crystalMultiplier;
		int scienceProduced = this.scienceWorkers * this.scienceMultiplier;
		this.science += scienceProduced;
		this.crystalMultiplier -= scienceProduced;

		if (this.material < 0) {
			throw new IllegalStateException("Material is " + this.material + " at " + this.name + ".");
		}
		if (this.crystal < 0) {
			throw new IllegalStateException("Crystal is " + this.crystal + " at " + this.name + ".");
		}
	}

	public double getDistance(Point point) {
		return Geo.getDistance(this.point, point);
	}

	public Point getPoint() {
		return point;
	}
}
