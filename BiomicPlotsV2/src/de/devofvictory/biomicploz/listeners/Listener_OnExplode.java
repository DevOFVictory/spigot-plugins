package de.devofvictory.biomicploz.listeners;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;

public class Listener_OnExplode implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {

		Iterator<Block> it = e.blockList().iterator();

		while (it.hasNext()) {

			Block block = it.next();

			Plot plot = PlotManager.getPlot(block.getLocation());

			if (plot != null) {

				if (plot.getFlagValue("explosions") != null) {
					if (plot.getFlagValue("explosions").equals("false")) {
						it.remove();
					}
				} else {
					it.remove();
				}
			} else {
				it.remove();
			}

		}

	}

}
