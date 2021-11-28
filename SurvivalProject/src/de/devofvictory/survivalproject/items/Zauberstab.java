package de.devofvictory.survivalproject.items;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.survivalproject.main.Main;

public class Zauberstab implements Listener{
	
	public static HashMap<Player, Long> lastUsed = new HashMap<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			try {
				
				if (e.getItem().getType() == Material.BLAZE_ROD && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lZauberstab")) {
					e.setCancelled(true);
					
					if (lastUsed.get(e.getPlayer()).longValue() + 20*1000 <= System.currentTimeMillis()) {
					
						Location loc = e.getPlayer().getTargetBlock(null, 50).getLocation();
						loc.add(0,1,0).getBlock().setType(Material.FIRE);
						loc.getWorld().strikeLightning(loc);
						
						lastUsed.put(e.getPlayer(), System.currentTimeMillis());
					
				}else {
					long time = System.currentTimeMillis();
					long last = lastUsed.get(e.getPlayer());
					long remainingSec = ((time-last-20000)/1000)/-1;
					long remainingMili = ((time-last-20000)%1000)/-1/10;
					
					e.getPlayer().sendMessage(Main.Prefix+"§cBitte warte noch "+remainingSec+"."+remainingMili+" Sekunden!");
				}
				
				}
			}catch (NullPointerException ex){
				
			}
		}
	}

}
