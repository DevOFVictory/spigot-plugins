package de.chilipro.chililobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnBuild implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (!Main.allowBuild.contains(p.getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (!Main.allowBuild.contains(p.getName())) {
			e.setCancelled(true);
		}
	}

}
