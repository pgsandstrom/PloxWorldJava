package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.common.Geo;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class AmbushDecision implements Decision {

	private final World world;
	private final Person person;

	public AmbushDecision(World world, Person person) {
		this.world = world;
		this.person = person;
	}

	@Override
	public void execute() {
		Person ambushedPerson = world.getPersonsShuffled().stream()
				.filter(p -> Geo.getDistance(p.getPoint(), AmbushDecision.this.person.getPoint()) < 10)
				.filter(p -> p.equals(person) == false)
				.findFirst().get();

		Log.pirate(person + " ambushed " + ambushedPerson);
	}
}
