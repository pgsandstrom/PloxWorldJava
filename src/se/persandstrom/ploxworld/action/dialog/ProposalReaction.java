package se.persandstrom.ploxworld.action.dialog;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.common.Log;
import se.persandstrom.ploxworld.interaction.Dialog;
import se.persandstrom.ploxworld.main.WorldData;
import se.persandstrom.ploxworld.person.Person;

public class ProposalReaction implements Action {

	private final Dialog dialog;
	private final Person acter;
	private final Person receiver;

	ProposalReactionType reaction;

	public ProposalReaction(Dialog dialog, Person acter, Person receiver) {
		this.dialog = dialog;
		this.acter = acter;
		this.receiver = receiver;

		reaction = react(dialog.getProposalType());
	}

	@Override
	public void execute() {
		dialog.setProposalReact(reaction);
		Log.dialog(receiver + " decides to " + reaction);
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
		reaction = ProposalReactionType.valueOf(decision);
	}

	public ProposalReactionType react(Proposal.ProposalType proposalType) {
		if(receiver.getAi() == null) {
			return null;
		}

		double aggressionRoll = receiver.getPersonality().getAggressionRoll(dialog.getPowerRatioForReceiver());
		int choiceLimit;
		switch (proposalType) {
			case BOARD:
				choiceLimit = 10;
				break;
			case GIVE_ALL:
				choiceLimit = 20;
				break;
			case GIVE_RANSOM:
				choiceLimit = 50;
				break;
			case LEAVE:
				if (dialog.getPowerRatioForReceiver() > 1.6 && aggressionRoll > 50) {
					return ProposalReactionType.ATTACK;
				} else {
					return ProposalReactionType.ACCEPT;
				}
			default:
				throw new IllegalStateException();
		}
		if (aggressionRoll > choiceLimit) {
			return ProposalReactionType.REFUSE;
		} else {
			return ProposalReactionType.ACCEPT;
		}
	}


	public enum ProposalReactionType {
		ACCEPT, REFUSE, ATTACK
	}
}
