package de.devofvictory.wargame.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearUtil {
	
	public ClearUtil(Player player){
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		}
	
	public static void removeInventoryItems(Player p, Material type, String name, int amount) {
	    for (ItemStack is : p.getInventory().getContents()) {
	        if (is != null && is.getType() == type) {
	            int newamount = is.getAmount() - amount;
	            if (newamount > 0) {
	                is.setAmount(newamount);
	                break;
	            } else {
	                p.getInventory().remove(is);
	                amount = -newamount;
	                if (amount == 0) break;
	            }
	        }
	    }
	}
	
	public static boolean hasEnough(Player p, Material type, String name, int amount) {
		for (ItemStack is : p.getInventory().getContents()) {
			try {
			if (is != null && is.getType() == type && is.getItemMeta().getDisplayName().equals(name) && is.getAmount() >= amount) {
				return true;	
			}
			}catch (NullPointerException ex) {
				
			}
		}
		
		return false;
	}

}
