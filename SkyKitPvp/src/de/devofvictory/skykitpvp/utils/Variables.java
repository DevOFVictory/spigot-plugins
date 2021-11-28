package de.devofvictory.skykitpvp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;

public class Variables {
	
	public static String prefix = "§4[§eSky§6Kit§cPvp§c§4] ";
	public static boolean falldamage = false;
	public static double jumpPadPower = 3;
	public static double pvpHeight = 117;
	
	public static String mysql_host = "localhost";
	public static String mysql_user = "skykitpvp";
	public static String mysql_password = "password";
	public static String mysql_database = "skykitpvp";
	
	public static String signLine0 = "§6§l%rank%";
	public static String signLine1 = "§3%name%";
	public static String signLine2 = "§2%kills%";
	public static String signLine3 = "§4%deaths%";
	
	public static String villagerName = "§e§lKits";
	public static String witchName = "§d§lItems";
	public static String zombieName = "§2§lArena";
	
	public static int coinsPerFrame = 10;
	
	public static int coinsPerKill = 30;
	
	public static int deathHeight = 80;
	
	public static ArrayList<Kit> regeisterdKits = new ArrayList<Kit>();
	
	public static int itemFrameAmount = 5;
	
	
	public static ArrayList<ItemFrame> itemFrames = new ArrayList<ItemFrame>();
	
	public static Location[] skullLocs = new Location[5];
	public static Location[] signLocs = new Location[5];
	
	public static Location spawnLocation = new Location(Bukkit.getWorld("world"), -0.5, 119, 3.5);
	
	public static Location dailyloot_location = new Location(Bukkit.getWorld("world"), 2, 118, 5);
	public static int dailyloot_from = 1;
	public static int dailyloot_to = 50;
	public static long dailyloot_cooldown = 86400000L;
	
	
	public static Location getSpawnLocation() {
		return spawnLocation;
	}
	
	public static HashMap<String, Inventory> inventories = new HashMap<String, Inventory>();
	
	public static String worldguardSpawnRegionName = "spawn";
	
	private static HashMap<Player, Player> lastDamager = new HashMap<Player, Player>();
	
	public static void setLastDamager(Player player, Player killer) {
		Variables.lastDamager.put(player, killer);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				resetLastDamager(player);
				
			}
		}, 5*20);
		
	}
	
	public static Player getLastDamager(Player player) {
		return lastDamager.containsKey(player) ? lastDamager.get(player) : null;
	}
	
	public static void resetLastDamager(Player player) {
		lastDamager.remove(player);
	}

}
