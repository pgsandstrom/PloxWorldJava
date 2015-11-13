package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public interface Ai {

	void makeDecision(World world, Person person);
}
