package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TransferWeapon {

	private final Person person;
	private final Planet planet;
	private final Weapon weapon;
	private final TransferType type;

	public TransferWeapon(Person person, Planet planet, Weapon weapon, TransferType type) {
		this.person = person;
		this.planet = planet;
		this.weapon = weapon;
		this.type = type;
	}

	public void execute() {
		switch (type) {
			case BUY:
				//auto sell weapon:
				person.addMoney(person.getShip().getWeapon().getSellCost());

				person.addMoney(-weapon.purchaseCost);
				planet.addMoney(weapon.purchaseCost);
				person.getShip().setWeapon(weapon);
				break;
			case SELL:
				//TODO
				throw new NotImplementedException();
		}

	}
}
