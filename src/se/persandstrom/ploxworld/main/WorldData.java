package se.persandstrom.ploxworld.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.TotalProduction;

import com.google.gson.annotations.Expose;

public class WorldData {

	@Expose Map<ProductionType, TotalProduction> totalProduction;	// This is a map so it is easily parsed in the frontend

	public WorldData(World world) {

		totalProduction = new HashMap<>();

		for (ProductionType productionType : ProductionType.values()) {
			TotalProduction totalProduction = new TotalProduction(productionType);
			Set<Planet> planets = world.getPlanets();
			planets.forEach(new Consumer<Planet>() {
				@Override
				public void accept(Planet planet) {
					Production production = planet.getProduction(productionType);
					totalProduction.addStorage(production.getStorage());
					totalProduction.addProduction(production.getProduced());

				}
			});

			this.totalProduction.put(totalProduction.getProductionType(), totalProduction);
		}
	}
}
