package de.chilipro.chililobby.listeners;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.chilipro.chililobby.main.Main;

public class Listener_OnJoin implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		
		
		giveJoinItems(p);
		
		if (p.hasPlayedBefore()) {
			p.sendTitle("§eWillkommen zurück,", "§d"+p.getName()+"§e!");
		}else {
			p.sendTitle("§eWillkommen auf §4ChiliPro.net§e,", "§d"+p.getName()+"§e!");
			Bukkit.broadcastMessage(Main.Prefix+"§d"+p.getName()+" §aist neu auf dem Server!");
		}
		
		
		p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
		
		
		
		
	}
	public static void giveJoinItems(Player p) {
		ItemStack menu = new ItemStack(Material.FIREBALL);
		ItemMeta menuMeta = menu.getItemMeta();
		menuMeta.setDisplayName("§b§lMENU");
		menu.setItemMeta(menuMeta);
		
		
		ItemStack cosmetics = new ItemStack(Material.COOKIE);
		ItemMeta cosmeticsMeta = cosmetics.getItemMeta();
		cosmeticsMeta.setDisplayName("§b§lCOSMETICS");
		cosmetics.setItemMeta(cosmeticsMeta);
		
		ItemStack statistics = new ItemStack(Material.ENDER_PEARL);
		ItemMeta statisticsMeta = statistics.getItemMeta();
		statisticsMeta.setDisplayName("§b§lSTATISTIKEN");
		statistics.setItemMeta(statisticsMeta);
		
		ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
		SkullMeta profileMeta = (SkullMeta)profile.getItemMeta();
		profileMeta.setOwner(p.getName());
		profileMeta.setDisplayName("§b§lPROFIL");
		profile.setItemMeta(profileMeta);
		
		ItemStack settings = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta settingsMeta = settings.getItemMeta();
		settingsMeta.setDisplayName("§b§lEINSTELLUNGEN");
		settings.setItemMeta(settingsMeta);
		
		p.getInventory().clear();
		
		p.getInventory().setItem(1, menu);
		p.getInventory().setItem(2, null);
		p.getInventory().setItem(3, null);
		p.getInventory().setItem(4, null);
		p.getInventory().setItem(5, cosmetics);
		p.getInventory().setItem(6, statistics);
		p.getInventory().setItem(7, profile);
		p.getInventory().setItem(8, settings);
		
		p.setHealth(20);
		p.setCanPickupItems(false);
		p.setFoodLevel(20);
		p.setGameMode(GameMode.SURVIVAL);
	}

}
