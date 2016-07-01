package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class MineDecision implements Decision {

	private final World world;
	private final Person person;

	public MineDecision(World world, Person person) {
		this.world = world;
		this.person = person;
	}

	@Override
	public void execute() {
		Location location = person.getLocation();
		if (location.getMineable().isPresent()) {
			location.getMineable().get().mine(world, person);
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public Goal getGoal() {
		return null;
	}
}
