package de.devofvictory.pvpzone.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.devofvictory.pvpzone.utils.OtherUtils;
import de.devofvictory.pvpzone.utils.Variables;

public class Listener_OnPvp implements Listener{
	
	@EventHandler
	public void onDamgeByEntity(EntityDamageByEntityEvent e) {
		if (OtherUtils.isInRegion(e.getEntity().getLocation(), Variables.areaLoc1, Variables.areaLoc2) &&
				OtherUtils.isInRegion(e.getDamager().getLocation(), Variables.areaLoc1, Variables.areaLoc2)) {
			if (e.getEntity() instanceof Player) {
				if (e.getDamager() instanceof Player) {
					Player p = (Player) e.getEntity();
					Player damager = (Player) e.getDamager();
					
					OtherUtils.sendActionbar(p, "§cIn fight with §6"+damager.getName()+" (§c"+OtherUtils.roundAvoid(damager.getHealth()/2, 2)+" §4❤§6)");
					
					OtherUtils.sendActionbar(damager, "§cIn fight with §6"+p.getName()+" (§c"+OtherUtils.roundAvoid(p.getHealth()/2, 2)+" §4❤§6)");
				}
			}
		
		}else {
			e.setCancelled(true);
		}
	}

}
