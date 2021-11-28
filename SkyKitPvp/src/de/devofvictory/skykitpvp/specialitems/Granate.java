package de.devofvictory.skykitpvp.specialitems;

import org.bukkit.Sound;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import de.devofvictory.skykitpvp.utils.OtherUtils;

public class Granate implements Listener{
	
	public static String name = "§4§lGranate";
	public static int price = 100;
	
	@EventHandler
	public void onInteract(ProjectileHitEvent e) {
		
		if (e.getEntity() instanceof Snowball) {
			
			if (!OtherUtils.isSpawn(e.getEntity().getLocation())) {
			
				e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 3, false);
				e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
				
			}
		}
		
	}
	

}
