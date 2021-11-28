package de.devofvictory.survivalproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.devofvictory.survivalproject.items.Zauberstab;
import de.devofvictory.survivalproject.main.Main;

public class Listener_OnJoinQuit implements Listener{
	
	 @EventHandler
	 public void onJoin(PlayerJoinEvent e) {
		 e.setJoinMessage(Main.Prefix+"§a"+e.getPlayer().getName()+" hat den Projektserver betreten!");
		 
		 if (!Zauberstab.lastUsed.containsKey(e.getPlayer())) {
			 Zauberstab.lastUsed.put(e.getPlayer(), System.currentTimeMillis());
		 }
	 }
	 
	 @EventHandler
	 public void onQuit(PlayerQuitEvent e) {
		 e.setQuitMessage(Main.Prefix+"§c"+e.getPlayer().getName()+" hat den Projektserver verlassen!");
		 
	 }

}
