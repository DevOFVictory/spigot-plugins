package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;
import de.devofvictory.skykitpvp.utils.Variables;

public class WOLF_ATTACK implements SuperPowerExecutor, Listener {

	static ArrayList<Player> timeout = new ArrayList<Player>();

	@Override
	public void runSuperPower(Player p) {

		if (!timeout.contains(p)) {

			if (p.isOnGround()) {

				Kit kit = KitManager.getSelectedKit(p);


				int kitLevelInt = KitManager.getKitLevel(p, kit);
				KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
				
				int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
				int duration = Integer.parseInt(kitLevel.getVariableValue("duration"));
				int strengh = Integer.parseInt(kitLevel.getVariableValue("strengh"));


				Block targetBlock = p.getTargetBlockExact(50);
				if (targetBlock != null) {

					PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 1000000, 2, false, false, false);
					PotionEffect strenghEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, strengh, false, false,
							false);

					Wolf wolf = p.getWorld().spawn(p.getLocation(), Wolf.class);
					wolf.setAngry(true);
					wolf.setTarget(OtherUtils.getNearestPlayer(p, 50, p));
					wolf.addPotionEffect(speedEffect);
					wolf.addPotionEffect(strenghEffect);
					wolf.setSilent(false);
					wolf.setCustomName(p.getName());
					wolf.setCustomNameVisible(false);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {

							wolf.remove();

						}
					}, duration * 20);
					
					LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
					
					levelBar.addPlayer(p);
					levelBar.start();

					p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);

					timeout.add(p);
				}else {
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
				}

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {

						timeout.remove(p);

					}
				}, 20 * cooldown);
			} else {
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
			}

		} else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

		}
	}
	
	@EventHandler
	public void onDamgeByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof Wolf) {
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
