package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class ARROW_RAIN implements SuperPowerExecutor, Listener{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			
		Kit kit = KitManager.getSelectedKit(p);
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
		
		int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
		int amount = Integer.parseInt(kitLevel.getVariableValue("amount"));
		
		double squareRoot = Math.sqrt(amount);
		
		Block targetBlock = p.getTargetBlockExact(50);
		if (targetBlock != null) {
		
		Location loc = targetBlock.getLocation().clone().add(0,20,0);
		
		Location corner1 = loc.clone().add(squareRoot/2, 0, squareRoot/2);
		Location corner2 = loc.clone().subtract(squareRoot/2, 0, squareRoot/2);
		
		List<Location> locations = new ArrayList<Location>(OtherUtils.blocksFromTwoPoints(corner1, corner2));
		
		for (Location spawnLoc : locations) {
			Arrow ar = p.getWorld().spawnArrow(spawnLoc, new Vector(0,-0.5,0), 0F, 0F);
			ar.setShooter(p);
			PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, 4*20, 2, false, false, false);
			ar.addCustomEffect(effect, false);
		}
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
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
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntityType() == EntityType.ARROW) {
			e.getEntity().remove();
		}
	}
	


}
