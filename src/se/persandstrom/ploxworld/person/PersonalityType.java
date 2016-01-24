package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.MinerAi;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.common.Rand;

public enum PersonalityType {

	TRADE, MINER, PIRATE;

	public Personality getPersonality() {
		switch (this) {
			case TRADE:
				return new Personality(Rand.bound(0, 60));
			case MINER:
				return new Personality(Rand.bound(20, 90));
			case PIRATE:
				return new Personality(Rand.bound(70, 100));
			default:
				throw new IllegalStateException();
		}
	}

	public TraderAi getAi() {
		switch (this) {
			case TRADE:
				return new TraderAi();
			case MINER:
				new MinerAi();
			case PIRATE:
				new PirateAi();
			default:
				throw new IllegalStateException();
		}
	}
}
