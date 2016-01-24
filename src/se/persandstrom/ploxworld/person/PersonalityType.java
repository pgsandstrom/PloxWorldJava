package se.persandstrom.ploxworld.person;

import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.MinerAi;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.common.Rand;

public enum PersonalityType {

	TRADE, MINER, PIRATE;



	public static Personality getPersonality(Class<? extends Ai> aiClass) {

//		boolean lol1 = aiClass.isInstance(MinerAi.class);
//		boolean lol2 = MinerAi.class.isInstance(aiClass);
//		boolean lol3 = aiClass.isAssignableFrom(MinerAi.class);
//		boolean lol4 = MinerAi.class.isAssignableFrom(aiClass);

		if (aiClass.isAssignableFrom(MinerAi.class)) {
			return new Personality(Rand.bound(20, 90));
		} else if (aiClass.isAssignableFrom(TraderAi.class)) {
			return new Personality(Rand.bound(0, 60));
		} else if (aiClass.isAssignableFrom(PirateAi.class)) {
			return new Personality(Rand.bound(70, 100));
		} else {
			throw new IllegalStateException();
		}
	}
}
