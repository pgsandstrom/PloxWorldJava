package se.persandstrom.ploxworld.common;

import com.google.gson.annotations.Expose;

public class Point {

	@Expose public final int x;
	@Expose public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point oldPoint, int xDiff, int yDiff) {
		this.x = oldPoint.x + xDiff;
		this.y = oldPoint.y + yDiff;
	}

	public Point(String stringPoint) {
		String substring = stringPoint.substring(1, stringPoint.length() - 1);
		String[] split = substring.split(",");
		x = Integer.valueOf(split[0]);
		y = Integer.valueOf(split[1]);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
