package se.persandstrom.ploxworld.interaction;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import se.persandstrom.ploxworld.action.dialog.Proposal;
import se.persandstrom.ploxworld.action.dialog.ProposalReaction;
import se.persandstrom.ploxworld.action.dialog.ProposalRefusionReaction;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.main.World;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.person.Personality;
import se.persandstrom.ploxworld.ship.ShipBase;
import se.persandstrom.ploxworld.ship.Weapon;

import static org.junit.Assert.*;

public class DialogTest {

	@Test
	public void aggressiveDialogsCoward() throws IOException, URISyntaxException {
		Dialog dialog = new Dialog(new World(), getAggressive(), getCoward());
		dialog.start();

		assertEquals(Proposal.ProposalType.BOARD, dialog.getProposalType());
		assertEquals(ProposalReaction.ProposalReactionType.ACCEPT, dialog.getProposalReact());
		assertEquals(null, dialog.getProposalRefusionReaction());
	}

	@Test
	public void aggressiveDialogsAggressive() throws IOException, URISyntaxException {
		Dialog dialog = new Dialog(new World(), getAggressive(), getAggressive());
		dialog.start();

		assertEquals(Proposal.ProposalType.BOARD, dialog.getProposalType());
		assertEquals(ProposalReaction.ProposalReactionType.REFUSE, dialog.getProposalReact());
		assertEquals(ProposalRefusionReaction.ProposalRefusionReactionType.ATTACK, dialog.getProposalRefusionReaction());
	}

	@Test
	public void cowardDialogsCoward() throws IOException, URISyntaxException {
		Dialog dialog = new Dialog(new World(), getCoward(), getCoward());
		dialog.start();

		assertEquals(Proposal.ProposalType.LEAVE, dialog.getProposalType());
		assertEquals(ProposalReaction.ProposalReactionType.ACCEPT, dialog.getProposalReact());
		assertEquals(null, dialog.getProposalRefusionReaction());
	}

	@Test
	public void cowardDialogsAggressiveStrong() throws IOException, URISyntaxException {
		Dialog dialog = new Dialog(new World(), getCoward(), getAggressiveStrong());
		dialog.start();

		assertEquals(Proposal.ProposalType.LEAVE, dialog.getProposalType());
		assertEquals(ProposalReaction.ProposalReactionType.ATTACK, dialog.getProposalReact());
		assertEquals(null, dialog.getProposalRefusionReaction());
	}


	private Person getAggressive() {
		return new Person(new PirateAi(), new Personality(500), "aggressive", null, 1000);
	}

	private Person getAggressiveStrong() {
		Person person = new Person(new PirateAi(), new Personality(1000), "aggressive extreme", null, 1000);
		person.getShip().setWeapon(Weapon.weapons.get(5));
		person.getShip().setShipBase(ShipBase.shipBases.get(1));
		return person;
	}

	private Person getCoward() {
		return new Person(new TraderAi(), new Personality(-500), "coward", null, 1000);
	}


}