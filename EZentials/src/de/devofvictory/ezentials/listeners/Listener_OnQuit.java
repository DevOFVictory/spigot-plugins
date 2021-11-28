package de.devofvictory.ezentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.main.Main;

public class Listener_OnQuit implements Listener{
	
	@EventHandler
	
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Command_AFK.afkMessages.remove(p);
		Main.afkTime.put(p, 0);
		Command_AFK.afk.remove(p);
		
		for (World world : Bukkit.getWorlds()) {
			for (Entity entities : world.getEntities()) {
				if (entities.hasMetadata(p.getName())) {
					entities.remove();
				}
			}
			}
	}
}
