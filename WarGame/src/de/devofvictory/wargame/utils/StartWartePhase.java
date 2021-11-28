package de.devofvictory.wargame.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.devofvictory.wargame.main.Main;

public class StartWartePhase {
	
	Player p;
	float time;
	public static float count;
	Plugin plugin;
	public static int scheduler = -1;
	
	public StartWartePhase(Plugin plugin, Player p, float time) {
		this.plugin = plugin;
		this.p = p;
		this.time = time;
		count = time;
		
		
		launch();
		
	}

	private void launch() {
		p.setExp(0);
		p.setLevel((int)count);
		Bukkit.getWorld("map").setGameRuleValue("difficulty", "peaceful");
		scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				count--;
				float exp = p.getExp();
				float remove = (float)1/time;
				float newExp = exp-remove;
				
				for (Player all : Bukkit.getOnlinePlayers()) {
				all.setExp(newExp);
				all.setLevel((int)count);
			}
				if (count <= 0) {
					Bukkit.getScheduler().cancelTask(scheduler);
					if (Bukkit.getOnlinePlayers().size() < 2) {
						Bukkit.broadcastMessage(Main.Prefix+"§eEs wird mindestens §61 §eweiterer Spieler benötigt!");
						if (Bukkit.getOnlinePlayers().size() > 0) {
							new StartWartePhase(Main.getInstance(), p, 60);
						}else {
							Bukkit.getScheduler().cancelTask(scheduler);
							scheduler = -1;
						}
					}else {
						new StartGame(Bukkit.getOnlinePlayers().size());
					}
				}
			}
		}, 0, 20);
		
		
	}
	

}
