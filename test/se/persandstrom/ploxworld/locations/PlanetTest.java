package se.persandstrom.ploxworld.locations;

import org.junit.Test;

import se.persandstrom.ploxworld.common.TestUtil;

import static org.junit.Assert.*;

public class PlanetTest {

	@Test
	public void getQuota() {
		Planet planet = TestUtil.getPlanet();
		double quota;

		//between limits:
		quota = planet.getQuota(130, 1, 2, 100, 300);
		assertEquals(1.85, quota, 0);
		quota = planet.getQuota(160, 1, 2, 100, 300);
		assertEquals(1.70, quota, 0);

		//max storage:
		quota = planet.getQuota(300, 3, 4, 100, 300);
		assertEquals(3, quota, 0);
		quota = planet.getQuota(3000, 3, 4, 100, 300);
		assertEquals(3, quota, 0);

		//min storage:
		quota = planet.getQuota(100, 3, 4, 100, 300);
		assertEquals(4, quota, 0);
		quota = planet.getQuota(50, 3, 4, 100, 300);
		assertEquals(4, quota, 0);
	}

}