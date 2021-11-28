package de.chilipro.chililobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnInvClick implements Listener{
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			
			try {
				Player p = (Player) e.getWhoClicked();
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Spawn")) {
					p.performCommand("spawn");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("KnockDown")) {
					Main.connectToServer(p, "knock");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("WarGame")) {
					Main.connectToServer(p, "WarGame");
				}else if(!e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("")) {
					p.closeInventory();
					p.sendMessage("§f[§4ChiliPro.net§f] §cComing Soon...");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			
		}
	}

}
