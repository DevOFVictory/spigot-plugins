package de.chilipro.chililobby.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnKnockEnter implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.getPlayer().getLocation().getZ() > 23 && e.getPlayer().getLocation().getZ() < 30) {
			if(!Main.allowBuild.contains(e.getPlayer().getName())) {
				if(e.getPlayer().isOnGround()) {
					Main.connectToServer(e.getPlayer(), "knock");
				}
			}
		}
		
		if (e.getPlayer().getLocation().getZ() < -21 && e.getPlayer().getLocation().getZ() > -30) {
			if(!Main.allowBuild.contains(e.getPlayer().getName())) {
				if(e.getPlayer().isOnGround()) {
					Location loc = new Location(e.getPlayer().getWorld(), -999, 100, -1000);
					e.getPlayer().teleport(loc);
				}
			}
			
		}
		
	}

}
