package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.ClearUtil;

public class Pistol implements Listener{
	
	public static HashMap<Player, Integer> shootsLeft = new HashMap<>();
	public static HashMap<Player, Boolean> isRealoding = new HashMap<>();
	
	public static HashMap<String, Long> lastShoot = new HashMap<>();
	
	private static double delay = 1;
	private static double reload = 3;
	public static int shoots = 14;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.EMERALD_BLOCK) {
				return;
			}
		}
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getItem() != null) {
				if (e.getItem().getType() != Material.AIR) {
				if (e.getItem().getType() == Material.WOOD_HOE) {
					if (e.getItem().getItemMeta().getDisplayName() != null) {
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lPistol")) {
							if (p.hasPermission("wargame.weapon.pistol")) {
								e.setCancelled(true);
								
								if (lastShoot.get(p.getName()).longValue() + delay*1000 < Long.valueOf(System.currentTimeMillis())) {
									

								
								if (!isRealoding.get(p)) {
									
								
								if (shootsLeft.get(p) > 0) {
									
									Arrow ar = p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(3D));
									ar.setCustomName("pistolarrow");
									lastShoot.put(p.getName(), System.currentTimeMillis());
									ActionBar.sendActionBarTime(p, "§6§lPistol §8» §c"+(shootsLeft.get(p)-1)+" §f/ §a"+shoots, 2*20);
									

									if (!shootsLeft.containsKey(p)) {
										shootsLeft.put(p, shoots-1);
									}else {
										if (!Command_Infinitymode.infinitymode.contains(p) && shootsLeft.get(p) > 0)
										shootsLeft.put(p, shootsLeft.get(p)-1);
									}
								}else {
									p.sendMessage(Main.Prefix+"§cDu musst vorher nachladen!");
								}
								}else {
									p.sendMessage(Main.Prefix+"§eWaffe läd nach...");
								}
								
								}
								
								
								
							}else {
								p.sendMessage(Main.Prefix+"§cDafür hast du nicht genug Rechte!");
							}
						}
					}
				
			}
			}
			}
			}else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
				if (e.getItem() != null) {
					if (e.getItem().getType() != Material.AIR) {
					if (e.getItem().getType() == Material.WOOD_HOE) {
						if (e.getItem().getItemMeta().getDisplayName() != null) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lPistol")) {
								if (p.hasPermission("wargame.weapon.pistol")) {
									e.setCancelled(true);
									
									
									
									if (!isRealoding.get(p)) {
										ItemStack is = new ItemStack(Material.GOLD_NUGGET);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName("§7§lPistolAmmo");
										is.setItemMeta(meta);
										
										if (ClearUtil.hasEnough(p, is.getType(), is.getItemMeta().getDisplayName(), 1)) {
								
										isRealoding.put(p, true);
										p.sendMessage(Main.Prefix+"§eWaffe wird nachgeladen! Bitte warten...");
									
								
									

								Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									if (isRealoding.get(p)) {
										if (ClearUtil.hasEnough(p, is.getType(), is.getItemMeta().getDisplayName(), 1)) {
											ClearUtil.removeInventoryItems(p, is.getType(), is.getItemMeta().getDisplayName(), 1);
										
											shootsLeft.put(p, shoots);
											p.sendMessage(Main.Prefix+"§aWaffe wurde nachgeladen! §6("+shoots+" Shoots left)");
											isRealoding.put(p, false);
										}else {
											p.sendMessage(Main.Prefix+"§cDu hast keine "+is.getItemMeta().getDisplayName()+"§c!");
											isRealoding.put(p, false);
										}

								}
								}
							}, (int)(reload*20));
										}else {
											p.sendMessage(Main.Prefix+"§cDu hast keine "+is.getItemMeta().getDisplayName()+"§c!");
											
										}
								
								}else {
									p.sendMessage(Main.Prefix+"§cWaffe wird bereits nachgeladen!");
								}
							}
						}
						}
					}
					}
				}
				
			}
		}
	}


