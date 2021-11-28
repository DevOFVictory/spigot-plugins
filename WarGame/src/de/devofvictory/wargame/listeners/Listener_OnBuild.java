package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.items.WurfMine;
import de.devofvictory.wargame.main.Main;

public class Listener_OnBuild implements Listener{
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		
		
		if (!Main.allowDamage)
			e.setCancelled(true);
		
		if (e.getBlock().getType() == Material.STONE_PLATE)
			WurfMine.minenLocations.put(e.getBlock().getLocation(), e.getPlayer().getName());
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		
		
				
		if (!Main.allowDamage)
			e.setCancelled(true);
		if (e.getBlock().getType() == Material.EMERALD_BLOCK && !p.isOp()) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cDu darfst keine Loot-Chests zerstören!");
			
		}
		
		if (Main.isMatchRunning) {
		if (e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2) {
			
			ItemStack apple = new ItemStack(Material.APPLE);
			ItemMeta appleMeta = apple.getItemMeta();
			appleMeta.setDisplayName("§4§lApfel");
			apple.setItemMeta(appleMeta);
			
			Bukkit.getWorld("map").dropItemNaturally(e.getBlock().getLocation(), apple);
		}
			
		}
	}
	
		

}
