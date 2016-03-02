package se.persandstrom.ploxworld.locations.property;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

import com.google.gson.annotations.Expose;

public class Mineable {

	@Expose private final double miningEfficiency;

	public Mineable(double miningEfficiency) {
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
