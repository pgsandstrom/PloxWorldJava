package se.persandstrom.ploxworld.main;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

	@Test
	public void runSomeWorld() throws IOException, URISyntaxException {
		for (int worldIndex = 0; worldIndex < 100; worldIndex++) {
			World world = new World();
			for (int turnIndex = 0; turnIndex < 500; turnIndex++) {
				world.progressTurn();
			}
		}
	}

}