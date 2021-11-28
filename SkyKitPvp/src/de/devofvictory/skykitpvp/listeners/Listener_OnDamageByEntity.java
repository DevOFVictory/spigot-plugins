package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.skykitpvp.commands.Command_Build;
import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.utils.ItemFrameManager;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnDamageByEntity implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamgeByEntity(EntityDamageByEntityEvent e) {

		if (e.getEntityType() == EntityType.PLAYER) {

			if (e.getDamager() instanceof Player) {

				if (e.getEntity().getLocation().getY() > Variables.pvpHeight
						|| e.getDamager().getLocation().getY() > Variables.pvpHeight) {
					e.setCancelled(true);
				}

			} else if (e.getDamager() instanceof Projectile) {
				Projectile pr = (Projectile) e.getDamager();
				if (pr.getShooter() instanceof Entity) {
					Entity shooter = (Entity) pr.getShooter();
					if (e.getEntity().getLocation().getY() > Variables.pvpHeight
							|| shooter.getLocation().getY() > Variables.pvpHeight) {
						e.setCancelled(true);
					}
				}

			}

		} else if (e.getEntityType() == EntityType.WANDERING_TRADER) {
			try {
				if (e.getEntity().getCustomName().equals(Variables.villagerName)) {
					e.setCancelled(true);
				}
			} catch (NullPointerException ex) {
				// TODO: handle exception
			}
			
		}else if (e.getEntityType() == EntityType.WITCH) {
			try {
				if (e.getEntity().getCustomName().equals(Variables.witchName)) {
					e.setCancelled(true);
				}
			} catch (NullPointerException ex) {
				// TODO: handle exception
			}
		
		}else if (e.getEntityType() == EntityType.ZOMBIE) {
			try {
				if (e.getEntity().getCustomName().equals(Variables.zombieName)) {
					e.setCancelled(true);
				}
			} catch (NullPointerException ex) {
				// TODO: handle exception
			}
		
		} else if (e.getEntityType() == EntityType.ITEM_FRAME) {

			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();

				if (!Command_Build.buildMode.contains(p)) {

					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			}

			if (e.getDamager().getType() == EntityType.PLAYER) {

				Player p = (Player) e.getDamager();

				ItemFrame frame = (ItemFrame) e.getEntity();

				ItemStack is = frame.getItem();
				if (is.getType() == Material.GOLD_INGOT) {
					if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
						if (is.getItemMeta().getDisplayName().equals("§6§lKick mich!")) {
							frame.setItem(new ItemStack(Material.AIR));
							ItemFrameManager.setGold(ItemFrameManager.getFreeItemFrame(frame));

							p.sendTitle("", "§6§l+" + Variables.coinsPerFrame);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
							EcoManager.addCoins(p, Variables.coinsPerFrame);

						}
					}
				}
			}
		}
	}

}
