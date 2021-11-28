package de.chilipro.chililobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import de.chilipro.chililobby.main.Main;

public class SpawnsFile {
	
	
	
	public static World spawnWorld;
	public static int spawnX;
	public static int spawnY;
	public static int spawnZ;
	public static float spawnYaw;
	public static float spawnPitch;
	
	public static World pvpWorld;
	public static int pvpX;
	public static int pvpY;
	public static int pvpZ;
	public static float pvpYaw;
	public static float pvpPitch;
	
	public static World wargameWorld;
	public static int wargameX;
	public static int wargameY;
	public static int wargameZ;
	public static float wargameYaw;
	public static float wargamePitch;
	
	public static World citybuildWorld;
	public static int citybuildX;
	public static int citybuildY;
	public static int citybuildZ;
	public static float citybuildYaw;
	public static float citybuildPitch;
	
	public static World knockWorld;
	public static int knockX;
	public static int knockY;
	public static int knockZ;
	public static float knockYaw;
	public static float knockPitch;
	
	public static World crystalWorld;
	public static int crystalX;
	public static int crystalY;
	public static int crystalZ;
	public static float crystalYaw;
	public static float crystalPitch;
	
	
	public static void createCfg() {
		Main.getInstance().saveConfig();
		Main.getInstance().reloadConfig();
	}
	
			
		
	
	
	public static void saveCfg() {
		Main.getInstance().saveConfig();
	}
	
	
	public static void readData() {
		
		FileConfiguration cfg = Main.getInstance().getConfig();
		
		try {
		spawnWorld = Bukkit.getWorld(cfg.getString("Spawn.world"));
		spawnX = cfg.getInt("Spawn.x");
		spawnY = cfg.getInt("Spawn.y");
		spawnZ = cfg.getInt("Spawn.z");
		spawnYaw = cfg.getLong("Spawn.yaw");
		spawnPitch = cfg.getLong("Spawn.pitch");
		
		wargameWorld = Bukkit.getWorld(cfg.getString("WarGame.world"));
		wargameX = cfg.getInt("WarGame.x");
		wargameY = cfg.getInt("WarGame.y");
		wargameZ = cfg.getInt("WarGame.z");
		wargameYaw = cfg.getLong("WarGame.yaw");
		wargamePitch = cfg.getLong("WarGame.pitch");
		
		crystalWorld = Bukkit.getWorld(cfg.getString("Crystal.world"));
		crystalX = cfg.getInt("Crystal.x");
		crystalY = cfg.getInt("Crystal.y");
		crystalZ = cfg.getInt("Crystal.z");
		crystalYaw = cfg.getLong("Crystal.yaw");
		crystalPitch = cfg.getLong("Crystal.pitch");
		
		pvpWorld = Bukkit.getWorld(cfg.getString("PvP.world"));
		pvpX = cfg.getInt("PvP.x");
		pvpY = cfg.getInt("PvP.y");
		pvpZ = cfg.getInt("PvP.z");
		pvpYaw = cfg.getLong("PvP.yaw");
		pvpPitch = cfg.getLong("PvP.pitch");
		
		knockWorld = Bukkit.getWorld(cfg.getString("Knock.world"));
		knockX = cfg.getInt("Knock.x");
		knockY = cfg.getInt("Knock.y");
		knockZ = cfg.getInt("Knock.z");
		knockYaw = cfg.getLong("Knock.yaw");
		knockPitch = cfg.getLong("Knock.pitch");
		
		citybuildWorld = Bukkit.getWorld(cfg.getString("CityBuild.world"));
		citybuildX = cfg.getInt("CityBuild.x");
		citybuildY = cfg.getInt("CityBuild.y");
		citybuildZ = cfg.getInt("CityBuild.z");
		citybuildYaw = cfg.getLong("CityBuild.yaw");
		citybuildPitch = cfg.getLong("CityBuild.pitch");
		
		Main.spawn = new Location(spawnWorld, spawnX, spawnY, spawnZ, spawnYaw, spawnPitch);
		Main.pvp = new Location(pvpWorld, pvpX, pvpY, pvpZ, pvpYaw, pvpPitch);
		Main.wargame = new Location(wargameWorld, wargameX, wargameY, wargameZ, wargameYaw, wargamePitch);
		Main.citybuild = new Location(citybuildWorld, citybuildX, citybuildY, citybuildZ, citybuildYaw, citybuildPitch);
		Main.knock = new Location(knockWorld, knockX, knockY, knockZ, knockYaw, knockPitch);
		Main.crystal = new Location(crystalWorld, crystalX, crystalY, crystalZ, crystalYaw, crystalPitch);
		
	
		
		
		
		}catch (NullPointerException | IllegalArgumentException ex) {
			Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§cEin Fehler ist aufgetreten!");
			
		}
	}

}
