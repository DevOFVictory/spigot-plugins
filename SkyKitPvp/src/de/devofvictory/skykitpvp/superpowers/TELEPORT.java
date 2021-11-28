package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;

public class TELEPORT implements SuperPowerExecutor{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			
		Kit kit = KitManager.getSelectedKit(p);
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
		
		int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
		int radius = Integer.parseInt(kitLevel.getVariableValue("radius"));
		
		if (randomTp(p, radius)) {
			
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
	
    public boolean randomTp(Player p, int radius) {
    	
    	for (int i = 0; i<100; i++) {
    		
    		int playerX = p.getLocation().getBlockX();
    		int playerZ = p.getLocation().getBlockZ();
    		
    		int x = ThreadLocalRandom.current().nextInt(playerX-radius, playerX+radius + 1);
    		int z = ThreadLocalRandom.current().nextInt(playerZ-radius, playerZ+radius + 1);
    		
    		Location location = p.getWorld().getHighestBlockAt(x, z).getLocation().clone().add(0,1,0);
    		
    		if (isSafe(location)) {
    			p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
    			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    			p.teleport(new Location(p.getWorld(), location.getX(), location.getY(), location.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
    			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    			p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
    			return true;
    		}
    	}
    	return false;
    	
    	
    	
    }
    
    public boolean isSafe(Location loc) {
    	if (loc != null) {
    		
    		if (loc.clone().subtract(0,1,0).getBlock().getType() != Material.AIR && loc.getBlock().getType() == Material.AIR && loc.clone().add(0,1,0).getBlock().getType() == Material.AIR) {
    			return true;
    		}else {
    			return false;
    		}
    		
    	}else {
    		return false;
    	}
    }

}
