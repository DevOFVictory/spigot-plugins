package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.superpowers.BAT_DISGUISE;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.StatsManager;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnJoinQuit implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage("§7[§a+§7] "+p.getName());
		EcoManager.loadFromMySql(p, true);
		StatsManager.readFromMysql(p, true);
		
		LevelBarUtil.abortLevelBar(p);
		
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		
		p.setLevel(0);
		
		p.setMaxHealth(20);
		p.setHealth(20);
		
		
		if (KitManager.hasKitSelected(p)) {
			KitManager.giveKitItems(p);
		}else {
			KitManager.setKit(p, (Kit) KitManager.getRegisteredKits().toArray()[0]);
		}
		
		if (p.hasPlayedBefore()) {
			p.teleport(Variables.getSpawnLocation());
		}else {
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2*20, 255, false, false, false));
			p.sendTitle("§aWillkommen auf §2SkyKitPvp!", "§eBitte warte kurz!");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					p.teleport(Variables.getSpawnLocation());
					
				}
			}, 2*20);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		LevelBarUtil.abortLevelBar(e.getPlayer());
		e.setQuitMessage("§7[§c-§7] "+e.getPlayer().getName());
		EcoManager.saveToMySql(e.getPlayer(), true);
		StatsManager.saveToMysql(e.getPlayer(), true);
		
		if (BAT_DISGUISE.bats.containsKey(e.getPlayer()))
			BAT_DISGUISE.unDisguise(e.getPlayer());
	}

}
