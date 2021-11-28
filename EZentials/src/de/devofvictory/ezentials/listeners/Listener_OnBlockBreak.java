package de.devofvictory.ezentials.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.main.Main;

public class Listener_OnBlockBreak implements Listener{
	
	@EventHandler
	public void Listener_BlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (p.hasMetadata("nobuild")) {
			e.setCancelled(true);
			
		}
		if (p.hasMetadata("freezed")) {
			e.setCancelled(true);
			p.sendMessage(Main.Prefix+"§cDu bist eingefroren!");
		
		}
		Block block = e.getBlock();
		
		
		
		
		if (block.getType() == Material.GOLD_PLATE) {
			if (block.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.REDSTONE_BLOCK) {
				if (!p.hasPermission("ezentials.jumppad.delete")) {
					e.setCancelled(true);
					p.sendMessage(Main.noPerms("ezentials.jumppad.delete"));
				}else {
					p.sendMessage(Main.Prefix+"§eJ§6u§cm§4p§aP§2a§bd §cerfolgreich zerstört!");
				}
			}
		}
		if (block.getType() == Material.REDSTONE_BLOCK) {
			if (block.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.GOLD_PLATE) {
				if (!p.hasPermission("ezentials.jumppad.delete")) {
					e.setCancelled(true);
					p.sendMessage(Main.noPerms("ezentials.jumppad.delete"));
				}else {
					p.sendMessage(Main.Prefix+"§eJ§6u§cm§4p§aP§2a§bd §cerfolgreich zerstört!");
				}
			}
		}
		if (Command_AFK.afk.contains(p)) {
			Command_AFK.removeAfk(p);
		}
	}
	
	

}
