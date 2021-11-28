package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.devofvictory.spigotlobbysystem.main.Main;
import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Listener_OnBlockBreak implements Listener {
	@EventHandler
	public void onBlockPlace(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		if (Utils.isLobby(p)) {
			if (!Utils.allowBuild.contains(p)) {
				e.setCancelled(true);
			}
		}
		if (p.hasPermission("lobby.setcompass")) {
			if (e.getBlock().getType() == Material.CHEST)
			if (p.isSneaking()) {
				if (p.getItemInHand().getType() != null) {
					if (p.getItemInHand().getType() == Material.COMPASS) {
						if (p.getItemInHand().getItemMeta().getDisplayName() != null) {
							if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lTeleporter")) {
								Block b = e.getBlock();
								Chest c = (Chest)b.getState();
								Main.getInstance().getConfig().set("CompassInv", c.getInventory());
								Main.getInstance().saveConfig();
								p.sendMessage(Main.Prefix+"§aSaved succsessfully!");
							}
						}
					}
				}
			}
		}
	
		
	}
	
	

}
