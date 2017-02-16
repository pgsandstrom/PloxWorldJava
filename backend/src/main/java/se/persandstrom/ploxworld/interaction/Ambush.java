package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.action.ambush.AmbushAction;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class Ambush {

	private final World world;
	private final Person acter;
	private final Person receiver;

	AmbushAction.AmbushType ambushType;

	public Ambush(World world, Person acter, Person receiver) {
		this.world = world;
		this.acter = acter;
		this.receiver = receiver;
	}

	public void start() {
		world.executeAction(new AmbushAction(this, acter, receiver));

		switch (ambushType) {
			case ATTACK:
				new Fight(world, acter, receiver).start();
				return;
			case DIALOG:
				new Dialog(world, acter, receiver).start();
				return;
			case LEAVE:
				//TODO: Other get chance to attack?
				return;
		}
	}

	public void setAmbushType(AmbushAction.AmbushType ambushType) {
		this.ambushType = ambushType;
	}
}
