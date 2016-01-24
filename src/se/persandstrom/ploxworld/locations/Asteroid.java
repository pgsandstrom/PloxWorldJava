package se.persandstrom.ploxworld.locations;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

import com.google.gson.annotations.Expose;

public class Asteroid extends Location {

	@Expose private final double miningEfficiency;

	public Asteroid(String name, Point point, double miningEfficiency) {
		super(name, point);
		this.miningEfficiency = miningEfficiency;
	}

	public double getMiningEfficiency() {
		return miningEfficiency;
	}

	public void mine(Person person) {
		double miningRoll = Rand.boundDouble(0, miningEfficiency);
		int mined = (int) miningRoll;
		person.getShip().addStorage(ProductionType.MATERIAL, mined);
		Log.mine(person.getName() + " mined for " + mined);
	}
}
