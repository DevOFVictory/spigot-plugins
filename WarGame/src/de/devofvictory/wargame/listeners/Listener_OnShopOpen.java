package de.devofvictory.wargame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.devofvictory.wargame.inventorys.Inv_Trader;

public class Listener_OnShopOpen implements Listener{
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Villager) {

			e.setCancelled(true);
			Inv_Trader.callInv((Player) e.getPlayer());
			
		}
	}

}
