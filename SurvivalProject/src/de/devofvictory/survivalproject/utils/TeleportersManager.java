package de.devofvictory.survivalproject.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.devofvictory.survivalproject.custom.Teleporter;

public class TeleportersManager {

	public static void importStats() {
		FileManager.create("teleporters");
		
		List<String> stringList = FileManager.teleportersCfg.getStringList("teleporters");
		
		for (String s : stringList) {
			
			String[] splitted = s.split(";");
			
			double x = Double.parseDouble(splitted[0]);
			double y = Double.parseDouble(splitted[1]);
			double z = Double.parseDouble(splitted[2]);
			String world = splitted[3];
			
			double ax = Double.parseDouble(splitted[4]);
			double ay = Double.parseDouble(splitted[5]);
			double az = Double.parseDouble(splitted[6]);
			float yaw = Float.parseFloat(splitted[7]);
			float pitch = Float.parseFloat(splitted[8]);

			
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			Location arrivalLoc = new Location(Bukkit.getWorld(world), ax, ay, az, yaw, pitch);
			
			String color = TeleporterUtils.getColorString(loc.getBlock().getType());
			
			TeleporterUtils.allLocations.add(loc);
			
			Teleporter teleporter = new Teleporter(loc);
			teleporter.setArrivalLocation(arrivalLoc);
			teleporter.setColor(color);
			
			TeleporterUtils.allTeleporters.put(teleporter, color);
		}
		
		
	}
		
	
	public static void exportStats() {
		
		List<String> stringList = new ArrayList<>();
		
		for (Location loc : TeleporterUtils.allLocations) {
			
			if (TeleporterUtils.isTeleporter(loc)) {
				Teleporter teleporter = TeleporterUtils.getTeleporter(loc);
				Location arrivialLoc = teleporter.getArrivalLocation();
				
				String s = loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ() + ";" + loc.getWorld().getName() + ";" 
				+ arrivialLoc.getBlockX() + ";" + arrivialLoc.getBlockY() + ";" + arrivialLoc.getBlockZ() + ";" + arrivialLoc.getYaw() + ";" +arrivialLoc.getPitch();

				stringList.add(s);
			}
			
			
		}

		FileManager.teleportersCfg.set("teleporters", stringList);
		
		FileManager.save("teleporters");
	}
}
