package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.devofvictory.spigotlobbysystem.main.Main;
import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnSneak implements Listener{
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		p.sendMessage("»»»»»»»»»»»»»»");
		p.sendMessage(Main.Prefix+"§fYaw: "+Main.getInstance().getConfig().getDouble("Lobby.YAW"));
		p.sendMessage(Main.Prefix+"§fPitch: "+Main.getInstance().getConfig().getDouble("Lobby.PITCH"));
		p.sendMessage("»»");
		p.sendMessage(Main.Prefix+"§fYaw: "+Utils.getSpawnYaw());
		p.sendMessage(Main.Prefix+"§fPitch: "+Utils.getSpawnPitch());
		p.sendMessage("»»»»»»»»»»»»»»");
		
		
	}
	

}
