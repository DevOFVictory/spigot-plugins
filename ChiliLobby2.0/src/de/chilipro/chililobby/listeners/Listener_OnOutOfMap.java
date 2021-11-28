package de.chilipro.chililobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnOutOfMap implements Listener{
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {
		if(!Main.allowBuild.contains(e.getPlayer().getName())) {
			if(e.getPlayer().getLocation().getY() == 85) {
				e.getPlayer().performCommand("spawn");
			}
		}
	}

}
