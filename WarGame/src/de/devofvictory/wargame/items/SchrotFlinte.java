package de.devofvictory.wargame.items;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.ClearUtil;

public class SchrotFlinte implements Listener{
	
	public static HashMap<Player, Integer> shootsLeft = new HashMap<>();
	public static HashMap<Player, Boolean> isRealoding = new HashMap<>();
	
	public static HashMap<String, Long> lastShoot = new HashMap<>();
	
	private static double delay = 2;
	private static double reload = 3;
	public static int shoots = 7;
	
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
				if (e.getItem().getType() == Material.WOOD_SPADE) {
					if (e.getItem().getItemMeta().getDisplayName() != null) {
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchrotFlinte")) {
							if (p.hasPermission("wargame.weapon.schrotflinte")) {
								e.setCancelled(true);
								
								
								
								if (lastShoot.get(p.getName()).longValue() + delay*1000 < Long.valueOf(System.currentTimeMillis())) {
									
									
								
								
								if (!isRealoding.get(p)) {
									
									
								
								if (shootsLeft.get(p) > 0) {
									
									Vector direction = p.getLocation().getDirection();
									
									
									
									for (int i=0; i<10; i++) {
										double random1 = getRandomDouble(-0.25, 0.25);
										double random2 = getRandomDouble(-0.15, 0.15);
										double random3 = getRandomDouble(-0.25, 0.25);
										
										Snowball sb = p.launchProjectile(Snowball.class, direction.add(new Vector(random1, random2, random3)));
										sb.setCustomName("shotgunsnowball");
										
									}
									
									ActionBar.sendActionBarTime(p, "§6§lSchrotflinte §8» §c"+(shootsLeft.get(p)-1)+" §f/ §a"+shoots, 2*20);
									if (!shootsLeft.containsKey(p)) {
										shootsLeft.put(p, shoots-1);
									}else {
										if (!Command_Infinitymode.infinitymode.contains(p) && shootsLeft.get(p) > 0)
										shootsLeft.put(p, shootsLeft.get(p)-1);
									}
									
									lastShoot.put(p.getName(), System.currentTimeMillis());
									
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
					if (e.getItem().getType() == Material.WOOD_SPADE) {
						if (e.getItem().getItemMeta().getDisplayName() != null) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchrotFlinte")) {
								if (p.hasPermission("wargame.weapon.schrotflinte")) {
									e.setCancelled(true);
									
									
									
									if (!isRealoding.get(p)) {
										ItemStack is = new ItemStack(Material.MELON_SEEDS);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName("§7§lSchrot");
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
	
	public double getRandomDouble(double min, double max){
		Random r = new Random();
		double randomDouble = r.nextDouble();
		double result = min + (max - min) * randomDouble;
		return result;
		}
	
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball) {
				if (e.getDamager().getCustomName() != null) {
				if (e.getDamager().getCustomName().equalsIgnoreCase("shotgunsnowball")) {
					e.setDamage(10);
				}
				}
			}
		
	}
	}


