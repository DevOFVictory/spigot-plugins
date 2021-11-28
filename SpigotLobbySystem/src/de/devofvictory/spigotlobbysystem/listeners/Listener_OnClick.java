package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnClick implements Listener{
	
	@EventHandler
	public void onDrop(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if (Utils.isLobby(p)) {
			if (!Utils.allowBuild.contains(p)) {
				e.setCancelled(true);
			}else {
				if (e.getSlotType() == SlotType.ARMOR) {
					e.setCancelled(true);
				}
			}
		}
	}

}
