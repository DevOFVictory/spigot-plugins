package de.devofvictory.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.listeners.ChatListener;
import de.devofvictory.listeners.SignListener;

public class Main 
	extends JavaPlugin{
	
	 public void onEnable() {
		 getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-");
		 getLogger().info("[DoubleJump] Enable");
		 getLogger().info("   Version 1.0" );
	 	 getLogger().info("§aColored2000 by DevOFVictory erfolgreich geladen und aktiviert!");
	 	 getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-");
	 	//Listener Register
		 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new ChatListener(), this);
		 pm.registerEvents(new SignListener(), this);
    }
	 public void onDisable() {
		 getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-");
		 getLogger().info("[DoubleJump] Disable");
		 getLogger().info("   Version " + getDescription().getVersion());
	 	 getLogger().info("§aColored2000 by DevOFVictory erfolgreich deaktiviert!");
	 	 getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-");
    }
		
	}


