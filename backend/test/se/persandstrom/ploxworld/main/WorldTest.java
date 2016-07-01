package se.persandstrom.ploxworld.main;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class WorldTest {

	@Test
	public void runSomeWorld() throws IOException, URISyntaxException {
		for (int worldIndex = 0; worldIndex < 50; worldIndex++) {
			World world = new World();
			for (int turnIndex = 0; turnIndex < 5000; turnIndex++) {
				world.progressTurn();
			}
		}

		World world = new World();
		for (int turnIndex = 0; turnIndex < 50000; turnIndex++) {
			world.progressTurn();
		}
	}

}