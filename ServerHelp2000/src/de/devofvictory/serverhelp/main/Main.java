package de.devofvictory.serverhelp.main;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.serverhelp.commands.Command_ServerHelp;
import de.devofvictory.serverhelp.listeners.Listener_OnCommand;

public class Main extends JavaPlugin{
	
	public static String Prefix = "";
	
	private static Main plugin;
	
	int x;
	public void onEnable() {
		initConfig();
		plugin = this;
		Bukkit.getConsoleSender().sendMessage("§f[§3AutoBroadcaster+Help§f] §aPlugin Version "+getDescription().getVersion()+" by DevOFVictory was enabled!");
		
		getCommand("serverhelp").setExecutor(new Command_ServerHelp());
		
		Bukkit.getPluginManager().registerEvents(new Listener_OnCommand(), this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
			
			@Override
			public void run() {
				sendBroadcast();
				
			}
		}, getConfig().getInt("AutoBroadcast.Delay")*20, getConfig().getInt("AutoBroadcast.Delay")*20*20);
		
		
		
		
		
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§f[§3AutoBroadcaster+Help§f] §cPlugin Version "+getDescription().getVersion()+" by DevOFVictory was disabled!");
		
	}
	
	
	
	
	private void initConfig() {
		reloadConfig();
		getConfig().addDefault("AutoBroadcast.Delay", 300);
		getConfig().addDefault("AutoBroadCastAmount", 3);
		
		getConfig().addDefault("AutoBroadCast.1.lines", 5);
		getConfig().addDefault("AutoBroadCast.1.line1", "&c####################################");
		getConfig().addDefault("AutoBroadCast.1.line2", "         &f[&4YourServer.net&f]");
		getConfig().addDefault("AutoBroadCast.1.line3", " ");
		getConfig().addDefault("AutoBroadCast.1.line4", "     &aTest out &6/playtime");
		getConfig().addDefault("AutoBroadCast.1.line5", "&c####################################");
		
		getConfig().addDefault("AutoBroadCast.2.lines", 5);
		getConfig().addDefault("AutoBroadCast.2.line1", "&c####################################");
		getConfig().addDefault("AutoBroadCast.2.line2", "         &f[&4&lNEWS:&f]");
		getConfig().addDefault("AutoBroadCast.2.line3", " - Some BugFixes");
		getConfig().addDefault("AutoBroadCast.2.line4", " - Now you can use /tpa");
		getConfig().addDefault("AutoBroadCast.2.line5", "&c####################################");
		
		getConfig().addDefault("AutoBroadCast.3.lines", 2);
		getConfig().addDefault("AutoBroadCast.3.line1", "&4You need help?");
		getConfig().addDefault("AutoBroadCast.3.line2", "- &aType &6/help&a!!");
		
		ArrayList<String> helpCommands = new ArrayList<>();
		helpCommands.add("/help");
		helpCommands.add("/hilfe");
		
		getConfig().addDefault("Help.HelpCommands", helpCommands);
		getConfig().addDefault("Help.lines", 5);
		getConfig().addDefault("Help.line.1", "&8[]=-=-=-=-=-=-=-=-=&3[HELP]&8=-=-=-=-=-=-=-=-=[]");
		getConfig().addDefault("Help.line.2", "&6/help &f> &cGet this help");
		getConfig().addDefault("Help.line.3", "&6/spawn &f> &cTeleport to spawn");
		getConfig().addDefault("Help.line.4", "&6/tpa &f> &cSend a teleport request");
		getConfig().addDefault("Help.line.5", "&8[]=-=-=-=-=-=-=-=-=&3[HELP]&8=-=-=-=-=-=-=-=-=[]");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public static Main getInstance() {
		return plugin;
	}
	
	public int amount = getConfig().getInt("AutoBroadCastAmount");
	private void sendBroadcast() {
		
		Random random = new Random();
		
		int zufall = random.nextInt(amount)+1;
		
		if (zufall != x) {
		x = zufall;
			
		for (int i = 1; i <= getConfig().getInt("AutoBroadCast."+zufall+".lines"); i++) {
			Bukkit.broadcastMessage(getConfig().getString("AutoBroadCast."+zufall+".line"+i).replace('&', '§'));
		}
			
		}else {
			zufall = random.nextInt(amount)+1;
			
			if (zufall != x) {
			x = zufall;
				
			for (int i = 1; i <= getConfig().getInt("AutoBroadCast."+zufall+".lines"); i++) {
				Bukkit.broadcastMessage(getConfig().getString("AutoBroadCast."+zufall+".line"+i).replace('&', '§'));
			}
		}else {
			
			zufall = random.nextInt(amount)+1;
			
			if (zufall != x) {
			x = zufall;
				
			for (int i = 1; i <= getConfig().getInt("AutoBroadCast."+zufall+".lines"); i++) {
				Bukkit.broadcastMessage(getConfig().getString("AutoBroadCast."+zufall+".line"+i).replace('&', '§'));
			}
		}
		
	}
		}
		}
}
