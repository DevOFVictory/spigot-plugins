package de.devofvictory.skykitpvp.invs;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.Variables;

public class DalyLootChest implements Listener{
	
	static HashMap<Player, Integer> alreadyOpened = new HashMap<Player, Integer>();
	static HashMap<Player, Integer> counter = new HashMap<Player, Integer>();
	
	public static HashMap<Player, Long> lastOpen = new HashMap<Player, Long>();
	
	public static void call(Player p) {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§5§lTägliche Belohnung");
		
		ItemStack chest = new ItemStack(Material.ENDER_CHEST);
		ItemMeta chestMeta = chest.getItemMeta();
		
		chestMeta.setDisplayName("§7Klicke zum Auswählen");
		
		chest.setItemMeta(chestMeta);
		
		for (int i = 0; i<6*9; i++) {
			inv.setItem(i, chest);
		}
		
		p.openInventory(inv);
		alreadyOpened.put(p, 0);
	}
	
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory() != null) {
			if (e.getView().getTitle().equals("§5§lTägliche Belohnung")) {
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§7Klicke zum Auswählen")) {
						
						if (alreadyOpened.get(e.getWhoClicked()) < 6) {
						
							alreadyOpened.put(p, alreadyOpened.get(p)+1);
							
							int coins = getRandomNumberInRange(Variables.dailyloot_from, Variables.dailyloot_to);
							
							e.getCurrentItem().setType(Material.GOLD_INGOT);
							ItemMeta meta = e.getCurrentItem().getItemMeta();
							meta.setDisplayName("§e§l"+coins+" Coins");
							e.getCurrentItem().setItemMeta(meta);
							
							EcoManager.addCoins(p, coins);
							
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
							
							if (alreadyOpened.get(e.getWhoClicked()) == 6) {
								
								alreadyOpened.remove(p);
								
								Inventory inv = Bukkit.createInventory(null, 6*9, "§r§5§lTägliche Belohnung");
								inv.setContents(e.getInventory().getContents());
								p.openInventory(inv);
								
								counter.put(p, 0);
								
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
								
								
								new BukkitRunnable() {
									
									@Override
									public void run() {
										
										if (counter.get(p) <= 53) {
											
											if (inv.getItem(counter.get(p)).getType() == Material.ENDER_CHEST) {
												ItemStack fail = new ItemStack(Material.CHARCOAL);
												ItemMeta failMeta = fail.getItemMeta();
												failMeta.setDisplayName("§8§l"+getRandomNumberInRange(Variables.dailyloot_from, Variables.dailyloot_to)+" Coins");
												fail.setItemMeta(failMeta);
												inv.setItem(counter.get(p), fail);
												
												
												p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
											}
											
											counter.put(p, counter.get(p)+1);
											
											
										}else {
											cancel();
											counter.put(p, 0);
										}
										
										
									}
								}.runTaskTimer(Main.getInstance(), 2, 2);

							}
						
							
						}
						
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getView().getTitle().equals("§5§lTägliche Belohnung")) {
			
			if (alreadyOpened.containsKey(p)) {
			
			for (int i = 0; i<6-alreadyOpened.get(p); i++) {
				EcoManager.addCoins(p, getRandomNumberInRange(Variables.dailyloot_from, Variables.dailyloot_to));
			}
			
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			
			
			alreadyOpened.remove(p);
			
			}
		
		}else if (e.getView().getTitle().equals("§r§5§lTägliche Belohnung")) {
			
			counter.put(p, 54);
			
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("Maximum muss hoeher sein als Minimum!");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
