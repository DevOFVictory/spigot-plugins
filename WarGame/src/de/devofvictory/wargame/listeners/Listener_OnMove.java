package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.items.AK;
import de.devofvictory.wargame.items.MachineGun;
import de.devofvictory.wargame.items.Pistol;
import de.devofvictory.wargame.items.RocketLauncher;
import de.devofvictory.wargame.items.SchrotFlinte;
import de.devofvictory.wargame.items.Sniper;
import de.devofvictory.wargame.items.WhiteRide;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Bar;
import de.devofvictory.wargame.utils.BossBar;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.StartGame;

public class Listener_OnMove implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		for (Bar b : BossBar.bars.values()) {
			b.update();
		}
		
		
		
		if (SpectatorClass.specCamera.containsValue(p.getName())) {
			for (String playername : SpectatorClass.specCamera.keySet()) {
				if (SpectatorClass.specCamera.get(playername).equalsIgnoreCase(p.getName())) {
					Player spectator = Bukkit.getPlayer(playername);
					
					if (spectator != null) {
						if (spectator.getLocation().distance(p.getLocation()) > 40) {
							
							Boolean hasOpen = false;
							if (spectator.getOpenInventory().getTitle().equalsIgnoreCase("§8> §2§lInventar §8<"))
								hasOpen = true;
							
							
							spectator.teleport(p);
							
							
							if (hasOpen) {
							Inventory inv = Bukkit.createInventory(null, 5*9, "§8> §2§lInventar §8<");
							
							for (int i = 0; i<9; i++) {
								ItemStack is = p.getInventory().getItem(i);
								if (is != null) {
								inv.setItem(i+36, p.getInventory().getItem(i));
								}else {
									inv.setItem(i+36, new ItemStack(Material.AIR));
								}
							}
							
							ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
							ItemMeta placeholderMeta = placeholder.getItemMeta();
							placeholderMeta.setDisplayName("§r");
							placeholder.setItemMeta(placeholderMeta);
							
							for (int i = 27; i<36; i++) {
								inv.setItem(i, placeholder);
							}
							
							for (int i = 0; i<27; i++) {
								ItemStack is = p.getInventory().getItem(i+9);
								
								if (is != null) {
									inv.setItem(i, is);
								}else {
									inv.setItem(i, new ItemStack(Material.AIR));
								}
							}
							spectator.openInventory(inv);
						}
						}
					}
				}
			}
		}
		

			if (e.getFrom().getYaw() == e.getTo().getYaw()) {
				if (!p.isSneaking()) {
				if (Pistol.isRealoding.get(p) || Sniper.isRealoding.get(p) || RocketLauncher.isRealoding.get(p) || AK.isRealoding.get(p) || SchrotFlinte.isRealoding.get(p) || WhiteRide.isRealoding.get(p) || MachineGun.isRealoding.get(p)) {
					Pistol.isRealoding.put(p, false);
					Sniper.isRealoding.put(p, false);
					RocketLauncher.isRealoding.put(p, false);
					AK.isRealoding.put(p, false);
					SchrotFlinte.isRealoding.put(p, false);
					WhiteRide.isRealoding.put(p, false);
					MachineGun.isRealoding.put(p, false);
					p.sendMessage(Main.Prefix+"§4Nachladen abgebrochen!");
					
					
			}
				}
	}		
			if (Main.isGameRunning && StartGame.ride.containsKey(p.getName())) {
				
				
				Creature c = StartGame.ride.get(p.getName());
				
				if (!c.isOnGround()) {
					
					if (StartGame.ride.get(p.getName()).getPassenger() == p && p.getLocation().getPitch() >= 0) {
					
						c.setVelocity(p.getLocation().getDirection().divide(new Vector(3, 3, 3)));
					
					}else {

						c.setVelocity(p.getLocation().getDirection().divide(new Vector(3, 3, 3)).setY(0D));
						
					}
				
				}else {
					c.remove();
					if (c.getPassenger() == p)
						p.teleport(p.getLocation().add(0, 1, 0));
				}
			
			}
}
}