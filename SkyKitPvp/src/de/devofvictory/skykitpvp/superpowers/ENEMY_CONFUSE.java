package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;

public class ENEMY_CONFUSE implements SuperPowerExecutor{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
		
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int effectDuration = Integer.parseInt(kitLevel.getVariableValue("effectDuration"));
			int radius = Integer.parseInt(kitLevel.getVariableValue("radius"));
		
		for (Entity en : p.getNearbyEntities(radius, radius, radius)) {
			if (en instanceof Player) {
				Player near = (Player) en;
				
				if (near != p) {
					
					PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, effectDuration*20, 255, false, false, false);
					PotionEffect nausea = new PotionEffect(PotionEffectType.CONFUSION, effectDuration*20, 255, false, false, false);
					
					near.addPotionEffect(blindness);
					near.addPotionEffect(nausea);
					
				}
			}
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
