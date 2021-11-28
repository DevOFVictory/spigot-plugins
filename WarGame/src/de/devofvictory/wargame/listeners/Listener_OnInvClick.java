package de.devofvictory.wargame.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;


public class Listener_OnInvClick implements Listener{
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		

		
		if (!Main.isGameRunning) {
			e.setCancelled(true);
		}
		
		if (e.getInventory().getTitle().equalsIgnoreCase("§3§lWar§b§lItems")) {
			if (p.hasPermission("waritems.waritems")) {
				if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().getType() != Material.AIR) {
				e.setCancelled(true);
				if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					
				
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lLaubBläser")) {
						
						ItemStack laubblaeser = new ItemStack(Material.GOLD_HOE);
						ItemMeta laubblaeserMeta = laubblaeser.getItemMeta();
						laubblaeserMeta.setDisplayName("§6§lLaubBläser");
						laubblaeser.setItemMeta(laubblaeserMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(laubblaeser);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lPistol")) {
						
						ItemStack pistol = new ItemStack(Material.WOOD_HOE);
						ItemMeta pistolMeta = pistol.getItemMeta();
						pistolMeta.setDisplayName("§6§lPistol");
						pistol.setItemMeta(pistolMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(pistol);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWurfMine")) {
						ItemStack wurfmine = new ItemStack(Material.STONE_PLATE, 64);
						ItemMeta wurfmineMeta= wurfmine.getItemMeta();
						wurfmineMeta.setDisplayName("§6§lWurfMine");
						wurfmine.setItemMeta(wurfmineMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(wurfmine);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSniper")) {
						ItemStack sniper = new ItemStack(Material.DIAMOND_HOE, 1);
						ItemMeta sniperMeta= sniper.getItemMeta();
						sniperMeta.setDisplayName("§6§lSniper");
						sniper.setItemMeta(sniperMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(sniper);
					
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lRocketLauncher")) {
						ItemStack rocketlauncher = new ItemStack(Material.STONE_HOE, 1);
						ItemMeta rocketlauncherMeta= rocketlauncher.getItemMeta();
						rocketlauncherMeta.setDisplayName("§6§lRocketLauncher");
						rocketlauncher.setItemMeta(rocketlauncherMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(rocketlauncher);
						
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lAK")) {
						ItemStack ak = new ItemStack(Material.IRON_HOE, 1);
						ItemMeta akMeta= ak.getItemMeta();
						akMeta.setDisplayName("§6§lAK");
						ak.setItemMeta(akMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(ak);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchrotFlinte")) {
						ItemStack schrotflinte = new ItemStack(Material.WOOD_SPADE, 1);
						ItemMeta schrotflinteMeta= schrotflinte.getItemMeta();
						schrotflinteMeta.setDisplayName("§6§lSchrotFlinte");
						schrotflinte.setItemMeta(schrotflinteMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(schrotflinte);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lGranate")) {
						ItemStack granate = new ItemStack(Material.FIREWORK_CHARGE, 64);
						ItemMeta granateMeta= granate.getItemMeta();
						granateMeta.setDisplayName("§6§lGranate");
						granate.setItemMeta(granateMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(granate);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWhiteRide")) {
						ItemStack whiteride = new ItemStack(Material.BLAZE_ROD, 1);
						ItemMeta whiterideMeta= whiteride.getItemMeta();
						whiterideMeta.setDisplayName("§6§lWhiteRide");
						whiteride.setItemMeta(whiterideMeta);
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(whiteride);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMachineGun")) {
						ItemStack machinegun = new ItemStack(Material.STONE_SPADE, 1);
						ItemMeta machinegunMeta= machinegun.getItemMeta();
						machinegunMeta.setDisplayName("§6§lMachineGun");
						machinegun.setItemMeta(machinegunMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(machinegun);
						
					}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMultiTool")) {
						ItemStack multitool = new ItemStack(Material.DIAMOND_SPADE);
						ItemMeta multitoolMeta = multitool.getItemMeta();
						multitoolMeta.setDisplayName("§6§lMultiTool");
						multitool.setItemMeta(multitoolMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(multitool);
						
					}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lChickenWing")) {
						ItemStack chickenwing = new ItemStack(Material.FEATHER);
						ItemMeta chickenwingMeta = chickenwing.getItemMeta();
						chickenwingMeta.setDisplayName("§6§lChickenWing");
						chickenwing.setItemMeta(chickenwingMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(chickenwing);
					
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchildTrank")) {
						ItemStack SchildTrank = new ItemStack(Material.POTION,1,(byte)0);
						ItemMeta SchildTrankMeta = SchildTrank.getItemMeta();
						SchildTrankMeta.setDisplayName("§6§lSchildTrank");
						SchildTrank.setItemMeta(SchildTrankMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(SchildTrank);
						
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lJetPack")) {
						ItemStack jetpack = new ItemStack(Material.GOLD_CHESTPLATE);
						ItemMeta jetpackMeta = jetpack.getItemMeta();
						jetpackMeta.setDisplayName("§6§lJetPack");
						jetpack.setItemMeta(jetpackMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(jetpack);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lCrown")) {
						ItemStack crown = new ItemStack(Material.GOLD_HELMET);
						ItemMeta crownMeta = crown.getItemMeta();
						crownMeta.setDisplayName("§6§lCrown");
						crown.setItemMeta(crownMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(crown);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSlimeBoots")) {
						ItemStack slimeboots = new ItemStack(Material.GOLD_BOOTS);
						ItemMeta slimebootsMeta = slimeboots.getItemMeta();
						slimebootsMeta.setDisplayName("§6§lSlimeBoots");
						slimeboots.setItemMeta(slimebootsMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(slimeboots);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lC4")) {
						ItemStack c4 = new ItemStack(Material.STONE_BUTTON,64);
						ItemMeta c4Meta = c4.getItemMeta();
						c4Meta.setDisplayName("§6§lC4");
						c4.setItemMeta(c4Meta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(c4);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lSuperLeiter")) {
						ItemStack superladder = new ItemStack(Material.LADDER, 64);
						ItemMeta superladderMeta = superladder.getItemMeta();
						superladderMeta.setDisplayName("§e§lSuperLeiter");
						superladder.setItemMeta(superladderMeta);
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.getInventory().setItemInHand(superladder);
					
					}
					
					
					
					
			}
		}
		}
	}
	}
	}

}
