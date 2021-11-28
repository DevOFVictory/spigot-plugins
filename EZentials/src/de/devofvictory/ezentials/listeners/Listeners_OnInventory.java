package de.devofvictory.ezentials.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;


public class Listeners_OnInventory implements Listener{
	
	@EventHandler
	public void onHotbarSwitch(PlayerItemHeldEvent e) {
		HumanEntity p = e.getPlayer();
		
		if (p.hasMetadata("freezed")) {
			e.setCancelled(true);
		}
	}

}
