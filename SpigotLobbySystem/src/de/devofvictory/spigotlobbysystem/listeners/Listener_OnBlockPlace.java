package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnBlockPlace implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (Utils.isLobby(p)) {
			if (!Utils.allowBuild.contains(p)) {
				e.setCancelled(true);
			}
		}
	}

}
