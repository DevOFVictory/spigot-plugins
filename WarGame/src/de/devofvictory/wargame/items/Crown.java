package de.devofvictory.wargame.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;

public class Crown implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			try {
				if (p.getInventory().getHelmet().getType() == Material.GOLD_HELMET && p.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lCrown")) {
					
					e.setCancelled(true);
					
					if (e.getCause() == DamageCause.FALL && !Command_Infinitymode.infinitymode.contains(p)) {
						if (!p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSlimeBoots")) {
							p.getInventory().setHelmet(null);
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							p.sendMessage(Main.Prefix+"§4Du bist nun wieder verwundbar!");
						}
					}else {
						if (!Command_Infinitymode.infinitymode.contains(p)) {
							p.getInventory().setHelmet(null);
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							p.sendMessage(Main.Prefix+"§4Du bist nun wieder verwundbar!");
						}
					}
					
					
				}
			}catch (NullPointerException ex) {
				
			}
		}
	}

}
