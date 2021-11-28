package de.chilipro.chililobby.inventorys;

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

import de.chilipro.chililobby.main.Main;

public class Inv_Menu implements Listener{
	
	public static void callInventory(Player p) {
		Inventory menu = Bukkit.createInventory(null, 5*9, "§3§lMENU");
		
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)11);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§r");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack placeholder2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)3);
		ItemMeta placeholder2Meta = placeholder2.getItemMeta();
		placeholder2Meta.setDisplayName("§r");
		placeholder2.setItemMeta(placeholder2Meta);

		
		ItemStack citybuild = new ItemStack(Material.WORKBENCH);
		ItemMeta citybuildMeta = citybuild.getItemMeta();
		citybuildMeta.setDisplayName("§eCityBuild");
		citybuild.setItemMeta(citybuildMeta);
		
		ItemStack crystal = new ItemStack(Material.BEACON);
		ItemMeta crystalMeta = crystal.getItemMeta();
		crystalMeta.setDisplayName("§eCrystal");
		crystal.setItemMeta(crystalMeta);
		
		ItemStack wargame = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta wargameMeta = wargame.getItemMeta();
		wargameMeta.setDisplayName("§eWarGame");
		wargame.setItemMeta(wargameMeta);
		
		ItemStack spawn = new ItemStack(Material.NETHER_STAR);
		ItemMeta spawnMeta = spawn.getItemMeta();
		spawnMeta.setDisplayName("§eSpawn");
		spawn.setItemMeta(spawnMeta);
		
		ItemStack knockdown = new ItemStack(Material.FEATHER);
		ItemMeta knockdownMeta = knockdown.getItemMeta();
		knockdownMeta.setDisplayName("§eKnockDown");
		knockdown.setItemMeta(knockdownMeta);

		ItemStack pvp = new ItemStack(Material.STONE_SWORD);
		ItemMeta pvpMeta = pvp.getItemMeta();
		pvpMeta.setDisplayName("§ePvP");
		pvp.setItemMeta(pvpMeta);

		ItemStack commingsoon = new ItemStack(Material.GRASS);
		ItemMeta commingsoonMeta = commingsoon.getItemMeta();
		commingsoonMeta.setDisplayName("§eComming soon...");
		commingsoon.setItemMeta(commingsoonMeta);

		
		
		ItemStack rewards = new ItemStack(Material.CHEST);
		ItemMeta rewardsMeta = rewards.getItemMeta();
		rewardsMeta.setDisplayName("§eBelohnungen");
		rewards.setItemMeta(rewardsMeta);

		ItemStack credits = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta creditsMeta = credits.getItemMeta();
		creditsMeta.setDisplayName("§eCredits");
		credits.setItemMeta(creditsMeta);

		ItemStack vipmenu = new ItemStack(Material.DIAMOND);
		ItemMeta vipmenuMeta = vipmenu.getItemMeta();
		vipmenuMeta.setDisplayName("§eVIP-Menu");
		vipmenu.setItemMeta(vipmenuMeta);

		ItemStack rules = new ItemStack(Material.SLIME_BALL);
		ItemMeta rulesMeta = rules.getItemMeta();
		rulesMeta.setDisplayName("§eRegeln");
		rules.setItemMeta(rulesMeta);

		ItemStack contact = new ItemStack(Material.PAPER);
		ItemMeta contactMeta = contact.getItemMeta();
		contactMeta.setDisplayName("§eKontakt");
		contact.setItemMeta(contactMeta);
		
		for (int i = 0; i<5*9; i++) {
			menu.setItem(i, placeholder);
		}
		menu.setItem(2, citybuild);
		menu.setItem(4, crystal);
		menu.setItem(7, placeholder2);
		menu.setItem(8, rewards);
		menu.setItem(16, placeholder2);
		menu.setItem(17, credits);
		menu.setItem(19, wargame);
		menu.setItem(21, spawn);
		menu.setItem(23, knockdown);
		menu.setItem(25, placeholder2);
		menu.setItem(26, vipmenu);
		menu.setItem(34, placeholder2);
		menu.setItem(35, rules);
		menu.setItem(38, pvp);
		menu.setItem(40, commingsoon);
		menu.setItem(43, placeholder2);
		menu.setItem(44, contact);
		
		p.openInventory(menu);
	}
	
	@EventHandler
	public void onMeuClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (!Main.allowBuild.contains(p.getName())) 
			e.setCancelled(true);
		
		try {
			String invname = e.getClickedInventory().getName();
			String name = e.getCurrentItem().getItemMeta().getDisplayName();
			
			if (invname.equalsIgnoreCase("§3§lMENU")) {
				e.setCancelled(true);
				
				if (name.equalsIgnoreCase("§eCityBuild")) {
					
					// tp citybuild
					p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
					
					
				}else if (name.equalsIgnoreCase("§eCrystal")) {
					
					// tp crystal
					p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
					
					
				}else if (name.equalsIgnoreCase("§eWarGame")) {
					
				
					// tp wargame
					p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
					
					
				}else if (name.equalsIgnoreCase("§eSpawn")) {
					p.closeInventory();
					
					if (Main.spawn != null) {
						p.teleport(Main.spawn);
					
						p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
					}else {
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
						p.sendMessage(Main.Prefix+"§cWarp ist noch nicht gesetzt!");
					}
					
				}else if (name.equalsIgnoreCase("§eKnockDown")) {
					
				
					// tp knockdown
					p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
					
					
				}else if (name.equalsIgnoreCase("§ePvP")) {
					
					// tp pvp
					p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1, 1);
				
					
				}
				
			}
			
		}catch (NullPointerException ex) {
		}
		
		
	}


	


}
