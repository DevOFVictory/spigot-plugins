package de.devofvictory.pvpzone.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.pvpzone.main.PvPZone;
import de.devofvictory.pvpzone.utils.Variables;

public class Listener_OnBla implements Listener{
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e ) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			e.getDrops().clear();
			Player p = e.getEntity();
			p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
			p.getLocation().getWorld().strikeLightning(p.getLocation().clone().add(0,1,0));
			p.getLocation().getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 100);
			p.spigot().respawn();
			if (Variables.spawn != null)
				p.teleport(Variables.spawn);
			p.setGameMode(GameMode.SPECTATOR);
			if (p.getKiller() != null && p.getKiller() instanceof Player) {
				p.sendTitle("§4§lx §c§lEliminated §4§lx", "§eby "+p.getKiller().getName());
			}else {
				p.sendTitle("§4§lx §c§lEliminated §4§lx", "");
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(PvPZone.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					p.setGameMode(GameMode.SURVIVAL);
					p.sendTitle("§aRespawning...", "");

					
					if (Variables.spawn != null) {
						p.teleport(Variables.spawn);
						p.getInventory().clear();
						p.getInventory().setItem(1, new ItemStack(Material.DIAMOND_SWORD));
						p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
						p.getInventory().setItem(8, new ItemStack(Material.BOOK));
						p.getInventory().setItem(7, new ItemStack(Material.WATCH));
						p.getInventory().setItem(6, new ItemStack(Material.NAME_TAG));
					}
				}
			}, 3*20);
			
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			
			new BukkitRunnable() {
				int count = 0;
				Location loc = e.getBlock().getLocation();
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					switch (count) {
					case 0:
						loc.getBlock().setType(Material.WOOL);
						loc.getBlock().setData((byte)5);
						break;
					case 1:
						loc.getBlock().setType(Material.WOOL);
						loc.getBlock().setData((byte)4);
						break;
					
					case 2:
						loc.getBlock().setType(Material.WOOL);
						loc.getBlock().setData((byte)14);
						break;
					case 3:
						loc.getBlock().setType(Material.AIR);
						break;
					default:
						count = 0;
						cancel();
						break;
					}
					
					count++;
				}
			}.runTaskTimer(PvPZone.getPlugin(), 0, 2*20);
		}
	}
	
	@EventHandler
	public void onFallDamge(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			e.setCancelled(true);
		}
	}
	
	

}
