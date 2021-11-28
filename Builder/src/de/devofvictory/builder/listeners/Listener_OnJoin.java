package de.devofvictory.builder.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.devofvictory.builder.main.Main;

public class Listener_OnJoin implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(Main.Prefix+"§a"+p.getName()+" hat den Bauserver betreten!");
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				p.setGameMode(GameMode.CREATIVE);
				
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
				p.sendTitle("§aWillkommen auf dem Builderver,", "§b"+p.getName());
				
				p.getInventory().clear();
				
			}
		},20);
		
	}

}
