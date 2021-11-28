package de.devofvictory.survivalproject.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.devofvictory.survivalproject.main.Main;

public class Command_Ride implements CommandExecutor, Listener{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player)sender;
			
			if (p.getNearbyEntities(3, 3, 3).size() >= 1) {
				for (Entity entity : p.getNearbyEntities(3, 3, 3)) {
					entity.setPassenger(p);
					p.sendMessage(Main.Prefix+"§aDu reitest nun ein(e) "+entity.getType().getName());
					break;
				}
				
			}else {
				p.sendMessage(Main.Prefix+"§cKein reitbares Tier gefunden!");
			}
			
		}
			
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(EntityDamageByEntityEvent e) {
		
		try {
		
		if (e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			
			if (p.getPassenger() != null && p.isSneaking()) {
				Player rider = (Player) p.getPassenger();
				
				rider.teleport(rider.getLocation().add(0,0.5,0));
				rider.setVelocity(p.getLocation().getDirection().multiply(5));
				
				
			}
			
			
		}
		}catch (NullPointerException ex) {
			
		}
	}

}
