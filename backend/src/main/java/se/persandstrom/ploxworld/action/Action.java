package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public interface Action {

	void execute();

	Person getActor();

	void saveData(WorldData worldData);

	boolean isDecided();

	void setDecision(String decision);
}
