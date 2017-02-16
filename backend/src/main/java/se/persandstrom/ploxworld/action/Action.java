package se.persandstrom.ploxworld.action;

import se.persandstrom.ploxworld.main.WorldData;

public interface Action {

	void execute();

	void saveData(WorldData worldData);

	boolean isDecided();

	void setDecision(String decision);
}
