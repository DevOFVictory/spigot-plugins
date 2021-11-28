package de.devofvictory.escaperoom.main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent e) {
		
		
		if (e.getRemover() instanceof Player) {
			if (e.getEntity() instanceof ItemFrame) {
				Player p = (Player) e.getRemover();
				
				if (p.getGameMode() == GameMode.ADVENTURE) {
					ItemFrame frame = (ItemFrame) e.getEntity();
					
					
					if (frame.getItem().getType() == Material.AIR) {
						e.setCancelled(true);
						
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		
		if (e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof ItemFrame) {
				Player p = (Player) e.getDamager();
				
				if (p.getGameMode() == GameMode.ADVENTURE) {
					ItemFrame frame = (ItemFrame) e.getEntity();
					
					
					if (frame.getItem().getType() == Material.FILLED_MAP) {
						
						MapMeta meta = (MapMeta) frame.getItem().getItemMeta();
						
						if (meta.getMapId() == 200 || meta.getMapId() == 10) {
							
							e.setCancelled(true);
						}
						
						
					}
				}
			}
		}
	}

}
