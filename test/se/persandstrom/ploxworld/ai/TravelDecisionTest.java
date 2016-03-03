package se.persandstrom.ploxworld.ai;

import org.junit.Test;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.TestUtil;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.person.Person;

public class TravelDecisionTest {

	/**
	 * This is a "roflcopter"-test to quickly test different locations when we switch algorithms...
	 */
	@Test
	public void lol() {
		String[] working = new String[]{
				"(17,416)",
				"(317,401)",
				"(336,216)",
				"(395,296)",
				"(126,351)",
				"(258,406)",
				"(20,111)",
				"(113,32)",
				"(302,427)",
				"(396,486)",
				"(82,84)",
				"(280,474)",
				"(289,39)",
				"(497,281)",
				"(344,300)",
				"(353,447)",
				"(17,55)",
				"(191,92)",
				"(5,290)",
				"(308,393)",
				"(3,187)",
				"(31,244)",
				"(82,138)",
				"(494,497)",
				"(403,450)",
				"(459,331)",
				"(57,197)",
				"(357,499)",
				"(146,407)",
				"(460,479)",
				"(434,121)",
				"(446,189)",
				"(366,443)",
				"(429,3)",
				"(262,11)",
				"(378,141)",
				"(313,442)",
				"(369,57)",
				"(238,493)",
				"(345,212)",
				"(45,114)",
				"(470,463)",
				"(207,54)",
				"(393,286)",
				"(137,320)",
				"(380,38)",
				"(34,213)",
				"(113,44)",
				"(144,453)",
				"(172,493)",
				"(79,139)",
				"(325,244)",
				"(186,224)",
				"(279,152)",
				"(204,261)",
				"(263,337)",
				"(15,444)",
				"(121,94)",
				"(127,294)",
				"(232,380)",
				"(8,105)",
				"(293,90)",
				"(175,473)",
				"(416,489)",
				"(9,412)",
				"(290,226)",
				"(143,33)",
				"(182,440)",
				"(394,315)",
				"(420,95)",
				"(91,118)",
				"(118,495)",
				"(401,290)",
				"(449,75)",
				"(49,421)",
				"(159,495)",
				"(212,276)",
				"(289,332)",
				"(87,460)",
				"(423,134)",
				"(463,495)",
				"(472,329)",
				"(99,270)",
				"(394,492)",
				"(144,287)",
				"(437,379)",
				"(65,315)",
				"(429,295)",
				"(19,156)",
				"(153,481)",
				"(398,320)",
				"(426,8)",
				"(9,82)",
				"(383,353)",
				"(441,270)",
				"(446,456)",
				"(61,103)",
				"(189,156)",
				"(44,157)",
				"(240,48)",
				"(67,475)",
				"(133,103)"};

		for (int i = 0; i < working.length; i += 2) {
			Point from = new Point(working[i]);
			Point to = new Point(working[i + 1]);
			Person person = TestUtil.getPerson(from);
			Location location = TestUtil.getPlanet(to);
			testTravel(person, location);
		}

		for (int i = 0; i < 1000000; i++) {
			Location toPlanet = TestUtil.getPlanet();
			Person person = TestUtil.getPerson();

			try {
				testTravel(person, toPlanet);
			} catch (Exception e) {
				System.out.println("FAIL");
				return;
			}
		}
	}

	private void testTravel(Person person, Location toLocation) {

		Location fromLocation = person.getLocation();
		TravelDecision travelDecision = new TravelDecision(person, toLocation);

		double startDistance = travelDecision.getDistance();
		travelDecision.execute();
		double endDistance = travelDecision.getDistance();

		if (endDistance > startDistance) {
			System.out.println("fail from: " + fromLocation.getPoint());
			System.out.println("fail to:   " + toLocation.getPoint());
			throw new RuntimeException();
		} else if (endDistance < startDistance) {
//				System.out.println("success from: " + fromPlanet.getPoint());
//				System.out.println("success to:   " + toPlanet.getPoint());
//			System.out.println(fromPlanet.getPoint());
//			System.out.println(toPlanet.getPoint());
		}
	}
}