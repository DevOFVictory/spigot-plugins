package de.devofvictory.biomicploz.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Utils;

public class Listener_OnMark implements Listener{
	
	public static HashMap<Player, Location> loc1 = new HashMap<>();
	public static HashMap<Player, Location> loc2 = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		try {
			
			if (e.getItem().getType() == Material.GOLD_AXE && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e•§f●§e§lZauberaxt")) {
				
				e.setCancelled(true);
				
				Location loc = e.getClickedBlock().getLocation();
				
				if (loc.getWorld().getName().equals("plotworld")) {
					
					if (PlotManager.getPlot(loc) == null) {
					
					if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
						
						if (loc1.containsKey(p))
							loc1.get(p).getBlock().getState().update();
						
						loc1.put(p, loc);
						Utils.sendNoSpamMsg(p, Main.Prefix+"§aErste Position gesetzt! §8(§e" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()+"§8)");
						Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								
								p.sendBlockChange(e.getClickedBlock().getLocation(), Material.GLOWSTONE, (byte)0);
								
							}
						}, 1);
						
					}else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						
						if (loc2.containsKey(p))
							loc2.get(p).getBlock().getState().update();
						
						loc2.put(p, loc);
						Utils.sendNoSpamMsg(p, Main.Prefix+"§aZweite Position gesetzt! §8(§e" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()+"§8)");
						
						Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								
								p.sendBlockChange(e.getClickedBlock().getLocation(), Material.GLOWSTONE, (byte)0);
								
							}
						}, 1);
					}
					
					}else {
						Utils.sendNoSpamMsg(p, Main.Prefix+"§cDieses Grundstück gehört bereits §6"+Bukkit.getOfflinePlayer(PlotManager.getPlot(loc).getOwner()).getName()+"§c!");
					}
			}else {
				Utils.sendNoSpamMsg(p, Main.Prefix+"§cIn dieser Welt kannst du kein Grundstück claimen!");
			}
			}
			
		}catch (NullPointerException ex){
			
		}
	}

}
