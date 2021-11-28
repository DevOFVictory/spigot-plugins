package de.devofvictory.soulboundenchantment.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.devofvictory.soulboundenchantment.main.SoulboundEnchantment;

public class Listener_OnInvClick implements Listener{
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getView().getTitle().equals(SoulboundEnchantment.tradePlusTitle)) {
			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasEnchant(SoulboundEnchantment.ench)) {
				e.setCancelled(true);
				p.sendMessage("§cYou cant trade souldbound items!");
			}
		}
	}
	
	

}
