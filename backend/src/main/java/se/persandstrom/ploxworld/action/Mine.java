package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.production.ProductionType;

public class Mine implements Action {

	private final Person person;
	private final int mined;

	public Mine(Person person, int mined) {
		this.person = person;
		this.mined = mined;
	}

	@Override
	public void execute() {
		person.getShip().addStorage(ProductionType.MATERIAL, mined);
		Log.mine(person.getName() + " mined for " + mined);
	}

	@Override
	public Person getActor() {
		return person;
	}

	@Override
	public void saveData(WorldData worldData) {
		worldData.addDataValue(WorldData.KEY_MINED, mined);
	}

	@Override
	public boolean isDecided() {
		return true;
	}

	@Override
	public void setDecision(String decision) {
		throw new IllegalStateException();
	}
}
