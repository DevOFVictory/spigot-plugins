package de.devofvictory.wargame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class Listener_OnFlyKick implements Listener{
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		if (e.getReason().equals("Flying is not enabled on this server")) {
			e.setCancelled(true);
		}
	}

}
