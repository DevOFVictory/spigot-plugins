package de.devofvictory.survivalproject.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;

import de.devofvictory.survivalproject.custom.Teleporter;

public class TeleporterUtils {

	public static HashMap<Teleporter, String> allTeleporters = new HashMap<>();
	public static ArrayList<Location> allLocations = new ArrayList<>();

	public static String teleporterName = "§5§lTeleport-Block";

	public static Teleporter getTeleporter(Location loc) {
		
		for (Teleporter teleporter : allTeleporters.keySet()) {
			if (teleporter.getLocation().equals(loc)) {
//				Bukkit.broadcastMessage("2 "+TeleporterUtils.getTeleporter(new Location(Bukkit.getWorld("flat"), -72, 4, 224)).getCounterpart().getLocation());

				return teleporter;
			}
		}
		return null;
	}
	
	public static boolean isTeleporter(Location loc) {
		if (loc.getBlock().getType().name().contains("_WOOL")) {
		
			for (Teleporter teleporter : allTeleporters.keySet()) {
				if (teleporter.getLocation().equals(loc)) {
//					Bukkit.broadcastMessage("1 "+TeleporterUtils.getTeleporter(new Location(Bukkit.getWorld("flat"), -72, 4, 224)).getCounterpart().getLocation());
					return true;
				}
			}

		} else {
			return false;
		}
		return false;
	}

	public static ArrayList<Teleporter> getTeleportersInColor(String color) {
		ArrayList<Teleporter> teleporters = new ArrayList<>();

		for (Teleporter teleporter : allTeleporters.keySet()) {
			if (teleporter.getColor().equalsIgnoreCase(color)) {
				teleporters.add(teleporter);
			}
		}
		return teleporters;

	}

	public static String getColorString(Material type) {
		return type.name().split("_")[0];
	}

}
