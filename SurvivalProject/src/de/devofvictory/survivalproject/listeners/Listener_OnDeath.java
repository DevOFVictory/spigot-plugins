package de.devofvictory.survivalproject.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.devofvictory.survivalproject.main.Main;

public class Listener_OnDeath implements Listener{
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Location loc = e.getEntity().getLocation();
		
		Bukkit.broadcastMessage(Main.Prefix+"§aTodeskoordinaten von "+e.getEntity().getName()+": §e"+loc.getBlockX() + " " + loc.getBlockY() + " " +loc.getBlockZ());
		
		Player p = e.getEntity();
		
		try {
		EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)p.getLastDamageCause();
		
			if (edbee.getDamager() instanceof LlamaSpit) {
				
				LlamaSpit lspit = (LlamaSpit) edbee.getDamager();
				if (lspit.getShooter() instanceof Player) {
					Player shooter = (Player)lspit.getShooter();
					e.setDeathMessage("§f"+p.getName()+" wurde von "+shooter.getName()+" zu Tode gespuckt.");
				}
				
				
			}
			
		}catch (NullPointerException | ClassCastException ex) {
			
		}
		
		
	}

}
