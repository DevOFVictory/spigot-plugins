package de.devofvictory.wargame.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;


public class LaubBlaeser implements Listener{
	
	public static ArrayList<Player> noDamage = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.EMERALD_BLOCK) {
				return;
			}
		}
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getItem() != null && e.getItem().getType() != null) {
				if (e.getItem().getType() == Material.GOLD_HOE) {
					if (e.getItem().getItemMeta().getDisplayName() != null) {
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lLaubBläser")) {
						if (p.hasPermission("wargame.weapon.laubblaeser")) {

					if (e.getItem().getDurability() < 32) {
						
						if (!Command_Infinitymode.infinitymode.contains(p)) e.getItem().setDurability((short) (e.getItem().getDurability()+1));
						p.getWorld().playSound(p.getLocation(), Sound.FIZZ, (long)1, (long)10);
						//Location looking = p.getTargetBlock((Set<Material>)null, 10).getLocation();
						List<Block> list = p.getLineOfSight((HashSet<Byte>)null, 10);
						e.setCancelled(true);
					for (Block block : list) {
						p.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 0);
						
					}
					if (p.getLineOfSight((HashSet<Byte>)null, 20).size() <= 10) {
						int distance = p.getLineOfSight((HashSet<Byte>)null, 20).size();
						Vector direction = p.getLocation().getDirection();
						Vector a = direction.divide(new Vector(distance, distance, distance));
						Vector back = new Vector(a.getX()/(-1), a.getY()/(-1), a.getZ()/(-1));
						p.setVelocity(back.multiply(3));
						
						noDamage.add(p);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								noDamage.remove(p);
								
							}
						}, 2*20);
					}
					for (Entity near : p.getNearbyEntities(10, 10, 10)) {
						
								
								
								if(list.contains(near.getLocation().add(0, 1, 0).getBlock()) || list.contains(near.getLocation().getBlock())) {
									near.setVelocity(p.getLocation().getDirection().multiply(3D));
									Main.lastBlaeserHit.put(near.getName(), p.getName());
									
									
									
									Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
										
										@Override
										public void run() {
											Main.lastBlaeserHit.remove(near.getName());
											
										}
									}, 5*20);
								}
						}
					}else {
						p.getInventory().remove(p.getItemInHand());
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10, 10);
					}
					
				}else {
					p.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");
				}
					}
			}
				}
			
			}
		}
		}
	
	

}
