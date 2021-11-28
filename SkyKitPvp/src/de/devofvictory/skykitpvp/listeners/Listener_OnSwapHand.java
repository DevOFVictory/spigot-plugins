package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnSwapHand implements Listener{
	
	@EventHandler
	public void onSwapItem(PlayerSwapHandItemsEvent e) {
		if (!Command_Build.buildMode.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

}
