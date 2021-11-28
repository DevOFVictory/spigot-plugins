package de.devofvictory.skykitpvp.listeners;

import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.skykitpvp.commands.Command_Build;
import de.devofvictory.skykitpvp.invs.DalyLootChest;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.Interactable;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.PHYSICAL) {
			if (!Command_Build.buildMode.contains(e.getPlayer())) {
				e.setCancelled(true);
			}
		} else {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.BONE_MEAL || e.getItem().getType() == Material.FIREWORK_ROCKET
						|| e.getItem().getType() == Material.ENDER_EYE) {
					if (!Command_Build.buildMode.contains(e.getPlayer()))
						e.setCancelled(true);
				} else if (e.getItem().getType() == Material.ENDER_PEARL) {
					if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()
							&& !e.getItem().getItemMeta().getDisplayName().equals("§4§lEnderperle")) {
						e.setCancelled(true);
					}
				}
			}
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (!Command_Build.buildMode.contains(e.getPlayer())) {
				if (Interactable.contains(e.getClickedBlock().getType())) {
					e.setCancelled(true);
				}
			}

			if (e.getClickedBlock().getLocation().equals(Variables.dailyloot_location)) {
				if (DalyLootChest.lastOpen.containsKey(p)) {
					if (DalyLootChest.lastOpen.get(p)+Variables.dailyloot_cooldown  <= System.currentTimeMillis()) {
						DalyLootChest.lastOpen.put(p, System.currentTimeMillis());
						DalyLootChest.call(p);
					}else {
						long currentTime = System.currentTimeMillis();
						long last = DalyLootChest.lastOpen.get(p);
						
						long time = TimeUnit.MILLISECONDS.toSeconds(currentTime - last);
				        long days = TimeUnit.SECONDS.toDays(time);
				        time -= days * 24L * 60L * 60L;
				        long hours = TimeUnit.SECONDS.toHours(time);
				        time -= hours * 60L * 60L;
				        long minutes = TimeUnit.SECONDS.toMinutes(time);
				        time -= minutes * 60L;
				        long seconds = time;
						
						p.sendMessage(Main.Prefix+"§cBitte warte noch §6"+(24-hours-1)+" Stunden§c, §6"+(60-minutes-1)+" Minuten §cund §6"+(60-seconds-1)+" Sekunden§c, bis du das nächste mal deine Belohnung abholen kannst!");
					}
				}else {
					
					
					
					DalyLootChest.call(p);
					DalyLootChest.lastOpen.put(p, System.currentTimeMillis());
				}
				
			}
		}

	}

}
