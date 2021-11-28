package de.devofvictory.builder.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.devofvictory.builder.main.Main;

public class Listener_OnChat implements Listener{
	
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setMessage(Main.Prefix+"§d"+p.getName()+" §8» §a"+e.getMessage().replaceAll("§f<"+p.getName()+"§f>", ""));
	}

}
