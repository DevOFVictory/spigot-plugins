package de.devofvictory.soulboundenchantment.main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import com.trophonix.tradeplus.TradePlus;

import de.devofvictory.soulboundenchantment.commands.Command_Soulbound;
import de.devofvictory.soulboundenchantment.listeners.Listener_OnAnvil;
import de.devofvictory.soulboundenchantment.listeners.Listener_OnDisenchant;
import de.devofvictory.soulboundenchantment.listeners.Listener_OnInvClick;
import de.devofvictory.soulboundenchantment.listeners.Listener_OnRespawn;
import de.devofvictory.soulboundenchantment.listeners.Listener_OnSoulboundItemUse;
import de.devofvictory.soulboundenchantment.objects.Soulbound;
import de.devofvictory.soulboundenchantment.utils.armorequipevent.ArmorListener;
import de.devofvictory.soulboundenchantment.utils.armorequipevent.Blocked;

public class SoulboundEnchantment extends JavaPlugin {
	
	public static Soulbound ench = new Soulbound(NamespacedKey.minecraft("souldbound"));
	public static String tradePlusTitle = "#-1";
	public static SoulboundEnchantment plugin;
	
	@Override
	public void onEnable() {
		loadEnchantments();
		registerListeners();
		registerCommands();
		plugin = this;
		
		if (Bukkit.getPluginManager().getPlugin("TradePlus") != null) {
			TradePlus tp = (TradePlus) Bukkit.getPluginManager().getPlugin("TradePlus");
			File file = new File(tp.getDataFolder(), "config.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			tradePlusTitle = cfg.getString("gui.title").replace('&', '§');
		}
	}
	
	
	private void registerCommands() {
		getCommand("soulbound").setExecutor(new Command_Soulbound());
	}


	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(ench, this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnRespawn(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnAnvil(), this);
		List<String> blocked = new ArrayList<String>();
		for (Blocked b : Blocked.values()) blocked.add(b.toString());
		getServer().getPluginManager().registerEvents(new ArmorListener(blocked), this);
		
		Bukkit.getPluginManager().registerEvents(new Listener_OnSoulboundItemUse(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInvClick(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDisenchant(), this);
	}
	
	public static SoulboundEnchantment getPlugin() {
		return plugin;
	}


	private void loadEnchantments() {
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (Enchantment.getByKey(NamespacedKey.minecraft("souldbound")) == null) {
					Enchantment.registerEnchantment(ench);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
