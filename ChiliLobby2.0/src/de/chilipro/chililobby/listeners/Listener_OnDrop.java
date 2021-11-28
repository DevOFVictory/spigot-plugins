package de.chilipro.chililobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnDrop implements Listener{
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!Main.allowBuild.contains(e.getPlayer().getName()))
			e.setCancelled(true);
	}

}
