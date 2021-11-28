package de.devofvictory.skykitpvp.specialitems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class Slimeball implements Listener{
	
	public static String name = "§4§lSchleimball";
	public static int price = 100;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (e.getMaterial() == Material.SLIME_BALL) {
				if (e.getItem().getItemMeta().getDisplayName() != null) {
					if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
						
						p.playSound(p.getLocation(), Sound.BLOCK_HONEY_BLOCK_STEP, 1, 1);
						
					if (p.getInventory().getItemInMainHand().getAmount() == 1) {
							p.getInventory().remove(p.getInventory().getItemInMainHand());
							final Item slimeball = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.SLIME_BALL));
							
							if (!p.isSneaking()) {
								slimeball.setVelocity(p.getEyeLocation().getDirection().multiply(1));
							}else {
								slimeball.setVelocity(p.getLocation().getDirection().multiply(3));
							}
							
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									
									if (OtherUtils.isSpawn(slimeball.getLocation())) {
									
										for (Player player : OtherUtils.getNearbyPlayers(slimeball, 8)) {
											PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 5*20, 2, false, false, false);
											player.addPotionEffect(slow);
											player.playSound(p.getLocation(), Sound.BLOCK_HONEY_BLOCK_STEP, 1, 1);
											player.sendMessage(Main.Prefix+"§2\"§2Igitt! Hier klebt ja alles!\"");
										}
										
										slimeball.getWorld().spawnParticle(Particle.SLIME, slimeball.getLocation().getX(), slimeball.getLocation().getY(), slimeball.getLocation().getZ(), 
												5, 4, 5, 0.1, 1000);
									
									}
									
									slimeball.remove();
									
								}
							}, 2*20);
							
				
						
						return;
					}
						e.getItem().setAmount(e.getItem().getAmount()-1);
						final Item slimeball = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.SLIME_BALL));
					if (!p.isSneaking()) {
						slimeball.setVelocity(p.getEyeLocation().getDirection().multiply(1D));
					}else {
						slimeball.setVelocity(p.getLocation().getDirection().multiply(2));
					}
					
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								for (Player player : OtherUtils.getNearbyPlayers(slimeball, 8)) {
									PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 5*20, 2, false, false, false);
									player.addPotionEffect(slow);
									
									player.playSound(p.getLocation(), Sound.BLOCK_HONEY_BLOCK_STEP, 1, 1);
									player.sendMessage(Main.Prefix+"§2\"Igitt! Hier klebt ja alles!\"");
								}
								
								slimeball.getWorld().spawnParticle(Particle.SLIME, slimeball.getLocation().getX(), slimeball.getLocation().getY(), slimeball.getLocation().getZ(), 
										5, 4, 5, 0.1, 1000);
								
								slimeball.remove();
							}
						}, 2*20);
						
							}
						
					}
				}
			}
		}

}
