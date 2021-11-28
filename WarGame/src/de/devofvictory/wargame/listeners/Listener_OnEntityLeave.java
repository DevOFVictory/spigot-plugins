package de.devofvictory.wargame.listeners;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

import de.devofvictory.wargame.items.ChickenWing;
import de.devofvictory.wargame.utils.StartGame;

public class Listener_OnEntityLeave implements Listener{
	
	@EventHandler
	public void onDisamount(EntityDismountEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			e.getDismounted().remove();
		
		if (e.getDismounted() instanceof Chicken) {
			if (ChickenWing.chickenwings.containsKey(p.getName())) {
					ChickenWing.chickenwings.get(p.getName()).remove();
					ChickenWing.chickenwings.remove(p.getName());
				
		}
			if (StartGame.ride.get(p.getName()) != null) {
				if (StartGame.ride.get(p.getName()).getPassenger() == null) {
					StartGame.ride.get(p.getName()).remove();
				}
		}
	}
	}
	}
}
