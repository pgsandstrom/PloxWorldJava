package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class Ambush {

	private final World world;
	private final Person aggressor;
	private final Person victim;

	private final double powerRatio;

	public Ambush(World world, Person aggressor, Person victim) {
		this.world = world;
		this.aggressor = aggressor;
		this.victim = victim;
		this.powerRatio = aggressor.getShip().getPower() / victim.getShip().getPower();
	}

	public void start() {
		ConfrontDialogRun confrontDialogRun = confrontTalkRun();

		switch (confrontDialogRun) {
			case ATTACK:
				new Fight(world, aggressor, victim).start();
				return;
			case DIALOG:
				new Dialog(world, aggressor, victim).start();
				return;
			case LEAVE:
				//TODO: Other get chance to attack?
				return;
		}
	}

	public ConfrontDialogRun confrontTalkRun() {
		double aggressionRoll = aggressor.getPersonality().getAggressionRoll(powerRatio);

		if (aggressionRoll > 100) {
			return ConfrontDialogRun.ATTACK;
		} else if (aggressionRoll > 50) {
			return ConfrontDialogRun.DIALOG;
		} else {
			return ConfrontDialogRun.LEAVE;
		}
	}

	enum ConfrontDialogRun {
		ATTACK, DIALOG, LEAVE
	}
}
