package de.chilipro.chililobby.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnHotBarChange implements Listener{
	
	@EventHandler
	public void onHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		
		
		
		if (!Main.allowBuild.contains(p.getName()))
			p.playSound(p.getLocation(), Sound.CLICK, 10, 10);
	}

}
