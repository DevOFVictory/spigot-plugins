package de.devofvictory.wargame.items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.StartGame;

public class WurfMine implements Listener{
	
	HashMap<Item, Boolean> hashmap = new HashMap<>();
	public static HashMap<Location, String> minenLocations = new HashMap<>();
	static ArrayList<Location> allMinenLocs = new ArrayList<>();
	
	public static String getMineKiller(Location deathLoc) {
		for (Location loc : minenLocations.keySet()) {
			if (loc.getWorld() == deathLoc.getWorld()) {
			if (deathLoc.distance(loc) <= 5) {
				return minenLocations.get(loc);
			}
			}
		}
		System.out.println("no");
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			Player p = e.getPlayer();
					if (e.getMaterial() == Material.STONE_PLATE) {
						if (e.getItem().getItemMeta().getDisplayName() != null) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWurfMine")) {
						if (p.hasPermission("wargame.weapon.wurfmine")) {
							if (p.getItemInHand().getAmount() == 1) {

								if (!Command_Infinitymode.infinitymode.contains(p))
									p.getInventory().remove(p.getItemInHand());
								
								Item plate = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.STONE_PLATE, 1));
								if (!p.isSneaking()) {
									plate.setVelocity(p.getLocation().getDirection().multiply(1));
								}else {
									plate.setVelocity(p.getLocation().getDirection().multiply(2));
								}
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
									
									@Override
									public void run() {
										if (plate.isOnGround()) {
											plate.getLocation().getBlock().setType(Material.STONE_PLATE);
											allMinenLocs.add(plate.getLocation());
											minenLocations.put(plate.getLocation().getBlock().getLocation(), p.getName());
											plate.remove();
										}else {
											Location loc = plate.getLocation();
											
											while (loc.subtract(0D, 1D, 0D).getBlock().getType() == Material.AIR) {
												loc = loc.subtract(0, 1, 0);
											}
											loc.add(0, 1, 0).getBlock().setType(Material.STONE_PLATE);
											minenLocations.put(plate.getLocation().getBlock().getLocation(), p.getName());
											allMinenLocs.add(plate.getLocation().getBlock().getLocation());
											plate.remove();
											
										}
										
									}
								}, (long)1*20);
							
								
							
								
								return;
							}else {
								if (!Command_Infinitymode.infinitymode.contains(p))
								p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
								
								
								Item plate = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.STONE_PLATE, 1));
								if (!p.isSneaking()) {
									plate.setVelocity(p.getLocation().getDirection().multiply(1D));
								}else {
									plate.setVelocity(p.getLocation().getDirection().multiply(2));
								}
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
									
									@Override
									public void run() {
										if (plate.isOnGround()) {
											minenLocations.put(plate.getLocation().getBlock().getLocation(), p.getName());
											allMinenLocs.add(plate.getLocation().getBlock().getLocation());
											plate.getLocation().getBlock().setType(Material.STONE_PLATE);
											plate.remove();
										}else {
											Location loc = plate.getLocation();
											
											while (loc.subtract(0D, 1D, 0D).getBlock().getType() == Material.AIR) {
												loc = loc.subtract(0, 1, 0);
											}
											loc.add(0, 1, 0).getBlock().setType(Material.STONE_PLATE);
											minenLocations.put(plate.getLocation().getBlock().getLocation(), p.getName());
											allMinenLocs.add(plate.getLocation().getBlock().getLocation());
											plate.remove();
											
										}
										
									}
								}, (long)1*20);
							
								
							
								
							}
							
						}else {
							p.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");
						}
					}
						}
					}
				
			
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().getBlock().getType() == Material.STONE_PLATE && allMinenLocs.contains(p.getLocation().getBlock().getLocation())) {
			if (!StartGame.spectators.contains(p))
				p.getWorld().createExplosion(p.getLocation(), (long)5, true);	
		}
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().getType() == Material.STONE_PLATE) {
			e.setCancelled(true);
			e.getItem().remove();
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getType() == Material.STONE_PLATE) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.Prefix+"§cDieses Item kannst du nicht droppen!");
			e.getPlayer().sendMessage(Main.Prefix+"§c» (Rechtsklick = plazieren, Linksklick = werfen)");
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		try {
			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lWurfMine")) {
				allMinenLocs.add(e.getBlock().getLocation());
				
			}
		}catch (NullPointerException ex) {
			
		}
	}

}
