package de.devofvictory.survivalproject.items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.devofvictory.survivalproject.custom.ActionBar;
import de.devofvictory.survivalproject.custom.ClearUtil;
import de.devofvictory.survivalproject.main.Main;

public class JetPack implements Listener{
	
	static HashMap<Player, Integer> fuelInUse = new HashMap<>();
	static ArrayList<Player> nofall = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if (e.isSneaking() && !p.isOnGround()) {
			
			
			if (!fuelInUse.containsKey(p)) {
				fuelInUse.put(p, 0);
			}
			
			try {
				if (p.getInventory().getChestplate().getType() == Material.GOLDEN_CHESTPLATE && p.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lJetPack")) {
					

					// JetPack
					// Fuel
					// SchildTrank
					
					
					if (p.getInventory().getChestplate().getDurability()+1 <= 112) {
						
						if (ClearUtil.hasEnough(p, Material.COAL, "§6§lFuel", 1) || fuelInUse.get(p) > 0) {
							
							if (fuelInUse.get(p) == 0) {
								if (ClearUtil.hasEnough(p, Material.COAL, "§6§lFuel", 1)) {
									ClearUtil.removeInventoryItems(p, Material.COAL, "§6§lFuel", 1);
									fuelInUse.put(p, 10);
								}
							}
							
							/* 
							 * 2,a,e,6,c,4
							 * 112-94 = 4
							 * 93 -76 = c
							 * 75 -58 = 6
							 * 57 -40 = e
							 * 39 -22 = a
							 * 21 -00 = 2
							 */
							
							fuelInUse.put(p, fuelInUse.get(p)-1);
							
							short duability = p.getInventory().getChestplate().getDurability();
							
							if (isBetween(duability, 94, 112)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §4§l"+fuelInUse.get(p));
							}else if (isBetween(duability, 76, 93)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §c§l"+fuelInUse.get(p));
							}else if (isBetween(duability, 58, 75)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §6§l"+fuelInUse.get(p));
							}else if (isBetween(duability, 40, 57)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §e§l"+fuelInUse.get(p));
							}else if (isBetween(duability, 22, 39)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §a§l"+fuelInUse.get(p));
							}else if (isBetween(duability, 0, 21)) {
								ActionBar.sendActionBar(p, "§d§lJetPack-Ladung §f» §2§l"+fuelInUse.get(p));
							}
							
							p.getInventory().getChestplate().setDurability((short) (p.getInventory().getChestplate().getDurability()+1));
							p.setVelocity(p.getLocation().getDirection().multiply(0.7D).setY(0.5D));
							
							p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
							
							p.getWorld().spawnParticle(Particle.LAVA, p.getLocation(), 1);
							p.getWorld().spawnParticle(Particle.DRIP_LAVA, p.getLocation(), 1);
							p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation(), 1);
							p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 1);
							p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 1);
							p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 1);
							
							if (!nofall.contains(p)) {
								nofall.add(p);
								
								Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
									
									@Override
									public void run() {
										
										nofall.remove(p);
										
									}
								}, 5*20);
							}
							
							
						}else {
							p.sendMessage(Main.Prefix+"§cDu hast keinen Treibstoff (§6§lFuel§c)!");
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 8, 8);
						}
					}else {
						p.getInventory().setChestplate(null);
						p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				}
					
					
					
				}
			
			}catch (NullPointerException ex) {
			}
		}
	}
	
	@EventHandler
	public void onDamgage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL) {
			Player p = (Player)e.getEntity();
			if (nofall.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
	
	
	boolean isBetween(int a, int b, int c) {
		return a >= b ? a <= c : false;
		}

}
