package de.devofvictory.pvpzone.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.devofvictory.pvpzone.commands.Command_PvPZone;
import de.devofvictory.pvpzone.listeners.Listener_OnBla;
import de.devofvictory.pvpzone.listeners.Listener_OnJoin;
import de.devofvictory.pvpzone.listeners.Listener_OnMove;
import de.devofvictory.pvpzone.listeners.Listener_OnPvp;
import de.devofvictory.pvpzone.utils.BukkitSerialization;
import de.devofvictory.pvpzone.utils.Variables;

public class PvPZone extends JavaPlugin{
	
	private static PvPZone plugin;
	private static WorldEditPlugin worldeditApi;
	public static String Prefix = "§8¬ §4PvP§6Zone §8¬ §7";
	
	public static File cfgFile;
	public static FileConfiguration cfg;
	
	@Override
	public void onEnable() {
		plugin = this;
		if (Bukkit.getPluginManager().getPlugin("WorldEdit") != null) {
			worldeditApi = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
		}
		registerListeners();
		registerCommands();
		setupConfig();
	}
	
	@Override
	public void onDisable() {
		cfg.set("Area.World", Variables.areaLoc1.getWorld().getName());
		cfg.set("Area.1.X", Variables.areaLoc1.getBlockX());
		cfg.set("Area.1.Y", Variables.areaLoc1.getBlockY());
		cfg.set("Area.1.Z", Variables.areaLoc1.getBlockZ());
		
		cfg.set("Area.2.X", Variables.areaLoc2.getBlockX());
		cfg.set("Area.2.Y", Variables.areaLoc2.getBlockY());
		cfg.set("Area.2.Z", Variables.areaLoc2.getBlockZ());
		
		if (Variables.spawn != null) {
			cfg.set("Spawn.X", Variables.spawn.getX());
			cfg.set("Spawn.Y", Variables.spawn.getY());
			cfg.set("Spawn.Z", Variables.spawn.getZ());
			cfg.set("Spawn.World", Variables.spawn.getWorld().getName());
			cfg.set("Spawn.Yaw", Variables.spawn.getY());
			cfg.set("Spawn.Pitch", Variables.spawn.getPitch());
		}
		
		if (Variables.kit != null)
			cfg.set("Kit", BukkitSerialization.itemStackArrayToBase64(Variables.kit));
		
		try {
			cfg.save(cfgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private void setupConfig() {
		saveResource("config.yml", false);
		cfgFile = new File(getPlugin().getDataFolder(), "config.yml");
		cfg = YamlConfiguration.loadConfiguration(cfgFile);
		
		int x1 = cfg.getInt("Area.1.X");
		int y1 = cfg.getInt("Area.1.Y");
		int z1 = cfg.getInt("Area.1.Z");
		
		int x2 = cfg.getInt("Area.2.X");
		int y2 = cfg.getInt("Area.2.Y");
		int z2 = cfg.getInt("Area.2.Z");
		
		String world = cfg.getString("Area.World");
		
		Location loc1 = new Location(Bukkit.getWorld(world), x1, y1, z1);
		Location loc2 = new Location(Bukkit.getWorld(world), x2, y2, z2);
		
		Variables.areaLoc1 = loc1;
		Variables.areaLoc2 = loc2;
		
		if (cfg.isSet("Kit")) {
			try {
				Variables.kit = BukkitSerialization.itemStackArrayFromBase64(cfg.getString("Kit"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (cfg.isSet("Spawn")) {
			double x = cfg.getDouble("Spawn.X");
			double y = cfg.getDouble("Spawn.Y");
			double z = cfg.getDouble("Spawn.Z");
			String spawnWorld = cfg.getString("Spawn.World");
			double yaw = cfg.getDouble("Spawn.Yaw");
			double pitch = cfg.getDouble("Spawn.Pitch");
			
			Variables.spawn = new Location(Bukkit.getWorld(spawnWorld), x, y, z, (float)yaw, (float)pitch);
		}
	}

	private void registerCommands() {
		getCommand("pvpzone").setExecutor(new Command_PvPZone());
		
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Listener_OnMove(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnPvp(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBla(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoin(), this);
	}

	public static PvPZone getPlugin() {
		return plugin;
	}
	
	public static WorldEditPlugin getWorldeditApi() {
		return worldeditApi;
	}
	
	

}
