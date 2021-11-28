package de.devofvictory.ezentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.devofvictory.ezentials.main.Main;

public class Listener_OnLogin implements Listener{
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (Main.maintenance && !p.hasPermission("ezentials.maintenance.bypass")) {
			if (Main.maintenanceMessage.equalsIgnoreCase("none")) {
				e.disallow(Result.KICK_OTHER, "§c§lDerzeit sind Wartungen auf unserem Server. Versuche es später erneut!");
			}else {
				e.disallow(Result.KICK_OTHER, "§c§lDerzeit sind Wartungen auf unserem Server. Versuche es später erneut! §8>> §f"+Main.maintenanceMessage);
			}
		}
		
		
	}

}
