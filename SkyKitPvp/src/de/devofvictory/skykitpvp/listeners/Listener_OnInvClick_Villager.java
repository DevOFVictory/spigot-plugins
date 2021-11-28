package de.devofvictory.skykitpvp.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnInvClick_Villager implements Listener{
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
		
		if (e.getView().getTitle() != null) {
			if (e.getView().getTitle().equals(Variables.villagerName)) {
				e.setCancelled(true);
				
				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().hasItemMeta()) {
						if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
							
							String name = e.getCurrentItem().getItemMeta().getDisplayName();
							
							if (KitManager.getKitByDispName(name) != null) {
								LevelBarUtil.abortLevelBar(p);
								Kit kit = KitManager.getKitByDispName(name);
								
								
								if (KitManager.hasKit(p, kit)) {
									
									if (KitManager.hasKitSelected(p) && KitManager.getSelectedKit(p).equals(kit)) {
										
										callLevelsInventory(kit, p, false);
										p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
										
									}else {
										KitManager.setKit(p, kit);
										p.sendMessage(Main.Prefix+"§aDu spielst nun mit "+kit.getDisplayName());
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
										Listener_OnEntiyInteract.callKitInventory(p);
									}
										
										
										
									}else {
										
										callBuyInventory(p, kit);
										
										p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
								
								}
							}
							
							
						}
					}
				}
			}else if (e.getView().getTitle().startsWith("§6§l") || e.getView().getTitle().startsWith("§r§6§l")) {
				
				
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
					
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
				
					Kit kit = KitManager.getKitByUniqueName(e.getView().getTitle().replace("§6§l", "").toLowerCase());
					
					if (kit == null) {
						kit = KitManager.getKitByUniqueName(e.getView().getTitle().replace("§r§6§l", "").toLowerCase());
					}
					
					if (kit != null) {
						if (e.getView().getType() == InventoryType.BREWING) {
							
							if (name.equals("§a§lJa!")) {
								
								if (!KitManager.hasKit(p, kit)) {
								
									if (EcoManager.hasEnough(p, kit.getPrice())) {
										EcoManager.takeCoins(p, kit.getPrice());
										KitManager.addKit(p, kit);
										KitManager.setKit(p, kit);
										Listener_OnEntiyInteract.callKitInventory(p);
										
										p.sendMessage(Main.Prefix+"§aDu hast dir erfolgreich das "+kit.getDisplayName()+" §aKit für §2"+kit.getPrice()+" §aCoins gekauft!");
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
									}else {
										p.sendMessage(Main.Prefix+"§cDu hast nicht genügend Coins! Dir fehlen noch §6"+(kit.getPrice()-EcoManager.getCoins(p))+" §cCoins!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
									}
								}else {
									p.sendMessage(Main.Prefix+"§cDu hast dieses Kit bereits!!");
									p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
								}
								
							}else if (name.equals("§c§lNein!")) {
								p.closeInventory();
								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
								
							}else if (name.equals("§4§lZurück")) {
								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
								Listener_OnEntiyInteract.callKitInventory(p);
							}else if (name.equals("§e§lLevel Informationen")) {
								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
								callLevelsInventory(kit, p, true);
							}
							
							
						}else if (e.getView().getType() == InventoryType.CHEST) {
							
							if (name.startsWith("§6§lLevel ")) {
								int levelInt = Integer.parseInt(name.split(" ")[1]);
								KitLevel kitLevel = kit.getKitLevelForLevel(levelInt);
								
								if (e.getAction() != InventoryAction.CLONE_STACK && e.getAction() != InventoryAction.NOTHING) {
									
									if (e.getView().getTitle().startsWith("§6§l")) {
								
									if (KitManager.getKitLevel(p, kit) < levelInt) {
										
										if (KitManager.getKitLevel(p, kit) + 1 != levelInt) {
											p.sendMessage(Main.Prefix+"§cDu musst die verherigen Level zuerst abschließen!!");
											p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
											return;
										}
										
										if (KitManager.getKitKills(p, kit) >= kitLevel.getMinKillsForUpgrade()) {
											
											KitManager.levelUp(p, kit);
											Listener_OnEntiyInteract.callKitInventory(p);
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
											p.sendMessage(Main.Prefix+"§aDas Kit "+kit.getDisplayName()+" §awurde erfolgreich auf Level §2"+levelInt+" §ageupgraded!");
											
										}else {
											p.sendMessage(Main.Prefix+"§cDu brauchst noch §6"+(10*KitManager.getKitLevel(p, kit)-KitManager.getKitKills(p, kit))+" §cKills mit diesem Kit, um es aufzuleveln!");
											p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										}
									}else {
										p.sendMessage(Main.Prefix+"§cDieses Level hast du bereits erreicht!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
									}
									}
								
								}else {
									
									
									ItemStack placeHolder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
									ItemMeta meta = placeHolder.getItemMeta();
									meta.setDisplayName("§r");
									placeHolder.setItemMeta(meta);
									
									Inventory kitInv = Variables.inventories.get(kitLevel.getInventoryName());
									Inventory inv = Bukkit.createInventory(null, 5*9, "§r§6§l"+(kit.getUniqueName().substring(0, 1).toUpperCase() + kit.getUniqueName().substring(1))+ " Lv. "+levelInt);
									inv.setContents(kitInv.getContents());
									p.openInventory(inv);
								}
								
							}
						}
					}
					
				}
			}
		}
		}
	}

	private void callBuyInventory(Player p, Kit kit) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.BREWING, "§6§l"+(kit.getUniqueName().substring(0, 1).toUpperCase() + kit.getUniqueName().substring(1)));
		
		ItemStack question = new ItemStack(kit.getDisplayType());
		ItemMeta questionMeta = question.getItemMeta();
		questionMeta.setDisplayName("§f§lMöchtest du das "+kit.getDisplayName()+" §f§lKit kaufen?");
		List<String> lore = new ArrayList<String>();
		
		lore.add("§d§lPreis: "+kit.getPrice()+" Coins");
		questionMeta.setLore(lore);
		question.setItemMeta(questionMeta);
		inv.setItem(3, question);
		
		
		ItemStack yes = new ItemStack(Material.LIME_DYE);
		ItemMeta yesMeta = yes.getItemMeta();
		yesMeta.setDisplayName("§a§lJa!");
		yes.setItemMeta(yesMeta);
		inv.setItem(0, yes);
		
		
		ItemStack no = new ItemStack(Material.RED_DYE);
		ItemMeta noMeta = no.getItemMeta();
		noMeta.setDisplayName("§c§lNein!");
		no.setItemMeta(noMeta);
		inv.setItem(2, no);
		
		ItemStack placeholder = OtherUtils.getSkullFromUrl("http://textures.minecraft.net/texture/d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f");
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§e§lLevel Informationen");
		placeholder.setItemMeta(placeholderMeta);
		inv.setItem(1, placeholder);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName("§4§lZurück");
		back.setItemMeta(backMeta);
		inv.setItem(4, back);
		
		
		
		
		p.openInventory(inv);
		
	}

	private void callLevelsInventory(Kit kit, Player p, boolean spoiler) {
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		
		Inventory inv = null;
		if (!spoiler) {
			inv = Bukkit.createInventory(null, 9, "§6§l"+(kit.getUniqueName().substring(0, 1).toUpperCase() + kit.getUniqueName().substring(1)));
		}else {
			inv = Bukkit.createInventory(null, 9, "§r§6§l"+(kit.getUniqueName().substring(0, 1).toUpperCase() + kit.getUniqueName().substring(1)));
		}
		
		for (KitLevel level : kit.getKitLevels()) {
			ItemStack is = new ItemStack(kit.getDisplayType());
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName("§6§lLevel "+level.getLevelInt());
			List<String> lore = new ArrayList<String>();
			if (kitLevelInt >= level.getLevelInt()) {
				lore.add("§a§lIn Besitz");
			}else {
				lore.add("§c§lNicht in Besitz");
			}
			lore.add("§8»");
			lore.add("§d§lKills: "+KitManager.getKitKills(p, kit)+"/"+level.getMinKillsForUpgrade());
			lore.add("§8»");
			
			for (String key : kit.getKitLevelForLevel(level.getLevelInt()).getVariables().keySet()) {
				String value = kit.getKitLevelForLevel(level.getLevelInt()).getVariables().get(key);
				
				lore.add("§3"+key.substring(0, 1).toUpperCase() + key.substring(1) + "§7: §b"+value);
			}
			lore.add("§3Regeneration nach Kill: §b"+level.getRegenerationTime() + " Sekunden");
			lore.add("§3Inventar: §bMittlere Maustaste");
			
			meta.setLore(lore);
			is.setItemMeta(meta);
			
			inv.addItem(is);
		}
		p.openInventory(inv);
		
	}
	
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
//		Player p = (Player) e.getPlayer();
//		if (e.getView().getType() == InventoryType.CHEST) {
//			if (e.getView().getTitle() != null && e.getView().getTitle().startsWith("§6§l")) {
//				
//				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
//					
//					@Override
//					public void run() {
//						Listener_OnEntiyInteract.callKitInventory(p);
//						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
//						
//					}
//				}, 1);
//				
//				
//			}else if (e.getView().getTitle() != null && e.getView().getTitle().startsWith("§r§6§l")) {
//				
//				
//				String uniqueName = e.getView().getTitle().replace("§r§6§l", "").toLowerCase();
//				
//				Kit kit = KitManager.getKitByUniqueName(uniqueName);
//				
//				if (kit != null) {
//					
//					
//					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
//						
//						@Override
//						public void run() {
//							callBuyInventory(p, kit);
//							p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
//							
//						}
//					}, 1);
//					
//					
//				}
//				
//			}
//		}else if (e.getView().getType() == InventoryType.BREWING) {
//			if (e.getView().getTitle() != null && e.getView().getTitle().startsWith("§6§l")) {
//				
//				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
//					
//					@Override
//					public void run() {
//						Listener_OnEntiyInteract.callKitInventory(p);
//						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
//						
//					}
//				}, 1);
//				
//				
//			}
//		}
	}

}
