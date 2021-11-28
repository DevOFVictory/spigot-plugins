package de.devofvictory.builder.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.builder.listeners.Listener_OnBuild;
import de.devofvictory.builder.listeners.Listener_OnChat;
import de.devofvictory.builder.listeners.Listener_OnJoin;
import de.devofvictory.builder.listeners.Listener_OnQuit;

public class Main extends JavaPlugin{
	
	public static String Prefix = "§8§l[§a§lBuildServer§8§l] ";
	public static ArrayList<String> allowBuild = new ArrayList<>();
	
	static Main plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getConsoleSender().sendMessage(Prefix+"§aPlugin wurde geladen!");
		
		Bukkit.getPluginManager().registerEvents(new Listener_OnChat(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuild(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnQuit(), this);
		
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.setGameMode(GameMode.CREATIVE);
				}
				
			}
		}, 40);
		
	}
	
	public static Main getInstance() {
		return plugin;
		
	}

}
