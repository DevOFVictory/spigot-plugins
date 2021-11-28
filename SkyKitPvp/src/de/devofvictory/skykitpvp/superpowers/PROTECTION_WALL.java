package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;

public class PROTECTION_WALL implements SuperPowerExecutor{

	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
		
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int duration = Integer.parseInt(kitLevel.getVariableValue("duration"));
		
		ArrayList<Location> locations = new ArrayList<Location>();
		Location pLoc = p.getLocation().getBlock().getLocation();

		switch (getCardinalDirection(p)) {
		case "N":
			Location main1 = pLoc.clone().add(0,0,-2);
			locations.add(main1);
			locations.add(main1.clone().add(-1,0,0));
			locations.add(main1.clone().add(1,0,0));
			locations.add(main1.clone().add(-1,1,0));
			locations.add(main1.clone().add(1,1,0));
			locations.add(main1.clone().add(0,1,0));
			locations.add(main1.clone().add(0,2,0));
			
			break;
		case "E":
			
			Location main4 = pLoc.clone().add(2,0,0);
			locations.add(main4);
			locations.add(main4.clone().add(0,0,1));
			locations.add(main4.clone().add(0,0,-1));
			locations.add(main4.clone().add(0,1,1));
			locations.add(main4.clone().add(0,1,-1));
			locations.add(main4.clone().add(0,1,0));
			locations.add(main4.clone().add(0,2,0));
			
			
			break;
		case "S":
			Location main3 = pLoc.clone().add(0,0,2);
			locations.add(main3);
			locations.add(main3.clone().add(-1,0,0));
			locations.add(main3.clone().add(1,0,0));
			locations.add(main3.clone().add(-1,1,0));
			locations.add(main3.clone().add(1,1,0));
			locations.add(main3.clone().add(0,1,0));
			locations.add(main3.clone().add(0,2,0));
			
			break;
		case "W":
			Location main2 = pLoc.clone().add(-2,0,0);
			locations.add(main2);
			locations.add(main2.clone().add(0,0,1));
			locations.add(main2.clone().add(0,0,-1));
			locations.add(main2.clone().add(0,1,1));
			locations.add(main2.clone().add(0,1,-1));
			locations.add(main2.clone().add(0,1,0));
			locations.add(main2.clone().add(0,2,0));
			
			
			break;

		default:
			break;
		}
		
		
		for (Location loc : locations) {
			
			Material matBefore = loc.getBlock().getType();
			BlockData dataBefore = loc.getBlock().getBlockData();
			
			loc.getBlock().setType(Material.BRICKS);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					if (matBefore != Material.BRICK) {
					
						loc.getBlock().setType(matBefore);
						loc.getBlock().setBlockData(dataBefore);
					
					}
					
				}
			}, duration*20);
			
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
	
	public String getCardinalDirection(Player p) {

		double rotation = (p.getLocation().getYaw() - 90.0F) % 360.0F;

		if (rotation < 0.0D) {
		rotation += 360.0D;
		}
		if ((0.0D <= rotation) && (rotation < 45.0D))
		return "W";
		if ((45.0D <= rotation) && (rotation < 135.0D))
		return "N";
		if ((135.0D <= rotation) && (rotation < 225.0D))
		return "E";
		if ((225.0D <= rotation) && (rotation < 315.0D))
		return "S";
		if ((315.0D <= rotation) && (rotation < 360.0D)) {
		return "W";
		}
		return null;
		}
	
}
