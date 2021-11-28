package de.devofvictory.pvpzone.listeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.pvpzone.utils.OtherUtils;
import de.devofvictory.pvpzone.utils.Variables;

public class Listener_OnMove implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().clone().getBlock().getType() == Material.GOLD_PLATE) {
			p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2).setY(1.2));
			p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 10);
	
		}
		
		if (Variables.areaLoc1 != null && !OtherUtils.isInRegion(e.getFrom(), Variables.areaLoc1, Variables.areaLoc2) &&
				OtherUtils.isInRegion(e.getTo(), Variables.areaLoc1, Variables.areaLoc2)) {
			
			// Region enter
			p.sendTitle("§cEntering Battlezone", "§ePay attention!");
			p.getInventory().setContents(Variables.kit);
		}
		
		if (Variables.areaLoc1 != null && OtherUtils.isInRegion(e.getFrom(), Variables.areaLoc1, Variables.areaLoc2) &&
				!OtherUtils.isInRegion(e.getTo(), Variables.areaLoc1, Variables.areaLoc2)) {
			// Region leave
			p.sendTitle("§2Leaving Battlezone", "§aHere you are save!");
			p.getInventory().clear();
			p.getInventory().setItem(1, new ItemStack(Material.DIAMOND_SWORD));
			p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			p.getInventory().setItem(8, new ItemStack(Material.BOOK));
			p.getInventory().setItem(7, new ItemStack(Material.WATCH));
			p.getInventory().setItem(6, new ItemStack(Material.NAME_TAG));



	
		}
	}

}
