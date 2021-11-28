package de.devofvictory.wargame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;

public class Listener_OnCommand implements Listener{

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		
		if (p.hasPermission("wargame.ask") && Main.isGameRunning) {
			if (!Code.trusted.contains(p.getName())) {
				e.setCancelled(true);
				Code.askForCode(p);
				
			}
		}
	}
	
}
