package de.devofvictory.cm.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.devofvictory.cm.main.Main;

public class OnChat implements Listener{
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
	Player p = e.getPlayer();
	String msg = e.getMessage().replace('&', '§');
	
	if(msg.contains("@Team")) {
		e.setCancelled(true);
		if (p.hasPermission("chatmanager.teamchat.send")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (all.hasPermission("chatmanager.teamchat.read")) {
					all.sendMessage(Main.TeamChatPrefix +"§c"+ p.getName() + " §8>> §e"+msg);
				}
			}
			
		} else {p.sendMessage(Main.Prefix+"§cDu hast keine Rechte in den TeamChat zu schreiben!"); 
		}
		}else {
			
			if(Main.globalMute) {
				if(!p.hasPermission("chatmanager.globalmute.bypass")) {
					e.setCancelled(true);
					p.sendMessage("§eDer Chat ist momentan stumm geschaltet! Bitte versuche es später erneut!");
				}
			}
			
		}
	
	}
	
	}

