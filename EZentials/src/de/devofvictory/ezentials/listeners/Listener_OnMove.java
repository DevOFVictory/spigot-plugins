package de.devofvictory.ezentials.listeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.devofvictory.ezentials.commands.Command_AFK;
import de.devofvictory.ezentials.commands.Command_Freeze;
import de.devofvictory.ezentials.main.Main;



public class Listener_OnMove implements Listener{
	

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.hasMetadata("freezed")) {
			p.teleport(Command_Freeze.loc);
			
		}
		
		if (p.getLocation().getBlock().getType() == Material.GOLD_PLATE && p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.REDSTONE_BLOCK) {
			if (Main.JumpPadsEnable) {
				if (p.hasPermission("ezentials.jumppad.use")) {
					Vector v = p.getLocation().getDirection().multiply(3D).setY(1);
					p.setVelocity(v);
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 2);
				}
			}
		}
		Main.afkTime.put(p, 0);
		if (Command_AFK.afk.contains(p)) {
			Command_AFK.removeAfk(p);
		}
		
		}
}
