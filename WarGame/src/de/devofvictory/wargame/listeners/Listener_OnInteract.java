package de.devofvictory.wargame.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.wargame.main.Main;

public class Listener_OnInteract implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		

		if (!Main.isMatchRunning) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				
				try {
				if (e.getItem().getType() != Material.WRITTEN_BOOK) {
					e.setCancelled(true);
				}else {
					e.setCancelled(true);
				}
				}catch(NullPointerException ex) {
					e.setCancelled(true);
				}
			}else if (e.getAction() == Action.PHYSICAL) {

				e.setCancelled(true);
			}
		}
		try {
		if (e.getItem().getType() == Material.ENDER_PEARL || e.getItem().getType() == Material.FIREBALL || e.getItem().getType() == Material.SNOW_BALL) {
			e.setCancelled(true);
		}
		
		}catch (NullPointerException ex) {
			
		}
		
		
	}
	
	

}
