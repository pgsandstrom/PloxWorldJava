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

}
