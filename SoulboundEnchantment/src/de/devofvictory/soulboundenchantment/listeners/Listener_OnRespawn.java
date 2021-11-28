package de.devofvictory.soulboundenchantment.listeners;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Listener_OnRespawn implements Listener{
	
	public static HashMap<Player, List<ItemStack>> playerItems = new HashMap<Player, List<ItemStack>>();
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (playerItems.containsKey(p) && !playerItems.get(p).isEmpty()) {
			for (ItemStack is : playerItems.get(p)) {
				p.getInventory().addItem(is);
			}
			playerItems.remove(p);
		}
	}

}
