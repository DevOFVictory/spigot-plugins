package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class RAMMING_ATTACK implements SuperPowerExecutor{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			
		Kit kit = KitManager.getSelectedKit(p);
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
		
		int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
		int damage = Integer.parseInt(kitLevel.getVariableValue("damage"));
		int radius = Integer.parseInt(kitLevel.getVariableValue("radius"));
		
		if (p.isOnGround()) {
		
		Vector up = new Vector(0,1.5,0);
		Vector down = new Vector(0,-3,0);
		
		p.setVelocity(up);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				p.setVelocity(down);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						if (!OtherUtils.isSpawn(p.getLocation())) {
						
							Material material = Material.DIRT;
							
							BlockData data = material.createBlockData();
							
							p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), radius*200, radius, 1, radius, data);
							
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
							
							
							for (Player near : OtherUtils.getNearbyPlayers(p, radius)) {
								
								if (!OtherUtils.isSpawn(near.getLocation())) {
									near.damage(1, p);
									near.setHealth(near.getHealth()-damage+1);
									near.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
								}
								
							}
						
						}
						
					}
				}, 5);
				
			}
		},20);
		
		
		
		
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
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
		}
		
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
	}

	
	
}
