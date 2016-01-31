package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.common.Rand;

public class Personality {

	public final int aggression;

	Personality(int aggression) {
		this.aggression = aggression;
	}

	public double getAggressionRoll() {
		return getAggressionRoll(1);
	}

	public double getAggressionRoll(double powerRatio) {
		return roll(aggression) * powerRatio;
	}

	private int roll(int attributeValue) {
		return Rand.bound(attributeValue - 50, attributeValue + 50);
	}

//	private double limit(double roll) {
//		if (roll < 0) {
//			return 0;
//		} else if (roll > 100) {
//			return 100;
//		} else {
//			return roll;
//		}
//	}


}
