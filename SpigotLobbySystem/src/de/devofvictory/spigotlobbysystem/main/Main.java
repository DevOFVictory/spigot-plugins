package de.devofvictory.spigotlobbysystem.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.spigotlobbysystem.command.Command_Lobby;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnBlockBreak;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnBlockPlace;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnClick;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnDrop;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnHotbarSwitch;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnInteract;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnJoin;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnMove;
import de.devofvictory.spigotlobbysystem.listeners.Listener_OnSneak;
import de.devofvictory.spigotlobbysystem.utils.Utils;



public class Main extends JavaPlugin{
	
	public static String mainPrefix = "§d§lLobbySystem §5§l»§r ";
	public static String Prefix = "";
	
	public static Main plugin;
	
	
	public void onEnable() {
		
		plugin = this;
		
		getServer().getPluginManager().registerEvents(new Listener_OnJoin(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnInteract(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnHotbarSwitch(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnMove(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnBlockPlace(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnBlockBreak(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnDrop(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnClick(), this);
		getServer().getPluginManager().registerEvents(new Listener_OnSneak(), this);
		
		
		getCommand("lobby").setExecutor(new Command_Lobby());
		
		Bukkit.getConsoleSender().sendMessage(mainPrefix+"§aPlugin enabled!");
		
		saveConfig();
		initConfig();
		
	}
	
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(mainPrefix+"§cPlugin disabled!");
		
	}
	
	public static void reloadCfg() {
		getInstance().reloadConfig();
		Utils.JumpPads = getInstance().getConfig().getBoolean("Jumppads.enable");
		Utils.jumppadBoostX = getInstance().getConfig().getDouble("Jumppads.boostX");
		Utils.jumppadBoostY = getInstance().getConfig().getDouble("Jumppads.boostY");
		Prefix = getInstance().getConfig().getString("Prefix").replace('&', '§');
		
	}
	
	private void initConfig() {
		getConfig().addDefault("Prefix", "&d&lLobbySystem &5&l| §f");
		getConfig().addDefault("Jumppads.enable", true);
		getConfig().addDefault("Jumppads.boostX", 3.5);
		getConfig().addDefault("Jumppads.boostY", 1.5);
		getConfig().addDefault("Lobby.WORLD", "NotSet");
		getConfig().addDefault("Lobby.X", 0);
		getConfig().addDefault("Lobby.Y", 0);
		getConfig().addDefault("Lobby.Z", 0);
		getConfig().addDefault("Lobby.YAW", 0);
		getConfig().addDefault("Lobby.PITCH", 0);
		
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		
		
		Utils.JumpPads = getInstance().getConfig().getBoolean("Jumppads.enable");
		Utils.jumppadBoostX = getInstance().getConfig().getDouble("Jumppads.boostX");
		Utils.jumppadBoostY = getInstance().getConfig().getDouble("Jumppads.boostY");
		Prefix = getConfig().getString("Prefix").replace('&', '§');
		
		Utils.setSpawnWorld(getConfig().getString("Lobby.WORLD"));
		Utils.setSpawnX(getConfig().getDouble("Lobby.X"));
		Utils.setSpawnY(getConfig().getDouble("Lobby.Y"));
		Utils.setSpawnZ(getConfig().getDouble("Lobby.Z"));
		Utils.setSpawnYaw((float)getConfig().getDouble("Lobby.YAW"));
		Utils.setSpawnPitch((float)getConfig().getDouble("Lobby.PITCH"));
		
		if (!getConfig().getString("Lobby.WORLD").equals("NotSet")) {
			Utils.setLobby(getConfig().getString("Lobby.WORLD"), getConfig().getDouble("Lobby.X"), getConfig().getDouble("Lobby.Y"), getConfig().getDouble("Lobby.Z"), (float)getConfig().get("Lobby.YAW"), (float)getConfig().get("Lobby.PITCH"));
		}
	}
	
	public static Main getInstance() {
		return plugin;
	}
	
}

