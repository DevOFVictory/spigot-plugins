package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.Fireworkgenerator;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;
import de.devofvictory.skykitpvp.utils.OtherUtils;
import de.devofvictory.skykitpvp.utils.Variables;

public class ENEMY_ROCKET implements SuperPowerExecutor {

	static ArrayList<Player> timeout = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@Override
	public void runSuperPower(Player p) {

		if (!timeout.contains(p)) {

			Kit kit = KitManager.getSelectedKit(p);
			
			int kitLevelInt = KitManager.getKitLevel(p, kit);
			KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
			
			int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
			int height = Integer.parseInt(kitLevel.getVariableValue("height"));
			int radius = Integer.parseInt(kitLevel.getVariableValue("radius"));
			
			if (p.isOnGround()) {

			if (!OtherUtils.getNearbyPlayers(p, 5).isEmpty()) {

				for (Player target : OtherUtils.getNearbyPlayers(p, radius)) {
					
					Variables.setLastDamager(target, p);

					Fireworkgenerator fwg = new Fireworkgenerator(Main.getInstance());
					fwg.setLocation(target.getLocation());
					fwg.setPower(3);
					fwg.setLifeTime(height * 20);
					fwg.setEffect(FireworkEffect.builder().withColor(Color.RED).with(Type.BALL).withColor(Color.WHITE)
							.withTrail().build());
					Firework firework = fwg.spawn();
					firework.setPassenger(target);
					p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
					
					LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
					
					levelBar.addPlayer(p);
					levelBar.start();

					timeout.add(p);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {

							timeout.remove(p);

						}
					}, 20 * cooldown);
					
				

				}
			}else {
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
			}
			
			}else {
				Fireworkgenerator fwg = new Fireworkgenerator(Main.getInstance());
				fwg.setLocation(p.getLocation());
				fwg.setPower(3);
				fwg.setLifeTime(height * 20);
				fwg.setEffect(FireworkEffect.builder().withColor(Color.RED).with(Type.BALL).withColor(Color.WHITE)
						.withTrail().build());
				Firework firework = fwg.spawn();
				firework.setPassenger(p);
				p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
				
				LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
				
				levelBar.addPlayer(p);
				levelBar.start();

				timeout.add(p);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {

						timeout.remove(p);

					}
				}, 20 * cooldown);
			}

		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
	}

}
