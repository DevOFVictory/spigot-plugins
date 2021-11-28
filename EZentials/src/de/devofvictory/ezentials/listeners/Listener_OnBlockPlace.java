package de.devofvictory.ezentials.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.main.Main;

public class Listener_OnBlockPlace implements Listener{
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		Block block = e.getBlock();
		
		Player p = e.getPlayer();
		
		if (p.hasMetadata("nobuild")) {
			e.setCancelled(true);
		}
		if (p.hasMetadata("freezed")) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cDu bist eingefroren!");
		}
		
		if (block.getType() == Material.GOLD_PLATE) {
			if (block.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.REDSTONE_BLOCK) {
				if (!p.hasPermission("ezentials.jumppad.create")) {
					e.setCancelled(true);
					p.sendMessage(Main.noPerms("ezentials.jumppad.create"));
				}else {
					p.sendMessage(Main.Prefix+"§eJ§6u§cm§4p§aP§2a§bd §aerfolgreich gesetzt!");
				}
			}
		}
		if (Command_AFK.afk.contains(p)) {
			Command_AFK.removeAfk(p);
		}
		
	}

}
