package se.persandstrom.ploxworld.ai;

import se.persandstrom.ploxworld.action.TransferWeapon;
import se.persandstrom.ploxworld.common.TransferType;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.locations.property.Civilization;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.ship.Weapon;

public class UpgradeGoal implements Goal {

	private final Weapon weapon;

	public UpgradeGoal(Weapon weapon) {
		this.weapon = weapon;
	}

	@Override
	public void execute(World world, Person person) {
		Location location = person.getLocation();
		if (location.getCivilization().isPresent()) {
			Civilization civilization = location.getCivilization().get();
			if (civilization.getWeapons().contains(weapon) == false) {
				throw new IllegalStateException();
			}

			world.executeAction(new TransferWeapon(person, civilization, weapon, TransferType.BUY));
			//TODO: Sell old weapon. This requires inventory and lots of stuff
		}
	}
}
