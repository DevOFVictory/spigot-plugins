package de.devofvictory.spigotlobbysystem.listeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.devofvictory.spigotlobbysystem.utils.Utils;


public class Listener_OnMove implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Utils.JumpPads) {
		if (p.getLocation().getBlock().getType() == Material.STONE_PLATE && p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.DIAMOND_BLOCK) {
			
					Vector v = p.getLocation().getDirection().multiply(Utils.jumppadBoostX).setY(Utils.jumppadBoostY);
					p.setVelocity(v);
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 2);
				}
		}
	
	}

}
