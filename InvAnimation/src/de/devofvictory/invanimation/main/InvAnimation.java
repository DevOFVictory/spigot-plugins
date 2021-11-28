package de.devofvictory.invanimation.main;

import org.bukkit.plugin.java.JavaPlugin;

public class InvAnimation extends JavaPlugin{
	
	private static InvAnimation plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
	}
	
	public static InvAnimation getInstance() {
		return plugin;
	}

}
