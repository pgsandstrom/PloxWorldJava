package se.persandstrom.ploxworld.common;

import com.google.gson.annotations.Expose;

public class Point {

	@Expose public final int x;
	@Expose public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
