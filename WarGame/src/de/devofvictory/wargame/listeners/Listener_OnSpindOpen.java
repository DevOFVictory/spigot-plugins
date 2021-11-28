package de.devofvictory.wargame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.wargame.inventorys.INV_PlayerSpind;

public class Listener_OnSpindOpen implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		try {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK ||e.getAction() == Action.RIGHT_CLICK_AIR) {
				if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lSpind")) {
					INV_PlayerSpind.callInv(e.getPlayer());
					e.setCancelled(true);
				}
			}
		}catch (NullPointerException ex) {
			
		}
	}

}
