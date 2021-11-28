package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;
import de.devofvictory.skykitpvp.utils.Variables;

public class ENEMY_GRABBLER implements SuperPowerExecutor{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
		
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int radius = Integer.parseInt(kitLevel.getVariableValue("radius"));
		
		
		
			for (Player target : OtherUtils.getNearbyPlayers(p, radius)) {
				
				Location difference = p.getLocation().subtract(target.getLocation());
				target.setVelocity(difference.toVector().normalize().multiply(target.getLocation().distance(p.getLocation())/2));
				
				Variables.setLastDamager(target, p);
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
