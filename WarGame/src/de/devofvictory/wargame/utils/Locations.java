package de.devofvictory.wargame.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations {
	
	public static Location middle = new Location(Bukkit.getWorld("map"), 430, 100, -409);
	
	public static Location getRandomLocation(Location location, int radius) {
		
		Location loc = new Location(location.getWorld(), ThreadLocalRandom.current().nextInt(location.getBlockX()-radius, location.getBlockX()+radius), 100, ThreadLocalRandom.current().nextInt(location.getBlockZ()-radius, location.getBlockZ()+radius));
		
		return loc;
		
	}
		
	}


