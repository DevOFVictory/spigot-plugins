package de.devofvictory.levelbarapi.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
	}
	
	public static Main getInstance() {
		return plugin;
	}

}
