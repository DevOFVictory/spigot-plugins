package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.ClearUtil;

public class WhiteRide implements Listener{
	
	public static HashMap<Player, Integer> shootsLeft = new HashMap<>();
	public static HashMap<Player, Boolean> isRealoding = new HashMap<>();
	
	private static double reload = 10;
	public static int shoots = 1;
	
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				if (e.getItem().getType() != Material.AIR) {
				if (e.getItem().getType() == Material.BLAZE_ROD) {
					if (e.getItem().getItemMeta().getDisplayName() != null) {
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWhiteRide")) {
							if (p.hasPermission("wargame.weapon.whiteride")) {
								e.setCancelled(true);
								
								
								
								
								if (!isRealoding.get(p)) {
									
									if (p.getLocation().getPitch() > -85) {

									
									
								
								if (shootsLeft.get(p) > 0) {
									EnderPearl ep = p.launchProjectile(EnderPearl.class, p.getLocation().getDirection().multiply(3D));
									
									ep.setPassenger((Entity)p);
									ep.setCustomName("whiterideenderpearl");
									ActionBar.sendActionBarTime(p, "§6§lWhiteRide §8» §c"+(shootsLeft.get(p)-1)+" §f/ §a"+shoots, 2*20);
									
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
										p.sendMessage(Main.Prefix+"§cDein Blickwinkel ist zu steil!");
									}
								}else {
									p.sendMessage(Main.Prefix+"§eWaffe läd nach...");
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
					if (e.getItem().getType() == Material.BLAZE_ROD) {
						if (e.getItem().getItemMeta().getDisplayName() != null) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWhiteRide")) {
								if (p.hasPermission("wargame.weapon.whiteride")) {
									e.setCancelled(true);
									
									
									
									
									if (!isRealoding.get(p)) {
										
										ItemStack is = new ItemStack(Material.ENDER_PEARL);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName("§7§lWhiteRideAmmo");
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
													p.sendMessage(Main.Prefix+"§aWaffe wurde nachgeladen! §6("+shoots+" Shoot left)");
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
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof EnderPearl) {
			
			EnderPearl ep = (EnderPearl)e.getEntity();
			if (ep.getShooter() instanceof Player) {
				Player p = (Player) ep.getShooter();
				
				try {
				if (ep.getCustomName().equalsIgnoreCase("whiterideenderpearl")) {
					if (ep.getPassenger() != null) {
						p.teleport(p.getLocation().add(0, 1.5, 0));
						p.damage(2);
						p.sendMessage("§f[§6§lWhiteRideService§f] §7Wir hoffen sie hatten einen angenehmen Flug! Auf Wiedersehen!");
					}
					
				}
				}catch (NullPointerException ex) {
					
				}
			}
			
			
		}
	}
	

	}


