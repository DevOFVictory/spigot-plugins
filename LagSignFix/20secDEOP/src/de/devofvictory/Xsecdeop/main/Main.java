package de.devofvictory.Xsecdeop.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.Xsecdeop.listeners.PlayerChatListener;

public class Main extends JavaPlugin {

	

	public void onEnable() {
		 getLogger().info("###############################################");
		 getLogger().info("             [Security] Enable");
		 getLogger().info("                  Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich geladen und aktiviert!");
	 	 getLogger().info("###############################################");
	 	 
		 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new PlayerChatListener(), this);

	 	 

	 	 
	 	 
	 }

		

	 }
