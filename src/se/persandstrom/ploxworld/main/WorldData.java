package se.persandstrom.ploxworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.TotalProduction;

import com.google.gson.annotations.Expose;

public class WorldData {

	@Expose Map<ProductionType, TotalProduction> totalProduction;    // This is a map so it is easily parsed in the frontend

	public WorldData(World world) {

		totalProduction = new HashMap<>();

		for (ProductionType productionType : ProductionType.values()) {
			TotalProduction totalProduction = new TotalProduction(productionType);
			List<Location> locations = world.getLocations();
			locations.forEach(planet -> {
				if (planet.getTradeable().isPresent()) {
					Production production = planet.getTradeable().get().getProduction(productionType);
					totalProduction.addStorage(production.getStorage());
					totalProduction.addProduction(production.getProduced());
				}
			});

			this.totalProduction.put(totalProduction.getProductionType(), totalProduction);
		}
	}
}
