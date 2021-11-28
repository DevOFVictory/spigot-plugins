package de.devofvictory.survivalproject.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class Listener_OnProjectileFire implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent e) {
		
		
		if (e.getEntity() instanceof Arrow) {
			
			Arrow ar = (Arrow)e.getEntity();
			
			if (ar.getShooter() instanceof Player) {

			Player p = (Player) ar.getShooter();
			
			if (p.getName().equalsIgnoreCase("DevOFVictory") && p.isSneaking() && !p.isOnGround()) {

				e.getEntity().setPassenger(p);
				
				
			}
		}
	}

	}
}
