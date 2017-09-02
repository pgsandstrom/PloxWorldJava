package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public class Repair implements Action {

	private static final int REPAIR_COST = 50;

	private final Person person;
	private final Civilization civilization;
	private final int repairAmount;

	/**
	 * Repairs to full health or max affordance
	 */
	public Repair(Person person, Civilization civilization) {
		this(person, civilization, calculateMaxRepair(person));
	}

	public Repair(Person person, Civilization civilization, int repairAmount) {
		this.person = person;
		this.civilization = civilization;
		this.repairAmount = repairAmount;
	}

	private static int calculateMaxRepair(Person person) {
		int damage = person.getShip().getMaxHealth() - person.getShip().getHealth();
		int affordRepair = person.getMoney() / REPAIR_COST;
		return Math.min(damage, affordRepair);
	}

	@Override
	public void execute() {
		person.getShip().addHealth(repairAmount);
		int cost = repairAmount * REPAIR_COST;
		person.addMoney(-cost);
		civilization.getTradeable().addMoney(cost);
	}

	@Override
	public Person getActor() {
		return person;
	}

	@Override
	public void saveData(WorldData worldData) {
		worldData.addDataValue(WorldData.KEY_REPAIRED, repairAmount);
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
