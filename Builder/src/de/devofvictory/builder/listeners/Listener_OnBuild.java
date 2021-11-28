package de.devofvictory.builder.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listener_OnBuild implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (p.getWorld().getName().equalsIgnoreCase("world")) {
			if (!p.hasPermission("buildserver.lobby.build")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if (p.getWorld().getName().equalsIgnoreCase("world")) {
			if (!p.hasPermission("buildserver.lobby.build")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (p.getWorld().getName().equalsIgnoreCase("world")) {
			if (!p.hasPermission("buildserver.lobby.build")) {
				e.setCancelled(true);
			}
		}
	}
	
}
