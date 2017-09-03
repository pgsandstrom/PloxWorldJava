package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.person.Person;

import com.google.gson.annotations.Expose;

public class MoveTransition extends FightTransition {

	@Expose private final String name = "move";

	@Expose private final String actorName;
	@Expose private final int startDistance;

	public MoveTransition(Fight fight, Person actor, int startDistance) {
		super(fight);
		this.actorName = actor.getName();
		this.startDistance = startDistance;
	}
}
