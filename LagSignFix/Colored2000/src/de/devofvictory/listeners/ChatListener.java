package de.devofvictory.listeners;

import org.bukkit.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener 
	implements Listener {

	   @EventHandler
		public void onChat(AsyncPlayerChatEvent e) {
		   

			String message = e.getMessage();
		if (e.getPlayer().hasPermission("colored2000.chat.use")) {
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			message.replace("&", "§").replace("§§", "&");
			}
		
		
	}
	


} 
