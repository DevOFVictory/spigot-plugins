package de.devofvictory.biomicploz.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.biomicploz.commands.Command_Build;
import de.devofvictory.biomicploz.commands.Command_GS;
import de.devofvictory.biomicploz.commands.Command_Restart;
import de.devofvictory.biomicploz.inventorys.INV_Menu;
import de.devofvictory.biomicploz.inventorys.INV_MyPlots;
import de.devofvictory.biomicploz.inventorys.INV_Plot;
import de.devofvictory.biomicploz.inventorys.INV_PlotSettings;
import de.devofvictory.biomicploz.listeners.Listener_OnBlockPhysics;
import de.devofvictory.biomicploz.listeners.Listener_OnBuild;
import de.devofvictory.biomicploz.listeners.Listener_OnDebugChatMsg;
import de.devofvictory.biomicploz.listeners.Listener_OnEntityDamage;
import de.devofvictory.biomicploz.listeners.Listener_OnExplode;
import de.devofvictory.biomicploz.listeners.Listener_OnMark;
import de.devofvictory.biomicploz.listeners.Listener_OnMove;
import de.devofvictory.biomicploz.utils.FileManager;
import de.devofvictory.biomicploz.utils.PlotSaver;

public class Main extends JavaPlugin{
	
	public static String Prefix = "§2•§a●§2§lCity§a§lBuild §8» §7";
	static Main plugin;
	public static World plotworld;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new Listener_OnMark(), this);
		Bukkit.getPluginManager().registerEvents(new INV_Menu(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDebugChatMsg(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuild(), this);
		Bukkit.getPluginManager().registerEvents(new INV_MyPlots(), this);
		Bukkit.getPluginManager().registerEvents(new INV_Plot(), this);
		Bukkit.getPluginManager().registerEvents(new INV_PlotSettings(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnExplode(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBlockPhysics(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnEntityDamage(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnMove(), this);

		
		getCommand("gs").setExecutor(new Command_GS());
		getCommand("powermode").setExecutor(new Command_Build());
		getCommand("warnedrestart").setExecutor(new Command_Restart());
		
		FileManager.create("plots");
		PlotSaver.importPlots();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("biomicplots.debug")) {
				player.sendMessage(Main.Prefix+"§aSystem erfolgreich aktiviert!");
			}
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				plotworld = Bukkit.getWorld("plotworld");
				
			}
		},10*20);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				PlotSaver.exportPlots();
				
			}
		}.runTaskTimerAsynchronously(this, 15*20*60, 15*20*60);
		
		
	}
	
	
	@Override
	public void onDisable() {
		PlotSaver.exportPlots();
	}
	
	public static Main getInstance() {
		return plugin;
	}
	
	

}
