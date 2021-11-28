package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class Listener_OnItemSpawn implements Listener{
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent e) {
		if (e.getEntity().getItemStack().getType() != Material.SLIME_BALL)
			e.setCancelled(true);
	}

}
