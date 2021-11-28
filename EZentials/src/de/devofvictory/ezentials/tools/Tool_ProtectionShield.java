package de.devofvictory.ezentials.tools;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.devofvictory.ezentials.main.Main;

public class Tool_ProtectionShield implements Listener{
	
	HashMap<Player, BukkitRunnable> run = new HashMap<>();
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.hasPermission("ezentials.protectionshild.use")) {
			if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getType() != Material.AIR) {
			if (e.getItem().getType() == Material.EYE_OF_ENDER && e.getItem().getItemMeta().getDisplayName() != null) {
				if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lSchutzschild")) {
				
					
						e.setCancelled(true);
						if (run.containsKey(p)) {
							p.sendMessage(Main.Prefix+"§4Schutzschild deaktiviert!");
							run.get(p).cancel();
							run.remove(p);
						}else if (!run.containsKey(p)){
							run.put(p, new BukkitRunnable() {
								
								@Override
								public void run() {
									p.getWorld().playEffect(p.getLocation().add(0.0D, 1.0D, 0.0D), Effect.ENDER_SIGNAL, 100);
									
								}
							});
							run.get(p).runTaskTimer(Main.instance(), 20, 20);
							p.sendMessage(Main.Prefix+"§2Schutzschild aktiviert!");
						}
				}	
			}
			}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		if (run.containsKey(e.getPlayer())) {
			Player p = e.getPlayer();
			
			run.get(p).cancel();
			run.remove(p);
			
			
		}
	}
	
	@EventHandler 
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		for(Player players : run.keySet()) {
			if (p != players && !p.hasPermission("ezentials.protectionshild.bypass")) {
			if(p.getLocation().distance(players.getLocation()) <= 5) {
				
				double Ax = p.getLocation().getX();
				double Ay = p.getLocation().getY();
				double Az = p.getLocation().getZ();
				
				double Bx = players.getLocation().getX();
				double By = players.getLocation().getY();
				double Bz = players.getLocation().getZ();
				
				double x = Ax - Bx;
				double y = Ay - By;
				double z = Az - Bz;
				
				Vector v = new Vector(x, y, z).normalize().multiply(1.25D).setY(0.5D);
				p.setVelocity(v);
			}
		}
		}
		if (run.containsKey(p)) {
			for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
				if (entity instanceof Player) {
					Player target = (Player) entity;
					
					if (p != target && !target.hasPermission("ezentials.protectionshild.bypass")) {
					
					double Ax = p.getLocation().getX();
					double Ay = p.getLocation().getY();
					double Az = p.getLocation().getZ();
					
					double Bx = target.getLocation().getX();
					double By = target.getLocation().getY();
					double Bz = target.getLocation().getZ();
					
					double x = Bx - Ax; 
					double y = By - Ay;
					double z = Bz - Az;
					
					Vector v = new Vector(x, y, z).normalize().multiply(1.25D).setY(0.5D);
					target.setVelocity(v);
				}
			}
		}
	}
	}
	

}
