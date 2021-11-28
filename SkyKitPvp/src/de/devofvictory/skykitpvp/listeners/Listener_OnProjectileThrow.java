package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import de.devofvictory.skykitpvp.utils.OtherUtils;

public class Listener_OnProjectileThrow implements Listener{

	@EventHandler
	public void onProjectileThrow(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			
			Entity target = OtherUtils.getTargetEntity(p);
			
			if (target != null) {
				
				if (target.getType() == EntityType.WANDERING_TRADER || target.getType() == EntityType.WITCH || target.getType() == EntityType.ZOMBIE) {
					e.setCancelled(true);
				}
				
			}
		}
		
	}
	
}
