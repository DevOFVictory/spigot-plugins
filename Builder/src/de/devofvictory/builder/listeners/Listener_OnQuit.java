package de.devofvictory.builder.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.devofvictory.builder.main.Main;

public class Listener_OnQuit implements Listener{
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(Main.Prefix+"§c"+p.getName()+" hat den Bauserver verlassen!");
	}

}
