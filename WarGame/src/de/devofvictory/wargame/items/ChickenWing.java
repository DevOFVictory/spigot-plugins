package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.main.Main;

public class ChickenWing implements Listener{
	
	public static HashMap<String, Entity> chickenwings = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			try {
				Player p = e.getPlayer();
				if (e.getItem().getType() == Material.FEATHER && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lChickenWing")) {
				if (p.hasPermission("wargame.weapon.chickenwing")) {
					
						if (!p.isOnGround()) {
							
							if (!chickenwings.containsKey(p.getName())) {
								Entity en = p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
								en.setPassenger((Entity)p);
								chickenwings.put(p.getName(), en);
								
								if (e.getItem().getAmount() == 1) {
									p.getInventory().removeItem(e.getItem());
									
								}else {
									e.getItem().setAmount(e.getItem().getAmount()-1);
								}
							}
						}else {
							p.sendMessage(Main.Prefix+"§cDieses Item kann nur in der Luft eingesetzt werden");
						}
					
				}else {
					p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
				}
				}
			}catch (NullPointerException ex) {
				
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		
		
		if (Main.isGameRunning && chickenwings.containsKey(p.getName())) {
			
			
			Entity c = chickenwings.get(p.getName());
		if (!c.isOnGround()) {
			
			
			if (chickenwings.get(p.getName()).getPassenger() == p && p.getLocation().getPitch() >= 0) {
				
			
				c.setVelocity(p.getLocation().getDirection().divide(new Vector(5, 5, 5)));
			
			}else {

				c.setVelocity(p.getLocation().getDirection().divide(new Vector(5, 5, 5)).setY(0D));
				
			}
		
		}else {
			c.remove();
			if (c.getPassenger() == p)
				p.teleport(p.getLocation().add(0, 1, 0));
		}
	}
	
	
	
	}
	

}
