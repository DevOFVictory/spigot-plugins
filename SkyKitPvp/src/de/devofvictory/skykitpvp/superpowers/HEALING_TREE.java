package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.HealingTree;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;

public class HEALING_TREE implements SuperPowerExecutor, Listener{

	static ArrayList<Player> timeout = new ArrayList<Player>();
	static ArrayList<Location> tmpLocs = new ArrayList<Location>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
		
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int effectLevel = Integer.parseInt(kitLevel.getVariableValue("effectLevel"));
			int duration = Integer.parseInt(kitLevel.getVariableValue("duration"));
		
		
		if (p.isOnGround()) {
			
			Block targetBlock = p.getTargetBlockExact(50);
			if (targetBlock != null) {
			
			HealingTree tree = new HealingTree(targetBlock.getLocation().add(0,1,0), tmpLocs, p, effectLevel);
			tmpLocs.clear();
			if (tree.spawn()) {
				p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
				
				LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
				
				levelBar.addPlayer(p);
				levelBar.start();
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						tree.despawn();
					}
				}, duration * 20);
				
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
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
			}
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
		}
		
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
	}
	
	
	
	@EventHandler
	public void onBlockChange(BlockPhysicsEvent e) {
		if (e.getBlock().getType() == Material.OAK_LEAVES || e.getBlock().getType() == Material.OAK_LOG) {
			if (!tmpLocs.contains(e.getBlock().getLocation())) {
				tmpLocs.add(e.getBlock().getLocation());
			}
			
		}
	}
	


}
