package de.devofvictory.wargame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.StartGame;

public class Listener_OnHungerChange {
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e) {
		if (StartGame.spectators.contains(e.getEntity()) || !Main.isGameRunning) {
			e.setCancelled(true);
		}
	}

}
