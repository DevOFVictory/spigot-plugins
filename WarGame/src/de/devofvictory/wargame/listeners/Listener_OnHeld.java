package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import de.devofvictory.wargame.items.AK;
import de.devofvictory.wargame.items.MachineGun;
import de.devofvictory.wargame.items.Pistol;
import de.devofvictory.wargame.items.RocketLauncher;
import de.devofvictory.wargame.items.SchrotFlinte;
import de.devofvictory.wargame.items.Sniper;
import de.devofvictory.wargame.items.WhiteRide;
import de.devofvictory.wargame.utils.ActionBar;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.StartGame;

public class Listener_OnHeld implements Listener{
	
	@EventHandler
	public void onHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		
		if (SpectatorClass.specCamera.containsValue(p.getName())) {
			for (String playername : SpectatorClass.specCamera.keySet()) {
				if (SpectatorClass.specCamera.get(playername).equalsIgnoreCase(p.getName())) {
					Player spectator = Bukkit.getPlayer(playername);
					
					if (spectator != null) {
						try {
							ActionBar.sendActionBarTime(spectator, p.getInventory().getItem(e.getNewSlot()).getItemMeta().getDisplayName(), 1*20);	
						}catch (NullPointerException ex) {
							ActionBar.sendActionBarTime(spectator, "§c§lNichts", 1*20);
						}
					}
				}
			}
		}
		
		if (StartGame.living.contains(p)) {
			try {
					String weapon = p.getInventory().getItem(e.getNewSlot()).getItemMeta().getDisplayName().replaceAll("§6§l", "");
					
					//	l("de.devofvictory.wargame.weapons.", ""))+" §8» §c"+shootsLeft.get(p)+" §f/ §a"+shoots, 2*20);

					
					switch (weapon) {
					
					case "AK":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+AK.shootsLeft.get(p)+" §f/ §a"+AK.shoots,20);
						break;
					case "MachineGun":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+MachineGun.shootsLeft.get(p)+" §f/ §a"+MachineGun.shoots,20);
						break;
					case "Pistol":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+Pistol.shootsLeft.get(p)+" §f/ §a"+Pistol.shoots,20);
						break;
					case "RocketLauncher":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+RocketLauncher.shootsLeft.get(p)+" §f/ §a"+RocketLauncher.shoots,20);
						break;
					case "SchrotFlinte":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+SchrotFlinte.shootsLeft.get(p)+" §f/ §a"+SchrotFlinte.shoots,20);
						break;
					case "Sniper":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+Sniper.shootsLeft.get(p)+" §f/ §a"+Sniper.shoots,20);
						break;
					case "WhiteRide":
						ActionBar.sendActionBarTime(p, "§6§l"+weapon+" §8» §c"+WhiteRide.shootsLeft.get(p)+" §f/ §a"+WhiteRide.shoots,20);
						break;
					default:
						ActionBar.sendActionBar(p, "");
						
						
					
					}
					
					
						
					
			}catch (NullPointerException ex) {
				
			}
		}
	}

}
