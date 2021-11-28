package de.devofvictory.soulboundenchantment.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.soulboundenchantment.main.SoulboundEnchantment;

public class OtherUtils {
	
	public static boolean isAllowedMaterial(Material type) {
		String name = type.toString();
		if (name.contains("_SWORD") || name.contains("_AXE") || 
				name.contains("_PICKAXE") || name.contains("_HOE") || name.contains("_SPADE") || name.contains("_SHOVEL") ||
				name.contains("_CHESTPLATE") || name.contains("_LEGGINGS") || name.contains("_BOOTS") || name.contains("_HELMET")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public static String getNameFromLore(String lore) {
		// Soulbound VI (DevOFVictory)
		
		if (lore.startsWith("§7Soulbound ")) {
			String[] splitted = lore.split(" ");
			return splitted[2].replace("(", "").replace(")", "");
		}else {
			return null;
		}
	}
	
	public static boolean canUse(Player p, ItemStack is) {
		if (is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(SoulboundEnchantment.ench)) {
			
			if (is.getItemMeta().hasLore()) {
				for (String s : is.getItemMeta().getLore()) {
					if (getNameFromLore(s) != null) {
						if (getNameFromLore(s).equals(p.getName())) {
							return true;
						}else {
							return false;
						}
					}
				}
			}else {
				return false;
			}
			
		}else {
			return true;
		}
		return true;
	}

}
