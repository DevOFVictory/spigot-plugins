package de.devofvictory.wargame.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;

public class RandomPos {
	
	public static Location getLoc(Location center, int radius) {
		
		Location loc = new Location(center.getWorld(), ThreadLocalRandom.current().nextInt(center.getBlockX()-radius, center.getBlockX()+radius), 100, ThreadLocalRandom.current().nextInt(center.getBlockZ()-radius, center.getBlockZ()+radius));

		return loc;
	}

}
