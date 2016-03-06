package se.persandstrom.ploxworld.locations.property;

import se.persandstrom.ploxworld.action.Mine;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

import com.google.gson.annotations.Expose;

public class Mineable {

	@Expose private final double miningEfficiency;

	public Mineable(double miningEfficiency) {
		this.miningEfficiency = miningEfficiency;
	}

	public double getMiningEfficiency() {
		return miningEfficiency;
	}

	public void mine(World world, Person person) {
		double miningRoll = Rand.boundDouble(0, miningEfficiency);
		int mined = (int) miningRoll;
		world.executeAction(new Mine(person, mined));
	}
}
