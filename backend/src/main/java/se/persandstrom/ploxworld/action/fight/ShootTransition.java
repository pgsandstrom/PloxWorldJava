package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.action.Transition;
import se.persandstrom.ploxworld.person.Person;

import com.google.gson.annotations.Expose;

public class ShootTransition implements Transition {

	@Expose private final String name = "shoot";

	@Expose private final String actorName;
	@Expose private final boolean hit;
	@Expose private final int damage;

	public ShootTransition(Person actor, boolean hit, int damage) {
		this.actorName = actor.getName();
		this.hit = hit;
		this.damage = damage;
	}
}
