package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnDrop implements Listener{
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		if (!Command_Build.buildMode.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

}
