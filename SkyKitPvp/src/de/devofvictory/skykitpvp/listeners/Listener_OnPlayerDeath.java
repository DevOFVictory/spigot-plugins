package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.devofvictory.skykitpvp.superpowers.BAT_DISGUISE;

public class Listener_OnPlayerDeath implements Listener{
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		e.setDroppedExp(0);
		e.setKeepInventory(true);
		
		e.getEntity().getInventory().clear();
		e.getEntity().setFireTicks(0);
		
		
		if (BAT_DISGUISE.bats.containsKey(e.getEntity()))
			BAT_DISGUISE.unDisguise(e.getEntity());
		
		
		
	}

}
