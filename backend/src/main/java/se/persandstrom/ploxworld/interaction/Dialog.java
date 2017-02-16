package se.persandstrom.ploxworld.interaction;

import se.persandstrom.ploxworld.action.dialog.Proposal;
import se.persandstrom.ploxworld.action.dialog.ProposalReaction;
import se.persandstrom.ploxworld.action.dialog.ProposalRefusionReaction;
import se.persandstrom.ploxworld.fight.Fight;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;

public class Dialog {

	private final World world;
	private final Person acter;
	private final Person receiver;

	private final double powerRatio;

	private Proposal.ProposalType proposalType;
	private ProposalReaction.ProposalReactionType proposalReact;
	private ProposalRefusionReaction.ProposalRefusionReactionType proposalRefusionReaction;

	public Dialog(World world, Person acter, Person receiver) {
		this.world = world;
		this.acter = acter;
		this.receiver = receiver;
		powerRatio = acter.getShip().getPower() / receiver.getShip().getPower();
	}

	public void start() {
		world.executeAction(new Proposal(this, acter, receiver));
		world.executeAction(new ProposalReaction(this, acter, receiver));

		switch (proposalType) {
			case BOARD:
			case GIVE_ALL:
			case GIVE_RANSOM:
				switch (proposalReact) {
					case ACCEPT:
						new AcceptThreat(world, acter, receiver).start(proposalType);
						return;
					case REFUSE:
						world.executeAction(new ProposalRefusionReaction(this, acter, receiver));
						switch (proposalRefusionReaction) {
							case ATTACK:
								new Fight(world, acter, receiver).start();
								return;
							case LEAVE:
								return;
						}
						return;
					case ATTACK:
						new Fight(world, receiver, acter).start();
						return;
				}
				return;
			case LEAVE:
				switch (proposalReact) {
					case ATTACK:
						new Fight(world, receiver, acter).start();
						return;
					case ACCEPT:
						return;
					case REFUSE:
						throw new IllegalStateException();
				}
		}

	}

	public double getPowerRatio() {
		return powerRatio;
	}

	public double getPowerRatioForReceiver() {
		return 1 / powerRatio;
	}

	public Proposal.ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(Proposal.ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public ProposalReaction.ProposalReactionType getProposalReact() {
		return proposalReact;
	}

	public void setProposalReact(ProposalReaction.ProposalReactionType proposalReact) {
		this.proposalReact = proposalReact;
	}

	public ProposalRefusionReaction.ProposalRefusionReactionType getProposalRefusionReaction() {
		return proposalRefusionReaction;
	}

	public void setProposalRefusionReaction(ProposalRefusionReaction.ProposalRefusionReactionType proposalRefusionReaction) {
		this.proposalRefusionReaction = proposalRefusionReaction;
	}
}
