package de.devofvictory.sr.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.sr.commands.Command_Trust;
import de.devofvictory.sr.listeners.Listener_OnCommand;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	public static String Prefix = "[§7§lSecurity§8§lRecoded§f] ";
	public static String noperms(String perm) {
		return Prefix+"§cHier für hast du leider nicht genug Rechte :( \n§8>> §f(§6"+perm+"§f)";
	}
	
	public static ArrayList<String> trustedplayers;
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("###############################################");
		Bukkit.getConsoleSender().sendMessage("              ["+getDescription().getFullName()+"] §aEnabled");
		Bukkit.getConsoleSender().sendMessage("                              §3Version: §b"+getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§2Plugin erfolgreich geladen und aktiviert!");
		Bukkit.getConsoleSender().sendMessage("###############################################");
		
		plugin = this;
		
		
		
		Main.trustedplayers.add("ChiliPro");
		Main.trustedplayers.add("DevOFVictory");
		Main.trustedplayers.add("blue_31");
		
		//Registrations:
		  //Listeners
		    
			PluginManager pm = Bukkit.getPluginManager();
		     pm.registerEvents(new Listener_OnCommand(), this);
		  
		  //Commands
		     getCommand("trust").setExecutor(new Command_Trust());
		
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("###############################################");
		Bukkit.getConsoleSender().sendMessage("              ["+getDescription().getFullName()+"] §cDesabled");
		Bukkit.getConsoleSender().sendMessage("                              §3Version: §b"+getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§2Plugin erfolgreich geladen und aktiviert!");
		Bukkit.getConsoleSender().sendMessage("###############################################");
		
	}
	
	public static Main getInstance() {
		return plugin;
	}

}
