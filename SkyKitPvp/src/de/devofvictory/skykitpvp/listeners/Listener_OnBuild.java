package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnBuild implements Listener{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!Command_Build.buildMode.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!Command_Build.buildMode.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

}
