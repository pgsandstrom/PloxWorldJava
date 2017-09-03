package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Transition;
import se.persandstrom.ploxworld.fight.Fight;

import com.google.gson.annotations.Expose;

public abstract class FightTransition  implements Transition {

	@Expose private int distance;

	public FightTransition(Fight fight) {
		this.distance = fight.getDistance();
	}

}
