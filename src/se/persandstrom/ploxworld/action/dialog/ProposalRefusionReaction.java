package se.persandstrom.ploxworld.action.dialog;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.interaction.Dialog;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public class ProposalRefusionReaction implements Action {

	private final Dialog dialog;
	private final Person acter;
	private final Person receiver;

	ProposalRefusionReactionType reaction;

	public ProposalRefusionReaction(Dialog dialog, Person acter, Person receiver) {
		this.dialog = dialog;
		this.acter = acter;
		this.receiver = receiver;

		reaction = react(dialog.getProposalType());
	}

	@Override
	public void execute() {
		dialog.setProposalRefusionReaction(reaction);
		Log.dialog(acter + " reacts to refusal with " + reaction);
	}

	@Override
	public void saveData(WorldData worldData) {
		//XXX NOT IMPLEMENTED
	}

	@Override
	public boolean isDecided() {
		return reaction != null;
	}

	@Override
	public void setDecision(String decision) {
		reaction = ProposalRefusionReactionType.valueOf(decision);
	}

	public ProposalRefusionReactionType react(Proposal.ProposalType proposalType) {
		if (acter.getAi() == null) {
			return null;
		}

		double aggressionRoll = receiver.getPersonality().getAggressionRoll(dialog.getPowerRatio());
		if (aggressionRoll > 50) {
			return ProposalRefusionReactionType.ATTACK;
		} else {
			return ProposalRefusionReactionType.LEAVE;
		}
	}


	public enum ProposalRefusionReactionType {
		ATTACK, LEAVE
	}
}
