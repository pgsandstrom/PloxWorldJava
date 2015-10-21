package se.persandstrom.ploxworld.common;

public class Geo {

	public static double getDistance(Point point1, Point point2) {
		int xDiff = Math.abs(point1.x - point2.x);
		int yDiff = Math.abs(point1.y - point2.y);
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}
}
