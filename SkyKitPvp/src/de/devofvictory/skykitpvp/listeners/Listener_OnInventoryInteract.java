package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnInventoryInteract implements Listener{
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (!Command_Build.buildMode.contains((Player)e.getWhoClicked())) {
			if (e.getView().getType() != InventoryType.CRAFTING) {
				e.setCancelled(true);
		}
		
		}
	}

}
