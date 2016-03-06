package se.persandstrom.ploxworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.production.Production;
import se.persandstrom.ploxworld.production.ProductionType;
import se.persandstrom.ploxworld.production.ProductionData;

import com.google.gson.annotations.Expose;

public class WorldData {

	public static final String KEY_MINED = "Minerals mined";
	public static final String KEY_REPAIRED = "Ships repaired";
	public static final String KEY_GOODS_TRADED = "Goods traded";
	public static final String KEY_GOODS_STOLEN = "Goods stolen";
	public static final String KEY_WEAPONS_BOUGHT = "Weapons bought";

	private final World world;

	@Expose Map<ProductionType, ProductionData> productionData;    // This is a map so it is easily parsed in the frontend
	@Expose Map<String, Long> data;

	public WorldData(World world) {
		this.world = world;
		this.data = new HashMap<>();
	}

	public void calculateProductionData() {
		productionData = new HashMap<>();
		for (ProductionType productionType : ProductionType.values()) {
			ProductionData productionData = new ProductionData(productionType);
			List<Location> locations = world.getLocations();
			locations.forEach(planet -> {
				if (planet.getTradeable().isPresent()) {
					Production production = planet.getTradeable().get().getProduction(productionType);
					productionData.addStorage(production.getStorage());
					productionData.addProduction(production.getProduced());
				}
			});

			this.productionData.put(productionData.getProductionType(), productionData);
		}
	}

	public void addDataValue(String key, int add) {
		Long value = data.get(key);
		Long newValue = value != null ? value + add : add;
		data.put(key, newValue);
	}
}
