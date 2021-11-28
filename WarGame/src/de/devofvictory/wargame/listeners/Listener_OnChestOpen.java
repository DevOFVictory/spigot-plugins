package de.devofvictory.wargame.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.StartGame;

public class Listener_OnChestOpen implements Listener {
	
	HashMap<Location, Inventory> chests = new HashMap<>();
	
	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent e) {
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.EMERALD_BLOCK) {
			Player p = e.getPlayer();
			e.setCancelled(true);
		
			if (chests.containsKey(e.getClickedBlock().getLocation())) {
				if (!StartGame.spectators.contains(p)) {
				Inventory inv = chests.get(e.getClickedBlock().getLocation());
				p.openInventory(inv);
				p.playSound(p.getLocation(), Sound.CHEST_OPEN, 10, 10);
				
				if (SpectatorClass.specCamera.containsValue(p.getName())) {
					for (String playername : SpectatorClass.specCamera.keySet()) {
						if (SpectatorClass.specCamera.get(playername).equalsIgnoreCase(p.getName())) {
							Player spectator = Bukkit.getPlayer(playername);
							
							if (spectator != null) {
								spectator.openInventory(inv);
								spectator.playSound(p.getLocation(), Sound.CHEST_OPEN, 10, 10);
							}
						}
					}
				}
				}
				
				
			}else {
				
				if (!StartGame.spectators.contains(p)) {
			
				Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
		
				ArrayList<ItemStack> all = new ArrayList<>();
				
				Random random = new Random();
				int amount = random.nextInt(8)+1;
				int slot;
				
				ItemStack wurfmine = new ItemStack(Material.STONE_PLATE, random.nextInt(5)+1);
				ItemMeta wurfmineMeta= wurfmine.getItemMeta();
				wurfmineMeta.setDisplayName("§6§lWurfMine");
				wurfmine.setItemMeta(wurfmineMeta);
				
				ItemStack pistol = new ItemStack(Material.WOOD_HOE);
				ItemMeta pistolMeta = pistol.getItemMeta();
				pistolMeta.setDisplayName("§6§lPistol");
				pistol.setItemMeta(pistolMeta);
				
				ItemStack laubblaeser = new ItemStack(Material.GOLD_HOE);
				ItemMeta laubblaeserMeta = laubblaeser.getItemMeta();
				laubblaeserMeta.setDisplayName("§6§lLaubBläser");
				laubblaeser.setItemMeta(laubblaeserMeta);
				
				ItemStack rocketlauncher = new ItemStack(Material.STONE_HOE);
				ItemMeta rocketlauncherMeta = rocketlauncher.getItemMeta();
				rocketlauncherMeta.setDisplayName("§6§lRocketLauncher");
				rocketlauncher.setItemMeta(rocketlauncherMeta);
				
				ItemStack ak = new ItemStack(Material.IRON_HOE);
				ItemMeta akMeta = ak.getItemMeta();
				akMeta.setDisplayName("§6§lAK");
				ak.setItemMeta(akMeta);
				
				ItemStack sniper = new ItemStack(Material.DIAMOND_HOE);
				ItemMeta sniperMeta = sniper.getItemMeta();
				sniperMeta.setDisplayName("§6§lSniper");
				sniper.setItemMeta(sniperMeta);
				
				ItemStack schrotflinte = new ItemStack(Material.WOOD_SPADE);
				ItemMeta schrotflinteMeta = sniper.getItemMeta();
				schrotflinteMeta.setDisplayName("§6§lSchrotFlinte");
				schrotflinte.setItemMeta(schrotflinteMeta);
				
				ItemStack granate = new ItemStack(Material.FIREWORK_CHARGE, random.nextInt(5)+1);
				ItemMeta granateMeta = sniper.getItemMeta();
				granateMeta.setDisplayName("§6§lGranate");
				granate.setItemMeta(granateMeta);
				
				ItemStack whiteride = new ItemStack(Material.BLAZE_ROD, 1);
				ItemMeta whiterideMeta = sniper.getItemMeta();
				whiterideMeta.setDisplayName("§6§lWhiteRide");
				whiteride.setItemMeta(whiterideMeta);
				
				ItemStack machinegun = new ItemStack(Material.STONE_SPADE, 1);
				ItemMeta machinegunMeta = sniper.getItemMeta();
				machinegunMeta.setDisplayName("§6§lMachineGun");
				machinegun.setItemMeta(machinegunMeta);
				
				ItemStack bread = new ItemStack(Material.BREAD, random.nextInt(10)+1);
				ItemStack apple = new ItemStack(Material.APPLE, random.nextInt(10)+1);
				ItemStack carrot = new ItemStack(Material.CARROT, random.nextInt(10)+1);
				ItemStack melon = new ItemStack(Material.MELON, random.nextInt(10)+1);
				
				ItemStack stoneX16 = new ItemStack(Material.STONE, 16);
				ItemStack stoneX32 = new ItemStack(Material.STONE, 32);
				ItemStack obsidian = new ItemStack(Material.OBSIDIAN, random.nextInt(7)+1);
				
				ItemStack chickenwing = new ItemStack(Material.FEATHER,1);
				ItemMeta chickenwingMeta = chickenwing.getItemMeta();
				chickenwingMeta.setDisplayName("§6§lChickenWing");
				chickenwing.setItemMeta(chickenwingMeta);
				
				ItemStack ironHelmelt = new ItemStack(Material.IRON_HELMET);
				ItemStack ironChestplate = new ItemStack(Material.IRON_CHESTPLATE);
				ItemStack ironLeggins = new ItemStack(Material.IRON_LEGGINGS);
				ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
				
				ItemStack leatherHelmelt = new ItemStack(Material.LEATHER_HELMET);
				ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
				ItemStack leatherLeggins = new ItemStack(Material.LEATHER_LEGGINGS);
				ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS);
				
				
				
				ItemStack pistolAmmo = new ItemStack(Material.GOLD_NUGGET,random.nextInt(5)+1);
				ItemMeta pistolAmmoMeta = pistolAmmo.getItemMeta();
				pistolAmmoMeta.setDisplayName("§7§lPistolAmmo");
				pistolAmmo.setItemMeta(pistolAmmoMeta);
				
				ItemStack rocket = new ItemStack(Material.FIREBALL,random.nextInt(3)+1);
				ItemMeta rocketMeta = rocket.getItemMeta();
				rocketMeta.setDisplayName("§7§lRockets");
				rocket.setItemMeta(rocketMeta);
				
				ItemStack akAmmo = new ItemStack(Material.SLIME_BALL,random.nextInt(3)+1);
				ItemMeta akAmmoMeta = akAmmo.getItemMeta();
				akAmmoMeta.setDisplayName("§7§lAKAmmo");
				akAmmo.setItemMeta(akAmmoMeta);
				
				ItemStack sniperAmmo = new ItemStack(Material.ARROW,random.nextInt(3)+1);
				ItemMeta sniperAmmoMeta = sniperAmmo.getItemMeta();
				sniperAmmoMeta.setDisplayName("§7§lSniperAmmo");
				sniperAmmo.setItemMeta(sniperAmmoMeta);
				
				ItemStack schrot = new ItemStack(Material.MELON_SEEDS,random.nextInt(9)+1);
				ItemMeta schrotMeta = schrot.getItemMeta();
				schrotMeta.setDisplayName("§7§lSchrot");
				schrot.setItemMeta(schrotMeta);
				
				ItemStack whiterideAmmo = new ItemStack(Material.ENDER_PEARL,random.nextInt(3)+1);
				ItemMeta whiterideAmmoMeta = whiterideAmmo.getItemMeta();
				whiterideAmmoMeta.setDisplayName("§7§lWhiteRideAmmo");
				whiterideAmmo.setItemMeta(whiterideAmmoMeta);
				
				ItemStack machinegunAmmo = new ItemStack(Material.DOUBLE_PLANT,random.nextInt(12)+1);
				ItemMeta machinegunAmmoMeta = machinegunAmmo.getItemMeta();
				machinegunAmmoMeta.setDisplayName("§7§lMachineGunAmmo");
				machinegunAmmo.setItemMeta(machinegunAmmoMeta);
				
				ItemStack schildTrank = new ItemStack(Material.POTION,1,(byte)0);
				ItemMeta SchildTrankMeta = schildTrank.getItemMeta();
				SchildTrankMeta.setDisplayName("§6§lSchildTrank");
				schildTrank.setItemMeta(SchildTrankMeta);
				
				ItemStack jetpack = new ItemStack(Material.GOLD_CHESTPLATE);
				ItemMeta jetpackMeta = jetpack.getItemMeta();
				jetpackMeta.setDisplayName("§6§lJetPack");
				jetpack.setItemMeta(jetpackMeta);
				
				ItemStack fuel = new ItemStack(Material.COAL,random.nextInt(10)+1);
				ItemMeta fuelMeta = fuel.getItemMeta();
				fuelMeta.setDisplayName("§6§lFuel");
				fuel.setItemMeta(fuelMeta);
				
				ItemStack crown = new ItemStack(Material.GOLD_HELMET);
				ItemMeta crownMeta = crown.getItemMeta();
				crownMeta.setDisplayName("§6§lCrown");
				crown.setItemMeta(crownMeta);
				
				ItemStack slimeboots = new ItemStack(Material.GOLD_BOOTS);
				ItemMeta slimebootsMeta = slimeboots.getItemMeta();
				slimebootsMeta.setDisplayName("§6§lSlimeBoots");
				slimeboots.setItemMeta(slimebootsMeta);
				
				ItemStack c4 = new ItemStack(Material.STONE_BUTTON,random.nextInt(20)+1);
				ItemMeta c4Meta = c4.getItemMeta();
				c4Meta.setDisplayName("§6§lC4");
				c4.setItemMeta(c4Meta);
				
				ItemStack superladder = new ItemStack(Material.LADDER,random.nextInt(40)+1);
				ItemMeta superladderMeta = superladder.getItemMeta();
				superladderMeta.setDisplayName("§e§lSuperLeiter");
				superladder.setItemMeta(superladderMeta);
				
				// §e§lSuperLeiter
				
				String position = e.getClickedBlock().getLocation().getBlockX()+ " " + e.getClickedBlock().getLocation().getBlockY() + " " + e.getClickedBlock().getLocation().getBlockZ();
				
				
				if (!StartGame.allStrings.contains(position)) {

					all.add(wurfmine);
					all.add(wurfmine);
					all.add(wurfmine);
					all.add(wurfmine);
					
					all.add(pistol);
					all.add(pistol);
					all.add(pistol);
					
					all.add(laubblaeser);
					all.add(laubblaeser);
					all.add(laubblaeser);
					all.add(laubblaeser);
					
					all.add(rocketlauncher);
					all.add(rocketlauncher);
					all.add(ak);
					all.add(ak);
					all.add(sniper);
					all.add(sniper);
					all.add(schrotflinte);
					
					all.add(granate);
					all.add(granate);
					all.add(granate);
					all.add(whiteride);
					
					all.add(machinegun);
					all.add(machinegun);
					
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(carrot);
					all.add(carrot);
					all.add(carrot);
					all.add(apple);
					all.add(apple);
					all.add(apple);
					all.add(melon);
					all.add(melon);
					all.add(melon);
	
					all.add(stoneX16);
					all.add(stoneX32);
					all.add(stoneX16);
					all.add(stoneX32);
					
					all.add(obsidian);
					all.add(obsidian);
					all.add(obsidian);
					
					all.add(chickenwing);
					
					all.add(ironHelmelt);
					all.add(ironChestplate);
					all.add(ironLeggins);
					all.add(ironBoots);
					
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					
					all.add(schildTrank);
					all.add(schildTrank);
					all.add(schildTrank);
					
					all.add(crown);
					all.add(jetpack);
					all.add(slimeboots);
					all.add(slimeboots);
					all.add(fuel);
					all.add(fuel);
					all.add(fuel);
					
					all.add(c4);
					all.add(c4);
					all.add(c4);
					
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
				
				
				}else {
					all.add(wurfmine);
					all.add(wurfmine);
					all.add(wurfmine);
					all.add(wurfmine);
					
					all.add(pistol);
					all.add(pistol);
					all.add(pistol);
					
					all.add(laubblaeser);
					all.add(laubblaeser);
					all.add(laubblaeser);
					all.add(laubblaeser);
					
					all.add(ak);
					all.add(ak);
					all.add(schrotflinte);
					
					all.add(granate);
					all.add(granate);
					all.add(granate);
					all.add(whiteride);
					
					all.add(machinegun);
					all.add(machinegun);
					
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(bread);
					all.add(carrot);
					all.add(carrot);
					all.add(carrot);
					all.add(apple);
					all.add(apple);
					all.add(apple);
					all.add(melon);
					all.add(melon);
					all.add(melon);

					all.add(stoneX16);
					all.add(stoneX32);
					all.add(stoneX16);
					all.add(stoneX32);
					
					all.add(obsidian);
					all.add(obsidian);
					all.add(obsidian);
					
					all.add(chickenwing);
					
					all.add(ironHelmelt);
					all.add(ironChestplate);
					all.add(ironLeggins);
					all.add(ironBoots);
					
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					all.add(leatherHelmelt);
					all.add(leatherChestplate);
					all.add(leatherLeggins);
					all.add(leatherBoots);
					
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					all.add(pistolAmmo);
					all.add(pistolAmmo);
					all.add(rocket);
					all.add(akAmmo);
					all.add(sniperAmmo);
					all.add(schrot);
					all.add(schrot);
					all.add(whiterideAmmo);
					all.add(machinegunAmmo);
					all.add(machinegunAmmo);
					
					all.add(schildTrank);
					all.add(schildTrank);
					all.add(schildTrank);
					
					all.add(fuel);
					all.add(fuel);
					all.add(fuel);
					
					all.add(c4);
					all.add(c4);
					all.add(c4);
					
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					all.add(superladder);
					
				}
			
				
				
				int item = 0;

				for (int i = 0; i < amount; i++) {
					item = random.nextInt(all.size()-1)+1;
					slot = random.nextInt(26);
					ItemStack is = all.get(item);
					
					inv.setItem(slot, is);

				}
				
				p.openInventory(inv);
				chests.put(e.getClickedBlock().getLocation(), inv);
				p.playSound(p.getLocation(), Sound.CHEST_OPEN, 10, 10);
				
				if (SpectatorClass.specCamera.containsValue(p.getName())) {
					for (String playername : SpectatorClass.specCamera.keySet()) {
						if (SpectatorClass.specCamera.get(playername).equalsIgnoreCase(p.getName())) {
							Player spectator = Bukkit.getPlayer(playername);
							
							if (spectator != null) {
								spectator.openInventory(inv);
								spectator.playSound(p.getLocation(), Sound.CHEST_OPEN, 10, 10);
							}
						}
					}
				}

				
				}
		
			}
	}
	}
}
