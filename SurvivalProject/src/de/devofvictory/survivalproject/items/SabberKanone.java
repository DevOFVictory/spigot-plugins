package de.devofvictory.survivalproject.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SabberKanone implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			try {
				
				if (e.getItem().getType() == Material.STICK && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSabberKanone")) {
					
					e.setCancelled(true);
					e.getPlayer().performCommand("spucken");
				}
				
			}catch (NullPointerException ex) {
				
			}
			
		}
	}

}
