package se.persandstrom.ploxworld.main;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

	@Test
	public void runSomeWorld() {
		for (int worldIndex = 0; worldIndex < 100; worldIndex++) {
			World world = new World();
			for (int turnIndex = 0; turnIndex < 500; turnIndex++) {
				world.progressTurn();
			}
		}
	}

	@Test
	public void lol() {
	}

}