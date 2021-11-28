package de.devofvictory.ezentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.main.Main;

public class Listener_OnCommand implements Listener{
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		Player p = e.getPlayer();
		if (p.hasMetadata("freezed")) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cDu bist eingefroren!");
		}
		if (msg.contains("ALL(NO)")) {
			if (p.hasPermission("ezentials.placeholder.all")) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (p != all) {
					e.setCancelled(true);
					Bukkit.dispatchCommand(p, msg.replace("/", "").replace("ALL(NO)", all.getName()));
					e.setCancelled(true);
			}
			}
			p.sendMessage(Main.Prefix+"§aIn dem Befehl wurde jedes ALL durch alle Spieler ersetzt!");
		}else {
			p.sendMessage(Main.noPerms("ezentials.placeholder.ALL"));
			e.setCancelled(true);
		}
	}else if (msg.contains("ALL(YES)")) {
		if (p.hasPermission("ezentials.placeholder.all")) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			
				e.setCancelled(true);
				Bukkit.dispatchCommand(p, msg.replace("/", "").replace("ALL(YES)", all.getName()));
				e.setCancelled(true);
		
		}
		p.sendMessage(Main.Prefix+"§aIn dem Befehl wurde jedes ALL durch alle Spieler ersetzt!");
	}else {
		p.sendMessage(Main.noPerms("ezentials.placeholder.ALL"));
		e.setCancelled(true);
	}
	}else if (msg.contains("ALL")) {
		if (p.hasPermission("ezentials.placeholder.all")) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cBenutze statt §eALL §aALL(YES/NO) §7<- Willst du auch dabei sein?");
		}
	}
		Main.afkTime.put(p, 0);
		if (Command_AFK.afk.contains(p) && !msg.contains("/afk")) {
			Command_AFK.removeAfk(p);
			
			
		}
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
