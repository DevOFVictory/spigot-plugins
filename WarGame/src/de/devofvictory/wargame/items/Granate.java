package de.devofvictory.wargame.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;

public class Granate implements Listener{
	
	public static Player lastShooter;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (e.getMaterial() == Material.FIREWORK_CHARGE) {
				if (e.getItem().getItemMeta().getDisplayName() != null) {
					if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lGranate")) {
				if (p.hasPermission("wargame.weapon.granate")) {
					if (p.getItemInHand().getAmount() == 1) {
						if (!Command_Infinitymode.infinitymode.contains(p))
							p.getInventory().remove(p.getItemInHand());
							final Item granate = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.FIREWORK_CHARGE));
							
							if (!p.isSneaking()) {
								granate.setVelocity(p.getEyeLocation().getDirection().multiply(1));
							}else {
								granate.setVelocity(p.getLocation().getDirection().multiply(3));
							}
							
							lastShooter = p;
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									granate.getWorld().createExplosion(granate.getLocation().getX(), granate.getLocation().getY(), granate.getLocation().getZ(), 5, false, true);
									
								}
							}, 2*20);
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									lastShooter = null;
									
								}
							},3*20);
				
						
						return;
					}
					if (!Command_Infinitymode.infinitymode.contains(p))
						e.getItem().setAmount(e.getItem().getAmount()-1);
						final Item granate = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.FIREWORK_CHARGE));
					if (!p.isSneaking()) {
						granate.setVelocity(p.getEyeLocation().getDirection().multiply(1D));
					}else {
						granate.setVelocity(p.getLocation().getDirection().multiply(2));
					}
					
					lastShooter = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								granate.getWorld().createExplosion(granate.getLocation().getX(), granate.getLocation().getY(), granate.getLocation().getZ(), 5, false, true);
								
							}
						}, 2*20);
						
							}
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						lastShooter = null;
						
					}
				},3*20);
				
				
							}else {
								p.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");
							}
						
					}
				}
			}
		}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().getType() == Material.FIREWORK_CHARGE) {
			e.setCancelled(true);
			e.getItem().remove();
		}
	}
	}


