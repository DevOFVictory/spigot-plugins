package de.devofvictory.skykitpvp.listeners;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.OtherUtils;

public class Listener_OnInteract_PerformSuperpower implements Listener{
	
	@EventHandler
	public void onToggleSneak(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() != Action.PHYSICAL) {
			perform(p);
		}
		}
	
	
	public static void perform(Player p) {
		if (KitManager.hasKitSelected(p)) {
			Kit kit = KitManager.getSelectedKit(p);
			try {
				if (p.getInventory().getHeldItemSlot() == 2 && p.getInventory().getItemInMainHand().getType() == kit.getDisplayType()) {
					
				if (!OtherUtils.isSpawn(p.getLocation())) {
					
					try {
						Class<?> clazz = Class.forName("de.devofvictory.skykitpvp.superpowers."+kit.getSuperPower().toString().toUpperCase());
	
						Method runMethod = clazz.getDeclaredMethod("runSuperPower", Player.class);
						runMethod.invoke(clazz.newInstance(), p);
						
					} catch (Exception ex) {
						p.sendMessage(Main.Prefix+"§cDeine Superpower konnte nicht gefunden werden! Den genauen Fehler kannst du in der Console nachlesen...");
						
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4-------------------------------------------------------------------------------------------");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4                                        FEHLER");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4Es wurde versucht eine Superpower zu benutzen, die nicht");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4regristert bzw. vorhanden ist. Bitte prüfe:");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4 - Die Klasse mit der Superpower liegt in de.devofvictory.superpowers");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4 - Der Eintrag in der kits.yml, die Klasse und der Enum Eintrag sind IDENTISCH");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4 - Die Run-Klasse implementiert das Interface 'SuperPowerExecutor'");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4 - Eine Methode namens 'runSuperPower(Player p)' wird überschrieben");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4Wenn all dies übereinstimmt lese dir den untenstehenden Java Fehler durch oder erreiche mich hier:");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§9Discord: DevOFVictory#3301");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§9Email: devofvictory@gmail.com");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4-------------------------------------------------------------------------------------------");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§c");
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§c>>>>>>>>>>>");
						ex.printStackTrace();
						Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§c>>>>>>>>>>>");
					}
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
			p.sendMessage(Main.Prefix+"§cDu kannst deine Superkraft nicht am Spawn aktivieren!");
		}
			}
			
		}catch (NullPointerException ex) {
			// TODO: handle exception
		}
		}
	}
	}

	

