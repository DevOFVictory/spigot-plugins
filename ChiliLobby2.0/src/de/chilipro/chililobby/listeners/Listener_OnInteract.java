package de.chilipro.chililobby.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.chilipro.chililobby.inventorys.Inv_Menu;
import de.chilipro.chililobby.main.Main;

public class Listener_OnInteract implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

		Block b = e.getClickedBlock();
		if (b.getType() == Material.WALL_SIGN) {
			Sign s = (Sign)b.getState();
			if (s.getLine(1).equalsIgnoreCase("§3§lWar§b§lGame")) {
				
				p.sendMessage(Main.Prefix+"§aDu wirst mit §3§lWar§b§lGame §averbunden...");
				
				Main.connectToServer(p, "WarGame");
				return;
			}
		}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (!Main.allowBuild.contains(p.getName()))
				e.setCancelled(true);
			
			try {
				String name = e.getItem().getItemMeta().getDisplayName();
				if (name.equalsIgnoreCase("§b§lMENU")) {
					Inv_Menu.callInventory(p);
				}
				
			}catch (NullPointerException ex) {
				return;
			}
				
				
			
		}
	}

}
