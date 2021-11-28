package de.devofvictory.soulboundenchantment.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.soulboundenchantment.main.SoulboundEnchantment;

public class Listener_OnDisenchant implements Listener{
	
	@EventHandler
	public void onDisenchant(InventoryClickEvent e) {
		if (e.getInventory().getType() == InventoryType.GRINDSTONE) {
			
			
			ItemStack item1 = e.getInventory().getItem(0);
			ItemStack item2 = e.getInventory().getItem(1);
			ItemStack item3 = e.getCursor();
			
			if ((item1 != null && item1.hasItemMeta() && item1.getItemMeta().hasEnchant(SoulboundEnchantment.ench) || 
					(item2 != null && item2.hasItemMeta() && item2.getItemMeta().hasEnchant(SoulboundEnchantment.ench) ||
					(item3 != null && item3.hasItemMeta() && item3.getItemMeta().hasEnchant(SoulboundEnchantment.ench))))) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(SoulboundEnchantment.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						e.getInventory().setItem(2, null);
					}
				}, 1);
			
			}
		}
	}
	
	@EventHandler
	public void onCombineCrafting(PrepareItemCraftEvent e) {
		for (ItemStack is : e.getInventory().getContents()) {
			if (is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(SoulboundEnchantment.ench)) {
				e.getInventory().setResult(null);
			}
		}
	}
	
	@EventHandler
	public void onPrepareAnvil(PrepareAnvilEvent e) {
		ItemStack item1 = e.getInventory().getItem(0);
		ItemStack item2 = e.getInventory().getItem(1);
		
		if ((item1 != null && item1.hasItemMeta() && item1.getItemMeta().hasEnchant(SoulboundEnchantment.ench) || 
				(item2 != null && item2.hasItemMeta() && item2.getItemMeta().hasEnchant(SoulboundEnchantment.ench)))) {
				e.setResult(new ItemStack(Material.AIR));
				e.getInventory().setItem(2, new ItemStack(Material.AIR));
		}
	}

}
