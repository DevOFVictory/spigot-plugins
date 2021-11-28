package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.ClearUtil;

public class JetPack implements Listener{
	
	static HashMap<Player, Integer> fuelInUse = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if (e.isSneaking() && !p.isOnGround()) {
			
			
			if (!fuelInUse.containsKey(p)) {
				fuelInUse.put(p, 0);
			}
			
			try {
				if (p.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE && p.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lJetPack")) {
					

					// JetPack
					// Fuel
					// SchildTrank
					
					
					if (p.getInventory().getChestplate().getDurability()+1 <= 112) {
						
						if (ClearUtil.hasEnough(p, Material.COAL, "§6§lFuel", 1) || fuelInUse.get(p) > 0) {
							
							if (fuelInUse.get(p) == 0) {
								if (ClearUtil.hasEnough(p, Material.COAL, "§6§lFuel", 1)) {
									if (!Command_Infinitymode.infinitymode.contains(p))
									ClearUtil.removeInventoryItems(p, Material.COAL, "§6§lFuel", 1);
									fuelInUse.put(p, 10);
								}
							}
							
							/* 
							 * 2,a,e,6,c,4
							 * 112-94 = 4
							 * 93 -76 = c
							 * 75 -58 = 6
							 * 57 -40 = e
							 * 39 -22 = a
							 * 21 -00 = 2
							 */
							
							if (!Command_Infinitymode.infinitymode.contains(p))
							fuelInUse.put(p, fuelInUse.get(p)-1);
							
							short duability = p.getInventory().getChestplate().getDurability();
							
							if (isBetween(duability, 94, 112)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §4§l"+fuelInUse.get(p), 1);
							}else if (isBetween(duability, 76, 93)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §c§l"+fuelInUse.get(p), 1);
							}else if (isBetween(duability, 58, 75)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §6§l"+fuelInUse.get(p), 1);
							}else if (isBetween(duability, 40, 57)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §e§l"+fuelInUse.get(p), 1);
							}else if (isBetween(duability, 22, 39)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §a§l"+fuelInUse.get(p), 1);
							}else if (isBetween(duability, 0, 21)) {
								ActionBar.sendActionBarTime(p, "§d§lJetPack-Ladung §f» §2§l"+fuelInUse.get(p), 1);
							}
							
							if (!Command_Infinitymode.infinitymode.contains(p))
							p.getInventory().getChestplate().setDurability((short) (p.getInventory().getChestplate().getDurability()+1));
							p.setVelocity(p.getLocation().getDirection().multiply(0.7D).setY(0.5D));
							
							p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
							
							p.getWorld().playEffect(p.getLocation(), Effect.LAVA_POP, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.LAVADRIP, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.LARGE_SMOKE, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.CLOUD, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE, 1);
							
						}else {
							p.sendMessage(Main.Prefix+"§cDu hast keinen Treibstoff (§6§lFuel§c)!");
							p.playSound(p.getLocation(), Sound.NOTE_PIANO, 8, 8);
						}
					}else {
						p.getInventory().setChestplate(null);
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
				}
					
					
					
				}
			
			}catch (NullPointerException ex) {
			}
		}
	}
	
	
	boolean isBetween(int a, int b, int c) {
		return a >= b ? a <= c : false;
		}

}
