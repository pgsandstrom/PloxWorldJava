package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Transition;
import se.persandstrom.ploxworld.person.Person;

import com.google.gson.annotations.Expose;

public class MoveTransition implements Transition {

	@Expose private final String name = "move";

	@Expose private final String actorName;
	@Expose private final int startDistance;
	@Expose private final int finishDistance;

	public MoveTransition(Person actor, int startDistance, int finishDistance) {
		this.actorName = actor.getName();
		this.startDistance = startDistance;
		this.finishDistance = finishDistance;
	}
}
