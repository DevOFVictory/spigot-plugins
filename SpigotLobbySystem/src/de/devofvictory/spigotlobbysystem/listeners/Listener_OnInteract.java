package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnInteract implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getItem() != null) {
				if (e.getItem().getItemMeta().getDisplayName() != null) {
		if (e.getItem().getType() == Material.COMPASS && e.getItem().getItemMeta().getDisplayName() == "§a§lTeleporter") {
			Utils.openCompass(p);
			e.setCancelled(true);
		}
	}
	}
		}
	}
}
