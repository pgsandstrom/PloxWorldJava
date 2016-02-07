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
		weapons.add(Weapon.SIMPLE);

		Optional<Weapon> missingWeaponEasistResearched = Weapon.getMissingWeaponEasistResearched(weapons);
		Assert.assertEquals(Weapon.X_LONG, missingWeaponEasistResearched.get());

		weapons.add(Weapon.X_LONG);

		missingWeaponEasistResearched = Weapon.getMissingWeaponEasistResearched(weapons);
		Assert.assertEquals(Weapon.BARR, missingWeaponEasistResearched.get());
	}
}