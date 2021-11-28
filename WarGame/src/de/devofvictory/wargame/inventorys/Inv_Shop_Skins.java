package de.devofvictory.wargame.inventorys;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.SpindManager;
import de.devofvictory.wargame.utils.TraderShop;

public class Inv_Shop_Skins implements Listener{
	
	public static void callInv(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 18, "§e§lHändler §f- §6Skins");
			
		for (String names : TraderShop.skins.keySet()) {
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1,(byte)3);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			
			int price = TraderShop.skins.get(names);
			
//			meta.setOwner(names);
			
			if (Main.getMinigame().getCoins(p) >= price) {
				meta.setDisplayName("§a"+names);
			}else {
				meta.setDisplayName("§c"+names);
			}
			
			
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§dPreis: "+price);
			meta.setLore(lore);
			skull.setItemMeta(meta);
			
			inv.addItem(skull);
		}
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName("§4<- Zurück");
		back.setItemMeta(backMeta);
		inv.setItem(9, back);
		
		p.openInventory(inv);
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		try {
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4<- Zurück")) {
				Inv_Trader.callInv(p);
				p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
				return;
			}
			
			if (e.getClickedInventory().getName().equalsIgnoreCase("§e§lHändler §f- §6Skins")) {
				
				String skinName = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§a", "").replaceAll("§c", "");
				if (Main.getMinigame().getCoins(p) >= TraderShop.skins.get(skinName)) {
					
					if (!SpindManager.getSkins(p.getUniqueId()).contains(skinName)) {
					
					SpindManager.addSkin(p.getUniqueId(), skinName);
					
					Main.getMinigame().setCoins(p, Main.getMinigame().getCoins(p)-TraderShop.skins.get(skinName));
					
					p.sendMessage(Main.Prefix+"§aDu hast den Skin von §2"+skinName+" §aerfolgreich gekauft!");
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
					
					}else {
						p.sendMessage(Main.Prefix+"§cDu hast diesen Skin bereits!");
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 5, 5);
					}
				}else {
					p.sendMessage(Main.Prefix+"§cDu hast nicht genügend WarCoins!");
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 5, 5);
				}
				
				
			}
		}catch (NullPointerException ex) {
			
		} 
			
		
		
		
	}

}
