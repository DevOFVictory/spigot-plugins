package de.devofvictory.skykitpvp.specialitems;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class CoinTracker implements Listener{
	
	public static String name = "§4§lCointracker";
	
	public static int price = 100;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		
		if (e.getAction() != Action.PHYSICAL) {
			
			if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals(name)) {
				
		
				Player p = e.getPlayer();
				
				ItemFrame nearest = OtherUtils.getNearestFrame(p, 100);
				
				if (nearest != null) {
					
					p.setCompassTarget(nearest.getLocation());
					
					p.sendMessage(Main.Prefix+"§aDer nächste Bilderrahmen ist in §2"+nearest.getLocation().distance(p.getLocation())+" §aBlöcken!");
				
				}
			
			}
	}
	}

}
