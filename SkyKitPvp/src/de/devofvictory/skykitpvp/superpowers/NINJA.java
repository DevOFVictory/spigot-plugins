package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class NINJA implements SuperPowerExecutor{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			
			
			Vector vector = null;
			
			if (p.isOnGround()) {
				vector = p.getLocation().getDirection().setY(0).multiply(2D);
			}else {
				vector = p.getLocation().getDirection().setY(0).normalize();
			}
			
			p.setVelocity(vector);
			p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
			
			for (Player near : OtherUtils.getNearbyPlayers(p, 1)) {
				near.damage(2D, p);
			}
			
			LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
			
			levelBar.addPlayer(p);
			levelBar.start();
			
			p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
			
			timeout.add(p);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					timeout.remove(p);
					
				}
			}, 20*cooldown);
		
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
	}

}
