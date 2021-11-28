package de.devofvictory.serverhelp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.devofvictory.serverhelp.main.Main;

public class Listener_OnCommand implements Listener{
	
	
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		Player p = e.getPlayer();
	
		if (Main.getInstance().getConfig().getStringList("Help.HelpCommands").contains(msg)) {
			e.setCancelled(true);
			for (int i = 1; i <= Main.getInstance().getConfig().getInt("Help.lines"); i++) {
				p.sendMessage(Main.getInstance().getConfig().getString("Help.line."+i).replace('&', '§'));
			}
		}
	}

}
