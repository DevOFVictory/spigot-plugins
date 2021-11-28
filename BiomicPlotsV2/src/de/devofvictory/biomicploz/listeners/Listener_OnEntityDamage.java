package de.devofvictory.biomicploz.listeners;

import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.devofvictory.biomicploz.commands.Command_Build;
import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Utils;

public class Listener_OnEntityDamage implements Listener{
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		
		if (Command_Build.powermode.contains(e.getDamager()))
			return;
		
		if (e.getDamager() instanceof LightningStrike && e.getEntity().getWorld().getName().equalsIgnoreCase("plotworld"))
			e.setCancelled(true);
		
		if (e.getDamager().getWorld() != Main.plotworld) {
			return;
		}
		
		try {
		
		if (e.getDamager() instanceof Player) {
			
			if (e.getEntity() instanceof Player) {
				
				Plot plot1 = PlotManager.getPlot(e.getEntity().getLocation());
				Plot plot2 = PlotManager.getPlot(e.getDamager().getLocation());
				
				Player damager = (Player)e.getDamager();
				
				if (!plot1.getFlagValue("pvp").equals("true") || !plot2.getFlagValue("pvp").equals("true")) {
					e.setCancelled(true);
					Utils.sendNoSpamMsg(damager, Main.Prefix+"§cPvP ist hier deaktiviert!");
				}
			}else {
				Plot plot1 = PlotManager.getPlot(e.getEntity().getLocation());
				Plot plot2 = PlotManager.getPlot(e.getDamager().getLocation());
				
				if (!plot1.getFlagValue("pve").equals("true") || !plot2.getFlagValue("pve").equals("true")) {
					e.setCancelled(true);
				}
			}
			
		}else {
			if (e.getDamager() instanceof Projectile) {
				Plot plot1 = PlotManager.getPlot(e.getEntity().getLocation());
				Plot plot2 = PlotManager.getPlot(e.getDamager().getLocation());
				
				Projectile pr = (Projectile)e.getDamager();
				Player damager = (Player) pr.getShooter();
				
				if (!plot1.getFlagValue("pvp").equals("true") || !plot2.getFlagValue("pvp").equals("true")) {
					e.setCancelled(true);
					Utils.sendNoSpamMsg(damager, Main.Prefix+"§cPvP ist hier deaktiviert!");
				}else {
					e.setCancelled(true);
					Utils.sendNoSpamMsg(damager, Main.Prefix+"§cPvP ist hier deaktiviert!");
				}
			}
		}
		
		}catch (NullPointerException ex) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player)e.getDamager();
				e.setCancelled(true);
				
				if (e.getEntity() instanceof Player) {
					Utils.sendNoSpamMsg(p, Main.Prefix+"§cPvP ist hier deaktiviert!");
				}else {
					Utils.sendNoSpamMsg(p, Main.Prefix+"§cPvE ist hier deaktiviert!");
				}
			}
		}
		
	}

}
