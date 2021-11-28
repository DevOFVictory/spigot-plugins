package de.devofvictory.biomicploz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Listener_OnDebugChatMsg implements Listener{
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.getMessage().startsWith("*debug")) {
			e.setCancelled(true);
			
//			
//			String[] args = e.getMessage().split(" ");
//			int a = Integer.parseInt(args[1]);
//			int b = Integer.parseInt(args[2]);
//			
//			int dif = Utils.getDifference(a, b);
//			
//			e.getPlayer().sendMessage(Main.Prefix+"Die Differenz aus "+a+" und "+b+" ist "+dif+"!");
			
		}
	}
	
	
	

}
