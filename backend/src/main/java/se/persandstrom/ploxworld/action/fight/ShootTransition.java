package se.persandstrom.ploxworld.action.fight;

import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.person.Person;

import com.google.gson.annotations.Expose;

public class ShootTransition extends FightTransition {

	@Expose private final String name = "shoot";

	@Expose private final String actorName;
	@Expose private final boolean hit;
	@Expose private final int damage;

	public ShootTransition(Fight fight,Person actor, boolean hit, int damage) {
		super(fight);
		this.actorName = actor.getName();
		this.hit = hit;
		this.damage = damage;
	}
}
