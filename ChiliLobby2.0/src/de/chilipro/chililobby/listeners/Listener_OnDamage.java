package de.chilipro.chililobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Listener_OnDamage implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

}
