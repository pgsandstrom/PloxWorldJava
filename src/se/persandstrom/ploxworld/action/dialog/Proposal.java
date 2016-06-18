package se.persandstrom.ploxworld.action.dialog;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.interaction.Dialog;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public class Proposal implements Action {

	private final Dialog dialog;
	private final Person acter;
	private final Person receiver;

	ProposalType proposal;

	public Proposal(Dialog dialog, Person acter, Person receiver) {
		this.dialog = dialog;
		this.acter = acter;
		this.receiver = receiver;

		proposal = getProposal();
	}

	@Override
	public void execute() {
		dialog.setProposalType(proposal);

		Log.dialog("");
		Log.dialog(acter + " is confronting " + receiver + " with " + proposal);
	}

	@Override
	public void saveData(WorldData worldData) {
		//XXX NOT IMPLEMENTED
	}

	@Override
	public boolean isDecided() {
		return proposal != null;
	}

	@Override
	public void setDecision(String decision) {
		proposal = ProposalType.valueOf(decision);
	}

	public ProposalType getProposal() {
		if (acter.getAi() == null) {
			return null;
		}

		double aggressionRoll = acter.getPersonality().getAggressionRoll(dialog.getPowerRatio());
		if (aggressionRoll > 90) {
			return ProposalType.BOARD;
		} else if (aggressionRoll > 80) {
			return ProposalType.GIVE_ALL;
		} else if (aggressionRoll > 50) {
			return ProposalType.GIVE_RANSOM;
		} else {
			return ProposalType.LEAVE;
		}
	}

	public enum ProposalType {
		GIVE_ALL, GIVE_RANSOM, BOARD, LEAVE
	}
}
