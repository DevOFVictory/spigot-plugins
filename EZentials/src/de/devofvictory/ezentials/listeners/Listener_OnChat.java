package de.devofvictory.ezentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.main.Main;

public class Listener_OnChat implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if (p.hasMetadata("freezed")) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cDu bist eingefroren!");
		}
		
		if (Command_AFK.afk.contains(p)) {
			Command_AFK.removeAfk(p);
		}
		Main.afkTime.put(p, 0);
		for (Player afk : Command_AFK.afk) {
			if (msg.contains(afk.getName())) {
				
					if (Command_AFK.afkMessages.get(afk) != null) {
						p.sendMessage(Main.afkPrefix+" §6"+afk.getName()+" §eist momentan AFK und antwortet wahrscheinlich erst später. §c(AFK seid "+Main.afkTime.get(afk)/60+" Minuten §8>> §7"+Command_AFK.afkMessages.get(afk)+"§c)");
					}else {
						p.sendMessage(Main.afkPrefix+" §6"+afk.getName()+" §eist momentan AFK und antwortet wahrscheinlich erst später. §c(AFK seid "+Main.afkTime.get(afk)/60+" Minuten)");
					}
					
			
		}
		}
		
	}

}
