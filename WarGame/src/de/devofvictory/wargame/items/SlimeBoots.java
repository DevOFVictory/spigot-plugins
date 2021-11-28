package de.devofvictory.wargame.items;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;

public class SlimeBoots implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if (e.getCause() == DamageCause.FALL) {
				
			
			
			try {
				if (p.getInventory().getBoots().getType() == Material.GOLD_BOOTS && p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSlimeBoots")) {
					
					
					if (p.getInventory().getBoots().getDurability()+5 <= 90) {
						
						p.setVelocity(new Vector(0,p.getFallDistance()/18,0));
						p.getWorld().playEffect(p.getLocation(), Effect.SLIME, 10);
						p.playSound(p.getLocation(), Sound.SLIME_WALK, 10, 1);
						
						
						
						
						e.setCancelled(true);
						
						if (!LaubBlaeser.noDamage.contains(p) && !Command_Infinitymode.infinitymode.contains(p)) 
							p.getInventory().getBoots().setDurability((short) (p.getInventory().getBoots().getDurability()+5));
						
						if (p.getInventory().getBoots().getDurability() == 90) {
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							p.sendMessage(Main.Prefix+"§4Du bekommst nun wieder Fallschaden!");
						}
							
						
					}
					
					
				}
			}catch (NullPointerException ex) {
				
			}
		}
		}
	}

}
