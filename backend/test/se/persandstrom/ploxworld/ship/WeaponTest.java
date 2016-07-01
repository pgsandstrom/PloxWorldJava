package se.persandstrom.ploxworld.ship;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class WeaponTest {

	@Test
	public void testGetMissingWeaponEasistResearched() throws Exception {
		Set<Weapon> weapons = new HashSet<>();
		weapons.add(Weapon.weapons.get(0));

		Optional<BuyableItem> missingWeaponEasistResearched = Weapon.getMissingItemEasistResearched(weapons);
		Assert.assertEquals(Weapon.weapons.get(1), missingWeaponEasistResearched.get());

		weapons.add(Weapon.weapons.get(1));

		missingWeaponEasistResearched = Weapon.getMissingItemEasistResearched(weapons);
		Assert.assertEquals(Weapon.weapons.get(2), missingWeaponEasistResearched.get());
	}

	@Test
	public void testGetNextWeapon() throws Exception {
		Optional<BuyableItem> nextWeapon = Weapon.getNextItem(Weapon.weapons.get(4));
		Assert.assertEquals(Weapon.weapons.get(5), nextWeapon.get());

		nextWeapon = Weapon.getNextItem(Weapon.weapons.get(6));
		Assert.assertFalse(nextWeapon.isPresent());

	}
}