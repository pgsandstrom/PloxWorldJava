package se.persandstrom.ploxworld.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandTest {

	@Test
	public void bound() {
		for (int i = 0; i < 100; i++) {
			double percentage = Rand.bound(1, 25);
			System.out.println(percentage);
		}
	}

	@Test
	public void percentage() {
		for (int i = 0; i < 100; i++) {
			double percentage = Rand.percentage(0.2, 0.5);
			assertTrue(percentage > 0.2 && percentage < 0.5);
		}
	}
}