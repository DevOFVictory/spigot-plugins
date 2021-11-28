package de.devofvictory.pvpzone.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.pvpzone.utils.Variables;

public class Listener_OnJoin implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		p.getInventory().clear();
		p.getInventory().setItem(1, new ItemStack(Material.DIAMOND_SWORD));
		p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
		p.getInventory().setItem(8, new ItemStack(Material.BOOK));
		p.getInventory().setItem(7, new ItemStack(Material.WATCH));
		p.getInventory().setItem(6, new ItemStack(Material.NAME_TAG));
		
		if (Variables.spawn != null) {
			p.teleport(Variables.spawn);
			p.sendTitle("§2Welcome", "§a"+p.getName());
		}
	}

}
