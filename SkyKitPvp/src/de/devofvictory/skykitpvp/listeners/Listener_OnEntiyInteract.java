package de.devofvictory.skykitpvp.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.skykitpvp.commands.Command_Build;
import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.superpowers.BAT_DISGUISE;
import de.devofvictory.skykitpvp.utils.ItemFrameManager;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnEntiyInteract implements Listener{ 
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		
		if (e.getRightClicked().getType() == EntityType.WANDERING_TRADER) {
			
			try {
			
			if (e.getRightClicked().getCustomName().equals(Variables.villagerName)) {
				e.setCancelled(true);
				if (!BAT_DISGUISE.bats.containsKey(e.getPlayer()))
					callKitInventory(p);
				
			}
			
			}catch (NullPointerException ex) {
				ex.printStackTrace();
			}
			
		}else if (e.getRightClicked().getType() == EntityType.WITCH) {
			
			try {
				
				if (e.getRightClicked().getCustomName().equals(Variables.witchName)) {
					e.setCancelled(true);
					
					
					callItemsInventory(p);
					
				}
				
				}catch (NullPointerException ex) {
				}
			
			
		}else if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			
			if (!Command_Build.buildMode.contains(p)) {
				e.setCancelled(true);
			}
			
			ItemFrame frame = (ItemFrame) e.getRightClicked();
			
			ItemStack is = frame.getItem();
			if (is.getType() == Material.GOLD_INGOT) {
				if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
					if (is.getItemMeta().getDisplayName().equals("§6§lKick mich!")) {
						frame.setItem(new ItemStack(Material.AIR));
						ItemFrameManager.setGold(ItemFrameManager.getFreeItemFrame(frame));					
						
						p.sendTitle("", "§6§l+"+Variables.coinsPerFrame);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
						EcoManager.addCoins(p, Variables.coinsPerFrame);
						
					}
				}
			}
		}
	}
	
	public static void callItemsInventory(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 9, Variables.witchName);
		
		ItemStack placeHolder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = placeHolder.getItemMeta();
		meta.setDisplayName("§r");
		placeHolder.setItemMeta(meta);
		
		for (int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, placeHolder);
		}
		
		ItemStack cointracker = new ItemStack(Material.COMPASS);
		ItemMeta cointrackerMeta = cointracker.getItemMeta();
		cointrackerMeta.setDisplayName("§4§lCointracker");
		cointracker.setItemMeta(cointrackerMeta);
		
		ItemStack granate = new ItemStack(Material.FIREWORK_STAR);
		ItemMeta granateMeta = granate.getItemMeta();
		granateMeta.setDisplayName("§4§lGranate");
		granate.setItemMeta(granateMeta);
		
		ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
		ItemMeta enderpearlMeta = enderpearl.getItemMeta();
		enderpearlMeta.setDisplayName("§4§lEnderperle");
		enderpearl.setItemMeta(enderpearlMeta);
		
		ItemStack slimeball = new ItemStack(Material.SLIME_BALL);
		ItemMeta slimeBallMeta = slimeball.getItemMeta();
		slimeBallMeta.setDisplayName("§4§lSchleimball");
		slimeball.setItemMeta(slimeBallMeta);
		
		
		inv.setItem(1, cointracker);
		inv.setItem(3, granate);
		inv.setItem(5, enderpearl);
		inv.setItem(7, slimeball);
		
		p.openInventory(inv);
	}
	
	public static void callKitInventory(Player p) {
		
		
		Inventory inv = Bukkit.createInventory(null, 4*9, Variables.villagerName);
		ItemStack placeHolder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = placeHolder.getItemMeta();
		meta.setDisplayName("§r");
		placeHolder.setItemMeta(meta);
		
		for (int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, placeHolder);
		}
		
		int counter = 0;
		
		for (Kit kit : KitManager.getRegisteredKits()) {
			Material type = kit.getDisplayType();
			List<String> lore = new ArrayList<String>();
			lore.add("§e"+kit.getSuperPower().getName());
			lore.add("§8»");
			lore.add("§6Preis: "+kit.getPrice()+" Coins");
			
			int minKills = 0;
			
			if (KitManager.hasKit(p, kit)) {
				lore.add("§a§lGekauft");
				if (KitManager.getKitLevel(p, kit) != 5) {
					minKills = kit.getKitLevelForLevel(KitManager.getKitLevel(p, kit)+1).getMinKillsForUpgrade();
				}else {
					minKills = kit.getKitLevelForLevel(KitManager.getKitLevel(p, kit)).getMinKillsForUpgrade();
				}
				
			}else {
				lore.add("§c§lNicht Gekauft");
				minKills = kit.getKitLevelForLevel(2).getMinKillsForUpgrade();
			}
			
			
			
			lore.add("§8»");
			lore.add("§7Level: "+KitManager.getKitLevel(p, kit)+"/"+kit.getMaxLevel());
			lore.add("§7Kills für Upgrade: "+KitManager.getKitKills(p, kit)+"/"+minKills);
			lore.add("§8»");
			if (KitManager.hasKitSelected(p)) {
				if (KitManager.getSelectedKit(p).equals(kit)) {
					lore.add("§d§lAusgewählt");
				}
			}
			
			ItemStack is = new ItemStack(type);
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(kit.getDisplayName());
			isMeta.setLore(lore);
			is.setItemMeta(isMeta);
			is.setAmount(KitManager.getKitLevel(p, kit) == 0 ? 1 : KitManager.getKitLevel(p, kit));
			inv.setItem(counter, is);
			counter+=2;
		}
		p.openInventory(inv);
	}


}
