package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.OtherUtils;
import de.devofvictory.skykitpvp.utils.StatsManager;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnEntityDeath implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		
		
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			Player killer = player.getKiller();
			
			if (killer == null || !(killer instanceof Player)) {
				if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
					EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)player.getLastDamageCause();
					if (edbee.getDamager() instanceof Player) {
						killer = (Player) edbee.getDamager();
					}else if (edbee.getDamager() instanceof Projectile) {
						Projectile proj = (Projectile) edbee.getDamager();
						if (proj.getShooter() instanceof Player) {
							killer = (Player) proj.getShooter();
						}
					}
				}
			}
			
			if (killer == null) {
				if (Variables.getLastDamager(player) != null) {
					killer = Variables.getLastDamager(player);
				}
			}
			
			
			
			if (killer != null && killer != player && player.getLastDamageCause().getCause() != DamageCause.BLOCK_EXPLOSION) {
				EcoManager.addCoins(killer, Variables.coinsPerKill);
				killer.sendTitle("", "§6§l+"+Variables.coinsPerKill);
				killer.sendMessage(Main.Prefix+"§aDu hast §2"+player.getName()+" §agetötet!");
				Kit killerKit = KitManager.getSelectedKit(killer);
				
				double health = OtherUtils.round(killer.getHealth()/2, 1);
				
				if (killerKit != null) {
					player.sendMessage(Main.Prefix+"§cDu wurdest von §6"+killer.getName() + " (§4"+health+"❤§6, "+killerKit.getDisplayName()+" §8| §7Lv. "+KitManager.getKitLevel(killer, killerKit)+"§6) §cgetötet!");
				}else {
					player.sendMessage(Main.Prefix+"§cDu wurdest von §6"+killer.getName() + " (§4"+health+"❤§6) §cgetötet!");
				}
				
				if (KitManager.hasKitSelected(killer)) {
				
					Kit kit = KitManager.getSelectedKit(killer);
					
					int kitLevelInt = KitManager.getKitLevel(killer, kit);
					KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
					
					PotionEffect regeneneration = new PotionEffect(PotionEffectType.REGENERATION, kitLevel.getRegenerationTime()*20, 3, true, true, true);
					killer.addPotionEffect(regeneneration);
				
				}else {
					PotionEffect regeneneration = new PotionEffect(PotionEffectType.REGENERATION, 1*20, 3, true, true, true);
					killer.addPotionEffect(regeneneration);
				}
				
				
				
				StatsManager.addKill(killer.getUniqueId().toString());
				KitManager.addKitKill(killer, KitManager.getSelectedKit(killer));
				
				killer.setLevel(killer.getLevel()+1);
				
				if (killer.getLevel()%5 == 0) {
					Bukkit.broadcastMessage(Main.Prefix+"§2"+killer.getName()+" §ahat einen Killstreak von §2"+killer.getLevel()+"§a!");
				}
				
				Variables.resetLastDamager(player);
			}
			
			StatsManager.addDeath(player.getUniqueId().toString());
			StatsManager.reloadStatsWall();
			player.setLevel(0);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					player.spigot().respawn();
					player.teleport(Variables.getSpawnLocation());
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 155, false, false, false));
					player.sendTitle("§4§lGestorben!", "");
					player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 10, 10);
					
					if (KitManager.hasKitSelected(player)) {
						KitManager.giveKitItems(player);
					}
				}
			}, 1);
			
		}
	}

}
