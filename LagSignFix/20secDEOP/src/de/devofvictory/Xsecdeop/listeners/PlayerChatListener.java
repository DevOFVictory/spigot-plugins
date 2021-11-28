package de.devofvictory.Xsecdeop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerChatListener implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		
		if(!message.equalsIgnoreCase("")){
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(player.isOp()) {
					if(PermissionsEx.getUser(player).inGroup("Owner")) {
					 {
						player.setOp(false);
					}
				}
				}
				}
				}
	}}
