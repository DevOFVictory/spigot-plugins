package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

@SuppressWarnings("deprecation")
public class Listener_OnItemPickUp implements Listener{
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (!Command_Build.buildMode.contains(e.getPlayer())) {
			e.setCancelled(true);
			e.getItem().remove();
			
		}
			
	}

}
