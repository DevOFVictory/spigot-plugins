package de.devofvictory.survivalproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Listener_OnDebug implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("*debug")) {
			e.setCancelled(true);
			
			//Bukkit.broadcastMessage(""+TeleporterUtils.getTeleporter(new Location(Bukkit.getWorld("flat"), -72, 4, 224)).getCounterpart().getLocation());
			
			// -72 4 224
		}
	}

}
