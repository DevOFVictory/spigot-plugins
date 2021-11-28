package de.devofvictory.biomicploz.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.devofvictory.biomicploz.commands.Command_Build;
import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Utils;

public class Listener_OnMove implements Listener{ 
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Plot plot = PlotManager.getPlot(e.getPlayer().getLocation());
		
		if (plot != null && !Command_Build.powermode.contains(e.getPlayer())) {
			if (plot.getDenied().contains(e.getPlayer().getUniqueId())) {
				
				Vector center = plot.getMiddle().toVector();
				Vector toThrow = e.getPlayer().getLocation().toVector();
				       
				double x = toThrow.getX() - center.getX();
				double z = toThrow.getZ() - center.getZ();

				Vector v = new Vector (x, 4, z).normalize();

				e.getPlayer().setVelocity(v);
				Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix+"§cDu darfst dieses Grundstück nicht betreten!");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 10, 10);
			}
		}
	}
	

}
