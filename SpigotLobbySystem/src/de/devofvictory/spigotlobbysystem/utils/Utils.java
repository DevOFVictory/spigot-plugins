package de.devofvictory.spigotlobbysystem.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.spigotlobbysystem.main.Main;

public class Utils {
	
	public static boolean JumpPads;
	
	public static double jumppadBoostX;
	public static double jumppadBoostY;
	
	
	static Utils plugin;
	
	public static Utils getInstance() {
		return plugin;
	}
	
	public static void setInv(Player p) {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();
		compassMeta.setDisplayName("§a§lTeleporter");
		ArrayList<String> lore = new ArrayList<>();
		
		lore.add("§7[RightClick]");
		compassMeta.setLore(lore);
		compass.setItemMeta(compassMeta);
		p.getInventory().clear();
		p.getInventory().setItem(0, compass);
		
	}
	
	
	public static void openCompass(Player p) {
		if (Main.getInstance().getConfig().get("CompassInv") == null) {
			Inventory inv = Bukkit.createInventory(null, 27, "§8» §a§lTeleporter");
			p.openInventory(inv);
			p.playSound(p.getLocation(), Sound.CLICK, (float)2, (2));
		
		}else {
			FileConfiguration cfg = Main.getInstance().getConfig();
			Inventory compass = (Inventory) cfg.get("CompassInv");
			p.openInventory(compass);
			p.playSound(p.getLocation(), Sound.CLICK, (float)2, (2));
		}
	}
	
	public static ArrayList<Player> allowBuild = new ArrayList<>();
	
	public static HashMap<Player, ItemStack[]> gmItems = new HashMap<>();
	
	// Lobby-Spawn
	static String LobbyName;
	static double X;
	static double Y;
	static double Z;
	static float YAW;
	static float PITCH;
	
	// Getters
	public static String getSpawnWorld() {
		return LobbyName;
	}
	public static double getSpawnX() {
		return X;
	}
	public static double getSpawnY() {
		return Y;
	}
	public static double getSpawnZ() {
		return Z;
	}
	public static float getSpawnYaw() {
		return YAW;
	}
	public static float getSpawnPitch() {
		return PITCH;
	}
	
	// Setters
	public static void setSpawnWorld(String world) {
		LobbyName = world;
	}
	public static void setSpawnX(double x) {
		X = x;
	}
	public static void setSpawnY(double y) {
		Y = y;
	}
	public static void setSpawnZ(double z) {
		Z = z;
	}
	public static void setSpawnYaw(float yaw) {
		YAW = yaw;
	}
	public static void setSpawnPitch(float pitch) {
		PITCH = pitch;
	}
	
	public static Location spawnLocation;
	// Lobby-Spawn END
	
	
	public static boolean isLobby(Player p) {
	
		if (p.getWorld().getName().equals(getSpawnWorld())) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isLobbySet() {
		if (Main.getInstance().getConfig().getString("Lobby.WORLD").equals("NotSet")) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("static-access")
	public static void setLobby(String world, double x, double y, double z, float yaw, float pitch) {
		getInstance().LobbyName = world;
		getInstance().X = x;
		getInstance().Y = y;
		getInstance().Z = z;
		getInstance().YAW = yaw;
		getInstance().PITCH = pitch;
		
		spawnLocation = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		Main.getInstance().getConfig().set("Lobby.WORLD", world);
		Main.getInstance().getConfig().set("Lobby.X", x);
		Main.getInstance().getConfig().set("Lobby.Y", y);
		Main.getInstance().getConfig().set("Lobby.Z", z);
		Main.getInstance().getConfig().set("Lobby.YAW", yaw);
		Main.getInstance().getConfig().set("Lobby.PITCH", pitch);
		Main.getInstance().saveConfig();
		Main.getInstance().reloadConfig();
	}
	

}
