package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.Variables;

public class BEE_ATTACK implements SuperPowerExecutor, Listener{
	
	static ArrayList<Player> timeout = new ArrayList<Player>();


	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int amount = Integer.parseInt(kitLevel.getVariableValue("amount"));
		
		
		for (int i = 0; i<amount; i++) {
			Bee bee = p.getWorld().spawn(p.getLocation().clone().add(0,2,0), Bee.class);
			bee.setCustomName(p.getName());
			bee.setCustomNameVisible(false);
			bee.setAnger(Integer.MAX_VALUE);
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
	public void onDamgeByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof Bee) {
				if (e.getDamager().getCustomName() != null) {
					Player killer = Bukkit.getPlayer(e.getDamager().getCustomName());
					
					if (killer != null) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {

								Variables.setLastDamager(p, killer);
								
							}
						}, 1);
					}
				}
			}
		}
	}

}
