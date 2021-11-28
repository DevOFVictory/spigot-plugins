package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import de.devofvictory.wargame.utils.SpectatorClass;

public class Listener_OnInventoryClose implements Listener{
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		if (SpectatorClass.specCamera.containsValue(p.getName())) {
			for (String playername : SpectatorClass.specCamera.keySet()) {
				if (SpectatorClass.specCamera.get(playername).equalsIgnoreCase(p.getName())) {
					Player spectator = Bukkit.getPlayer(playername);
					
					if (spectator != null) {
						if (e.getInventory().getType() == InventoryType.CHEST)
							spectator.closeInventory();
					}
				}
			}
		}
		
	}

}
