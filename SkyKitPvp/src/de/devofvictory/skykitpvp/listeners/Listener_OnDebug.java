package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class Listener_OnDebug implements Listener{
	
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if (e.getMessage().startsWith("*debug")) {
			Player p = e.getPlayer();
			
			if (p.hasPermission("skykitpvp.debug")) {
				
				float f = Float.parseFloat(e.getMessage().split(" ")[1]);
				
				p.setFlySpeed(f);
				
				
				e.setCancelled(true);
			}
		}
	}

}
