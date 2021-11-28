package de.devofvictory.cm.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.cm.Listeners.OnChat;
import de.devofvictory.cm.Listeners.OnCommandListener;
import de.devofvictory.cm.commands.Command_ClearChat;
import de.devofvictory.cm.commands.Command_CmdSpy;
import de.devofvictory.cm.commands.Command_GlobalMute;


public class Main extends JavaPlugin{
	
	public static String Prefix = "[§aChat§2Manager§r] ";
	public static String TeamChatPrefix = "[§aTeam§2Chat§r] ";
	public static String CmdSpyPrefix = "[§3Command§bSpy§r] ";
	
	public static boolean globalMute = false;
	
	private static Main plugin;
	
	public void onEnable() {
		 getLogger().info("###############################################");
		 getLogger().info("           [ChatManager] Enable");
		 getLogger().info("                 Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich geladen und aktiviert!");
	 	 getLogger().info("###############################################");
	 	 
	 	 //Register Listeners
	 	 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new OnChat(), this);
		 pm.registerEvents(new OnCommandListener(), this);
		
		 
		 //Register Commands
		 getCommand("clearchat").setExecutor(new Command_ClearChat());
		 getCommand("cmdspy").setExecutor(new Command_CmdSpy());
		 getCommand("globalmute").setExecutor(new Command_GlobalMute());
		 
		 plugin = this;
	}
	
	public void onDisable() {
		 getLogger().info("###############################################");
		 getLogger().info("           [ChatManager] Enable");
		 getLogger().info("                 Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich entladen und deaktiviert!");
	 	 getLogger().info("###############################################");
		
	}
	
	public static Main getInstance() {
		return plugin;
	}

}
