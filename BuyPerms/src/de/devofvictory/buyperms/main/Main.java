package de.devofvictory.buyperms.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.buyperms.commands.Command_buyperms;


public class Main extends JavaPlugin{
	
	public static String Prefix = "[§5Buy§dPerms§r] ";
	
	public static File file = new File("plugins//BuyPerms//BuyPerms.yml");
	public static YamlConfiguration BuyPerms = YamlConfiguration.loadConfiguration(file);
	
	public void onEnable() {
		
		createCfg();
		getLogger().info("###############################################");
		 getLogger().info("              [BuyPerms] Enable");
		 getLogger().info("                 Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich geladen und aktiviert!");
	 	 getLogger().info("###############################################");
	 	 
	 	getCommand("buyperms").setExecutor(new Command_buyperms());
		
		
	}
	
	
	
	
	
	private void savePt() {
		try {
			BuyPerms.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[BuyPerms] Ein Fehler beim Speichern der BuyPerms-File ist aufgetreten");
		}
	
	}
	public void loadPt() {
		
		getConfig().options().copyDefaults(true);
		savePt();
	}
	public void reloadPt() {
		reloadConfig();

	}
	private void createCfg() {
		if(!file.exists()) {
			file.mkdir();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	}
