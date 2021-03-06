package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.MinerAi;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.common.Rand;

public enum PersonalityType {

	PLAYER, TRADE, MINER, PIRATE;

	public Personality getPersonality() {
		switch (this) {
			case PLAYER:
				return null;
			case TRADE:
				return new Personality(Rand.bound(0, 60));
			case MINER:
				return new Personality(Rand.bound(20, 90));
			case PIRATE:
				return new Personality(Rand.bound(700, 10000));
			default:
				throw new IllegalStateException();
		}
	}

	public Ai getAi() {
		switch (this) {
			case PLAYER:
				return null;
			case TRADE:
				return new TraderAi();
			case MINER:
				return new MinerAi();
			case PIRATE:
				return new PirateAi();
			default:
				throw new IllegalStateException();
		}
	}
}
