package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.items.LaubBlaeser;
import de.devofvictory.wargame.main.Main;

public class Listener_OnDamage implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!Main.allowDamage)
				e.setCancelled(true);
		}else if (e.getEntity() instanceof Chicken) {
			e.getEntity().setVelocity(new Vector());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
            {
              public void run() {
            	  e.getEntity().setVelocity(new Vector());
              }
            }
            , 1L);
			
		}
		
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (LaubBlaeser.noDamage.contains(p)) {
					e.setCancelled(true);
				}
			}
		}
		
		if (e.getEntity() instanceof Villager) {
			e.setCancelled(true);
		}
		
		
		if (e.getCause() == DamageCause.SUFFOCATION && !Listener_OnBuildOutsideBorder.isOutsideOfBorder(e.getEntity().getLocation().getBlock().getLocation())) {
			e.setCancelled(true);
		}
		
		
		if (e.getEntity() instanceof Player && Main.isMatchRunning && !e.isCancelled()) {
			Player p = (Player) e.getEntity();
			p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		}
		
		
	     
	}
	
	
	

}
