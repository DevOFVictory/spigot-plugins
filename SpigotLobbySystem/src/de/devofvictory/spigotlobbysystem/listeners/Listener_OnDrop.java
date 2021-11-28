package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnDrop implements Listener{
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (Utils.isLobby(p)) {
			if (!Utils.allowBuild.contains(p)) {
				e.setCancelled(true);
			}
		}
	}

}
