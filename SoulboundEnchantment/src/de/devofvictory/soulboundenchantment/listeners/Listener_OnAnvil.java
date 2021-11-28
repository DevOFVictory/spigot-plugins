package de.devofvictory.soulboundenchantment.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.soulboundenchantment.main.SoulboundEnchantment;
import de.devofvictory.soulboundenchantment.utils.OtherUtils;
import de.devofvictory.soulboundenchantment.utils.RomanNumber;

public class Listener_OnAnvil implements Listener{
	
	@EventHandler
	public void onPrepareAnvil(PrepareAnvilEvent e) {
		Player p = (Player) e.getViewers().get(0);
		ItemStack book = e.getInventory().getItem(1);
		ItemStack input = e.getInventory().getItem(0);
		if (input != null && book != null && OtherUtils.isAllowedMaterial(input.getType()) && book.getType() == Material.ENCHANTED_BOOK) {
			if (input.hasItemMeta() && input.getItemMeta().hasEnchant(SoulboundEnchantment.ench)) return;
			
			EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
			if (bookMeta.hasStoredEnchant(SoulboundEnchantment.ench)) {
				ItemStack output = new ItemStack(input);
				
				int lvl = bookMeta.getStoredEnchantLevel(SoulboundEnchantment.ench);
				
				ItemMeta outputMeta = output.getItemMeta();
				outputMeta.addEnchant(SoulboundEnchantment.ench, lvl, true);
				
				List<String> lore = outputMeta.getLore() != null ? outputMeta.getLore() : new ArrayList<String>();
				lore.add("§7Soulbound "+RomanNumber.toRoman(lvl) + " ("+p.getName()+")");
				outputMeta.setLore(lore);
				
				output.setItemMeta(outputMeta);
				e.setResult(output);
			}
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.ANVIL) {
			AnvilInventory inv = (AnvilInventory) e.getClickedInventory();
			if (e.getSlot() == 2) {
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().hasEnchant(SoulboundEnchantment.ench)) {
						p.setItemOnCursor(e.getCurrentItem());
						inv.setItem(0, null);
						inv.setItem(1, null);
						inv.setItem(2, null);
						
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
						
					}
				}
			}
		}
	}

}
