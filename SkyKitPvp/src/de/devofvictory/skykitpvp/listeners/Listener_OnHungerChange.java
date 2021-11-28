package de.devofvictory.skykitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Listener_OnHungerChange implements Listener{
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}

}
