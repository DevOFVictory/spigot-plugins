package de.devofvictory.skykitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.specialitems.CoinTracker;
import de.devofvictory.skykitpvp.specialitems.Granate;
import de.devofvictory.skykitpvp.specialitems.Slimeball;
import de.devofvictory.skykitpvp.utils.Variables;

public class Listener_OnInvClick_Witch implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {

		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();

			if (e.getView().getTitle() != null) {
				if (e.getView().getTitle().equals(Variables.witchName)) {
					e.setCancelled(true);

					if (e.getCurrentItem() != null) {
						if (e.getCurrentItem().hasItemMeta()) {
							if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
								
								String name = e.getCurrentItem().getItemMeta().getDisplayName();
								
								if (name.equals(CoinTracker.name)) {
									
									if (EcoManager.hasEnough(p, CoinTracker.price)) {
										
										EcoManager.takeCoins(p, CoinTracker.price);
										
										ItemStack is = new ItemStack(Material.COMPASS);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName(CoinTracker.name);
										is.setItemMeta(meta);
										
										p.getInventory().addItem(is);
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
										
										p.sendMessage(Main.Prefix+"§aDu hast dir "+CoinTracker.name+" §aerfolgreich für "+CoinTracker.price+" gekauft!");
										p.closeInventory();
										
									}else {
										p.sendMessage(Main.Prefix+"§cDir fehlen noch §6"+(CoinTracker.price-EcoManager.getCoins(p))+ " §cCoins um dieses Item zu kaufen!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										p.closeInventory();
										
										
									}
									
									
								}else if (name.equals(Granate.name)) {
									
									if (EcoManager.hasEnough(p, Granate.price)) {
										
										EcoManager.takeCoins(p, Granate.price);
										
										ItemStack is = new ItemStack(Material.SNOWBALL);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName(Granate.name);
										is.setItemMeta(meta);
										
										p.getInventory().addItem(is);
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
										
										p.sendMessage(Main.Prefix+"§aDu hast dir "+Granate.name+" §aerfolgreich für "+Granate.price+" gekauft!");
										p.closeInventory();
										
									}else {
										p.sendMessage(Main.Prefix+"§cDir fehlen noch §6"+(CoinTracker.price-EcoManager.getCoins(p))+ " §cCoins um dieses Item zu kaufen!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										p.closeInventory();
									}
									

									
									
									
									
								}else if (name.equals("§4§lEnderperle")) {
									if (EcoManager.hasEnough(p, 100)) {
										
										EcoManager.takeCoins(p, 100);
										
										ItemStack is = new ItemStack(Material.ENDER_PEARL);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName("§4§lEnderperle");
										is.setItemMeta(meta);
										
										p.getInventory().addItem(is);
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
										
										p.sendMessage(Main.Prefix+"§aDu hast dir §4§lEnderperle §aerfolgreich für 100 gekauft!");
										p.closeInventory();
										
									}else {
										p.sendMessage(Main.Prefix+"§cDir fehlen noch §6"+(100-EcoManager.getCoins(p))+ " §cCoins um dieses Item zu kaufen!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										p.closeInventory();
									}
								}else if (name.equals(Slimeball.name)){
									if (EcoManager.hasEnough(p, Slimeball.price)) {
										
										EcoManager.takeCoins(p, Slimeball.price);
										
										ItemStack is = new ItemStack(Material.SLIME_BALL);
										ItemMeta meta = is.getItemMeta();
										meta.setDisplayName(Slimeball.name);
										is.setItemMeta(meta);
										
										p.getInventory().addItem(is);
										p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
										
										p.sendMessage(Main.Prefix+"§aDu hast dir "+Slimeball.name+" §aerfolgreich für "+Slimeball.price+" gekauft!");
										p.closeInventory();
										
									}else {
										p.sendMessage(Main.Prefix+"§cDir fehlen noch §6"+(CoinTracker.price-EcoManager.getCoins(p))+ " §cCoins um dieses Item zu kaufen!");
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										p.closeInventory();
									}
									
									
								}

							}
						}
					}
				}
			}
		}

	}

}
