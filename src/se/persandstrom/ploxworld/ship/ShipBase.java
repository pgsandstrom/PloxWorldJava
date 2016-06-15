package se.persandstrom.ploxworld.ship;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PointlessArithmeticExpression")
public class ShipBase extends BuyableItem {

	public static List<ShipBase> shipBases = new ArrayList<>();
	static {
		shipBases.add(new ShipBase("Simple",	30, 30,		1, 9000,	1000));
		shipBases.add(new ShipBase("Simple II",	40, 40,		1, 12000,	2000));
		shipBases.add(new ShipBase("Hunter",	35, 35,		2, 20000,	3000));
		shipBases.add(new ShipBase("Hunter II",	55, 40,		2, 30000,	5000));
		shipBases.add(new ShipBase("Fatty",		35, 60,		0, 20000,	3000));
		shipBases.add(new ShipBase("Fatty II",	40, 100,	0, 30000,	5000));
		shipBases.add(new ShipBase("Digger",	60, 60,		1, 20000,	3000));
		shipBases.add(new ShipBase("Digger II",	80, 80,		1, 30000,	5000));
	}

	public static ShipBase getFirst() {
		return shipBases.get(0);
	}

	public final int health;
	public final int storage;
	public final int weaponSlots;

	ShipBase(String name, int health, int storage, int weaponSlots, int purchaseCost, int researchCost) {
		super(name, purchaseCost, researchCost);
		this.health = health;
		this.storage = storage;
		this.weaponSlots = weaponSlots;
	}

	public int getPiratePower() {
		return health * 1 + storage * 1 + weaponSlots * 100;
	}

	public int getTradePower() {
		return health * 1 + storage * 2 + weaponSlots * 10;
	}

	public int getMinerPower() {
		return health * 1 + storage * 1 + weaponSlots * 50;
	}

}
