package de.devofvictory.skykitpvp.listeners;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;

public class Listener_OnHanging_Break implements Listener{
	
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent e) {
		if (e.getEntity() instanceof ItemFrame) {
			if (e.getRemover() instanceof Player) {
				Player p = (Player) e.getRemover();
				
				if (!Command_Build.buildMode.contains(p)) {
					e.setCancelled(true);
				}
				
			}else {
				e.setCancelled(true);
			}
		}
	}

}
