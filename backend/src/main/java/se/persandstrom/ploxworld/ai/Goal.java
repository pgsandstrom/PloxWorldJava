package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

/**
 * If a Decision is made with a specific purpose in mind, it is kept as a Goal.
 */
public interface Goal {

	void execute(World world, Person person);
}
