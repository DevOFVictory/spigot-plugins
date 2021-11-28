package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class Listener_OnEntityTarget implements Listener{
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (e.getEntity() instanceof Bee) {
			if (e.getTarget() instanceof Player) {
				Player p = (Player) e.getTarget();
			if (e.getEntity().getCustomName() != null) {
				if (e.getEntity().getCustomName().equals(p.getName())) {
					e.setCancelled(true);
				}
			}
		}
		}
	}

}
