package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnDamage implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			if (e.getCause() == DamageCause.FALL) {
				if (!Variables.falldamage) {
					e.setCancelled(true);
				}
			}
		}
	}

}
