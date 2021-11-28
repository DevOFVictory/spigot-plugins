package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class FIRE_RUNNER implements SuperPowerExecutor, Listener{

	public static ArrayList<Player> enabledFire = new ArrayList<Player>();
	static ArrayList<Player> timeout = new ArrayList<Player>();
	
	
	@Override
	public void runSuperPower(Player p) {
		
		Kit kit = KitManager.getSelectedKit(p);
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
		
		int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
		int duration = Integer.parseInt(kitLevel.getVariableValue("duration"));
		
		if (!timeout.contains(p)) {
		
		
		
		if (!enabledFire.contains(p)) {
			enabledFire.add(p);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					enabledFire.remove(p);
					
				}
			}, duration*20);
			
			timeout.add(p);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {

					timeout.remove(p);

				}
			}, 20 * cooldown);
			
			p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
		}
		
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
		
		
	}
	
	 @EventHandler
	 public void onMoveCombust(PlayerMoveEvent e) {
		 Player p = e.getPlayer();
		 
		 if (enabledFire.contains(p)) {
			 if (p.isOnGround()) {
			 if (!e.getFrom().getBlock().getLocation().equals(e.getTo().getBlock().getLocation())) {
				 
				 	Kit kit = KitManager.getSelectedKit(p);
					
					int kitLevelInt = KitManager.getKitLevel(p, kit);
					KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
					
					int extinguishDuration = Integer.parseInt(kitLevel.getVariableValue("extinguishDuration"));
				 
					
						 Material matBefore = e.getFrom().getBlock().getType();
						 BlockData dataBefore = e.getFrom().getBlock().getBlockData();
						 
						 e.getFrom().getBlock().setType(Material.FIRE);
						 
						 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								if (matBefore != Material.FIRE)  {
									e.getFrom().getBlock().setType(matBefore);
									e.getFrom().getBlock().setBlockData(dataBefore);
								}
								
							}
						}, extinguishDuration * 20);
						
					}
				 
			 }
		 }
	 }
	 
		@EventHandler
		public void onEntityCombust(EntityCombustByBlockEvent e) {
			if (FIRE_RUNNER.enabledFire.contains(e.getEntity())) {
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void ononTeleport(PlayerTeleportEvent e) {
			if (enabledFire.contains(e.getPlayer())) {
				enabledFire.remove(e.getPlayer());
			}
		}
		
		@EventHandler
		public void onMoveExingush(PlayerMoveEvent e) {
			if (enabledFire.contains(e.getPlayer())) {
				if (OtherUtils.isSpawn(e.getTo())) {
					enabledFire.remove(e.getPlayer());
				}
			}
		}

}
