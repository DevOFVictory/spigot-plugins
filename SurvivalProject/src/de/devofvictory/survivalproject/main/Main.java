package de.devofvictory.survivalproject.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.survivalproject.commands.Command_EC;
import de.devofvictory.survivalproject.commands.Command_Home;
import de.devofvictory.survivalproject.commands.Command_Invsee;
import de.devofvictory.survivalproject.commands.Command_PublicChest;
import de.devofvictory.survivalproject.commands.Command_Ride;
import de.devofvictory.survivalproject.commands.Command_Spucken;
import de.devofvictory.survivalproject.custom.CustomRecipes;
import de.devofvictory.survivalproject.custom.EpicPortal;
import de.devofvictory.survivalproject.inventorys.INV_ChooseColor;
import de.devofvictory.survivalproject.items.JetPack;
import de.devofvictory.survivalproject.items.SabberKanone;
import de.devofvictory.survivalproject.items.Zauberstab;
import de.devofvictory.survivalproject.listeners.Listener_OnBlockBreak;
import de.devofvictory.survivalproject.listeners.Listener_OnChat;
import de.devofvictory.survivalproject.listeners.Listener_OnDeath;
import de.devofvictory.survivalproject.listeners.Listener_OnDebug;
import de.devofvictory.survivalproject.listeners.Listener_OnJoinQuit;
import de.devofvictory.survivalproject.listeners.Listener_OnProjectileFire;
import de.devofvictory.survivalproject.listeners.TeleporterLogic;
import de.devofvictory.survivalproject.utils.TeleportersManager;

public class Main extends JavaPlugin {

	public static String Prefix = "§f[§5Survival§dProject§f] ";

	static Main plugin;

	@Override
	public void onEnable() {

		plugin = this;

		Bukkit.getPluginManager().registerEvents(new Listener_OnDeath(), this);
		Bukkit.getPluginManager().registerEvents(new Command_Home(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoinQuit(), this);
		Bukkit.getPluginManager().registerEvents(new Zauberstab(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnChat(), this);
		Bukkit.getPluginManager().registerEvents(new SabberKanone(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new JetPack(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnProjectileFire(), this);
		Bukkit.getPluginManager().registerEvents(new TeleporterLogic(), this);
		Bukkit.getPluginManager().registerEvents(new INV_ChooseColor(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDebug(), this);
		Bukkit.getPluginManager().registerEvents(new Command_Ride(), this);
		Bukkit.getPluginManager().registerEvents(new EpicPortal(), this);

		

		getCommand("publicchest").setExecutor(new Command_PublicChest());
		getCommand("home").setExecutor(new Command_Home());
		getCommand("spucken").setExecutor(new Command_Spucken());
		getCommand("ride").setExecutor(new Command_Ride());
		getCommand("invsee").setExecutor(new Command_Invsee());
		getCommand("ec").setExecutor(new Command_EC());

		CustomRecipes.load();
		
		TeleportersManager.importStats();
		
		
	}
	
	@Override
	public void onDisable() {
		
		TeleportersManager.exportStats();

		
	}

	public static Main getInstance() {
		return plugin;
	}
	

}
