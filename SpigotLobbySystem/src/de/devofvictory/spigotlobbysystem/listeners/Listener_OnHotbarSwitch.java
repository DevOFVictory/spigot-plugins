package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnHotbarSwitch implements Listener{
	
	@EventHandler
	public void onHotbarSwitch(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (Utils.isLobby(p))
		p.playSound(p.getLocation(), Sound.CLICK, (float)0.1, 10);
		
	}

}
