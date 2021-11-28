package de.devofvictory.biomicploz.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;

public class Listener_OnBlockPhysics implements Listener{
	
	 @EventHandler
	 public void onBlockFromTo(BlockFromToEvent event) {
        Block block = event.getToBlock();
        
        if (block.getLocation().getWorld().getName().equals("plotworld")) {
        
        Plot plot = PlotManager.getPlot(block.getLocation());
        
        	if (plot != null) {
        		
        
            if (plot.isWallLocation(block.getLocation()) && (block.getType() == Material.WATER || block.getType() == Material.AIR || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
               event.setCancelled(true);
            }
	
        	}else {
        		event.setCancelled(true);
        	}
        }
        
	}
	 
	 @EventHandler
	 public void onPistonExtend(BlockPistonExtendEvent e) {
		 
		 Plot plot = PlotManager.getPlot(e.getBlock().getLocation());
		 
		 if (plot != null) {
				 for (Block blocks : e.getBlocks()) {
					 if (plot.isWallLocation(blocks.getLocation())) {
						 e.setCancelled(true);
					 }
				 }
		 }else {
			 e.setCancelled(true);
		 }
		 
	 }
	 
	 @EventHandler
	 public void onPistonRetract(BlockPistonRetractEvent e) {
		 
		 Plot plot = PlotManager.getPlot(e.getBlock().getLocation());
		 
		 if (plot != null) {
				 for (Block blocks : e.getBlocks()) {
					 if (!plot.isLocOnPlot(blocks.getLocation())) {
						 e.setCancelled(true);
					 }
				 }
		 }else {
			 e.setCancelled(true);
		 }
		 
	 }

}
