package de.devofvictory.survivalproject.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Listener_OnChat implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		e.setFormat("§2[§a"+p.getLevel()+"§2] §b"+p.getName()+" §8» §7"+e.getMessage().replace('&', '§'));;
	}

}
