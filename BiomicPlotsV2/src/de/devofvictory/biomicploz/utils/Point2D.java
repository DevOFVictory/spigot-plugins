package de.devofvictory.biomicploz.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Point2D {
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int x;
	private int y;
	
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Location toLocation() {
		return new Location(Bukkit.getWorld("plotworld"), x, 71, y);
	}
	
	public static Point2D fromLocation(Location location) {
		return new Point2D(location.getBlockX(), location.getBlockZ());
	}
	
	@Override
	public String toString() {
		return "("+x+"/"+y+")";
	}

}
