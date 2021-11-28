package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnMove implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		
		if (e.getPlayer().getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(Variables.jumpPadPower).setY(1));
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
		}
		
		if (e.getPlayer().getLocation().getY() <= Variables.deathHeight) {
			EntityDamageEvent event = new EntityDamageEvent(e.getPlayer(), DamageCause.VOID, 1);
			e.getPlayer().setLastDamageCause(event);
			e.getPlayer().setHealth(0);
		}
	}

}
