package de.devofvictory.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

	public class SignListener implements Listener {
		
		@EventHandler
		public void onSignChange(SignChangeEvent e) {
			
			if (e.getPlayer().hasPermission("colored2000.sign.use")) {
				if(e.getLine(0) !=null && e.getLine(0) !="") {
					e.setLine(0, e.getLine(0).replace('&', '§'));
					}
					}
				
			if (e.getPlayer().hasPermission("colored2000.sign.use")) {
				if(e.getLine(1) !=null && e.getLine(1) !="") {
					e.setLine(1, e.getLine(1).replace('&', '§'));
					}
					}
					
			if (e.getPlayer().hasPermission("colored2000.sign.use")) {
				if(e.getLine(2) !=null && e.getLine(2) !="") {
					e.setLine(2, e.getLine(2).replace('&', '§'));
					}
					}
						
			if (e.getPlayer().hasPermission("colored2000.sign.use")) {
				if(e.getLine(3) !=null && e.getLine(3) !="") {
					e.setLine(3, e.getLine(3).replace('&', '§'));
					}
					}
			}
			
		}
		
