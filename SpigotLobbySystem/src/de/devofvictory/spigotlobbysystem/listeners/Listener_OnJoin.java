package de.devofvictory.spigotlobbysystem.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.devofvictory.spigotlobbysystem.main.Main;
import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnJoin implements Listener{
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (Utils.isLobbySet()) {
			Utils.setInv(p);
		}else {
			if (p.hasPermission("lobby.setlobby")) {
				p.sendMessage(Main.Prefix+"§cThe lobby is not set a this moment! Set the lobby with §6/lobby setlobby§c!");
			}
		}
			
		
	}
	
	

}
