package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnCraftItemEvent implements Listener{
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		if (e.getWhoClicked() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				
				if (!Command_Build.buildMode.contains(p)) {
					e.setCancelled(true);
				}
			}
		}
	}

}
