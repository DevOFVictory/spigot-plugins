package de.devofvictory.cm.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.devofvictory.cm.main.Main;

public class OnCommandListener implements Listener {
	
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all != p) {
			if (all.hasMetadata("cmdspy") && !p.hasPermission("chatmanager.cmdspy.bypass")) {
				
				if (msg.startsWith("/r ") || msg.startsWith("/msg ")) {
					if (p.hasPermission("chatmanager.cmdspy.invisiblemsgs")) {
						
					}else {
						if (all.hasPermission("chatmanager.cmdspy.seemsgs")) {
							all.sendMessage(Main.CmdSpyPrefix+"§e"+p.getName()+" §8-> §c"+msg);

						}
					}
					
					
				}else {
					all.sendMessage(Main.CmdSpyPrefix+"§e"+p.getName()+" §8-> §c"+msg);
				}
			}
		
			}
		
		
		}
		
		
		}}
