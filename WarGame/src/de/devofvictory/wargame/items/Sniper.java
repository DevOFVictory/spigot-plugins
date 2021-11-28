package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.ClearUtil;

public class Sniper implements Listener{
	
	public static HashMap<Player, Integer> shootsLeft = new HashMap<>();
	public static HashMap<Player, Boolean> isRealoding = new HashMap<>();
	
	private static double reload = 6;
	public static int shoots = 1;
	
	private static HashMap<String,ItemStack> helmet = new HashMap<>();
	
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
				if (e.getItem().getType() == Material.DIAMOND_HOE) {
					if (e.getItem().getItemMeta().getDisplayName() != null) {
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSniper")) {
							if (p.hasPermission("wargame.weapon.sniper")) {
								e.setCancelled(true);
								
								
								
								
								if (!isRealoding.get(p)) {
									
									
								
								if (shootsLeft.get(p) > 0) {
									Arrow ar = p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(80D));
									ar.setCustomName("sniperarrow");
									ActionBar.sendActionBarTime(p, "§6§lSniper §8» §c"+(shootsLeft.get(p)-1)+" §f/ §a"+shoots, 2*20);

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
					if (e.getItem().getType() == Material.DIAMOND_HOE) {
						if (e.getItem().getItemMeta().getDisplayName() != null) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSniper")) {
								if (p.hasPermission("wargame.weapon.sniper")) {
									e.setCancelled(true);
									
									
									
									if (!isRealoding.get(p)) {
										ItemStack is = new ItemStack(Material.ARROW);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName("§7§lSniperAmmo");
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
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
	try {
		if (!p.isSneaking()) {
		if (p.getItemInHand().getType() == Material.DIAMOND_HOE && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSniper")) {
			
				PotionEffect slowness	= new PotionEffect(PotionEffectType.SLOW, 1000000, 255, false, false);
				slowness.hasParticles();
				p.addPotionEffect(slowness, true);
				
				if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR)
					helmet.put(p.getName(), p.getInventory().getHelmet());
				
				ItemStack is = new ItemStack(Material.PUMPKIN);
				p.getInventory().setHelmet(is);
			}
		}else {
			p.removePotionEffect(PotionEffectType.SLOW);
			if (p.getInventory().getHelmet().getType() == Material.PUMPKIN) {
				
				if (helmet.containsKey(p.getName())) {
					p.getInventory().setHelmet(helmet.get(p.getName()));
				}else {
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
			}
			}
		}
		
	}catch (NullPointerException e2) {
		
	}
		
		
	}
	
	@EventHandler
	public void onHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		try {
		if (p.getInventory().getItem(e.getPreviousSlot()).getType() == Material.DIAMOND_HOE && p.getInventory().getItem(e.getPreviousSlot()).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSniper")) {
			p.removePotionEffect(PotionEffectType.SLOW);
			if (p.getInventory().getHelmet().getType() == Material.PUMPKIN) {
				
				if (helmet.containsKey(p.getName())) {
					p.getInventory().setHelmet(helmet.get(p.getName()));
				}else {
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
			}
			}
		}
	}catch (NullPointerException e3) {
		// TODO: handle exception
	}
	}
	
	@EventHandler
	public void onDamge(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			try {
				if (e.getDamager().getCustomName().equalsIgnoreCase("sniperarrow")) {
					e.setDamage(20);
				}
			}catch (NullPointerException ex) {
				// TODO: handle exception
			}
		}
	}
	
	}


