package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.action.TransferWeapon;
import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

public class UpgradeGoal implements Goal {

	private final Weapon weapon;

	public UpgradeGoal(Weapon weapon) {
		this.weapon = weapon;
	}

	@Override
	public void execute(Person person) {
		Location location = person.getLocation();
		if (location instanceof Planet) {
			Planet planet = (Planet) location;
			if (planet.getWeapons().contains(weapon) == false) {
				throw new IllegalStateException();
			}

			new TransferWeapon(person, planet, weapon, TransferType.BUY).execute();
			//TODO: Sell old weapon. This requires inventory and lots of stuff
		}
	}
}
