package de.devofvictory.prefix2000.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.prefix2000.commands.CommandCustomprefix;
import de.devofvictory.prefix2000.commands.CommandSetLastLine;
import de.devofvictory.prefix2000.listeners.OnChat;
import de.devofvictory.prefix2000.listeners.OnJoin;
import de.devofvictory.prefix2000.listeners.OnSneak;
import de.devofvictory.prefix2000.utils.ScoreboardClass;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;



public class Main extends JavaPlugin implements Listener{
	
	
	public static Economy economy = null;
	public static Permission permission = null;
	
	public static Main instance;
	 
	public static File file = new File("plugins/Prefix2000","config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static String lastline = "";
	
	
	
	
	@Override
	public FileConfiguration getConfig() {
		// TODO Auto-generated method stub
		return super.getConfig();
	}
	
	

	
	
	public static String Prefix = "ßf[ß4ChiliPro.netßf] ";
	
	
	
//TabPrefixes
	//Users
	public static String TabPrefixDefault   = cfg.getString("Config.Ranks.Users.Default.tabprefix").replace('&', 'ß');
	public static String TabPrefixSpieler   = cfg.getString("Config.Ranks.Users.Spieler.tabprefix").replace('&', 'ß');
	public static String TabPrefixPremium   = cfg.getString("Config.Ranks.Users.Premium.tabprefix").replace('&', 'ß');
	public static String TabPrefixUltra     = cfg.getString("Config.Ranks.Users.Ultra.tabprefix").replace('&', 'ß');
	public static String TabPrefixChampion  = cfg.getString("Config.Ranks.Users.Champion.tabprefix").replace('&', 'ß');
	public static String TabPrefixLegende   = cfg.getString("Config.Ranks.Users.Legende.tabprefix").replace('&', 'ß');
	//Team 
	public static String TabPrefixBuilder   = cfg.getString("Config.Ranks.Team.Builder.tabprefix").replace('&', 'ß');
	public static String TabPrefixDeveloper = cfg.getString("Config.Ranks.Team.Developer.tabprefix").replace('&', 'ß');
	public static String TabPrefixModerator = cfg.getString("Config.Ranks.Team.Moderator.tabprefix").replace('&', 'ß');
	public static String TabPrefixAdmin     = cfg.getString("Config.Ranks.Team.Admin.tabprefix").replace('&', 'ß');
	public static String TabPrefixOwner     = cfg.getString("Config.Ranks.Team.Owner.tabprefix").replace('&', 'ß');
	//Custom
	public static String TabPrefixCustom    = cfg.getString("Config.Ranks.Custom.tabnamecolor").replace('&', 'ß');
	
//ChatPrefixes
	//Users
	public static String ChatPrefixDefault   = cfg.getString("Config.Ranks.Users.Default.chatprefix").replace('&', 'ß');
	public static String ChatPrefixSpieler   = cfg.getString("Config.Ranks.Users.Spieler.chatprefix").replace('&', 'ß');
	public static String ChatPrefixPremium   = cfg.getString("Config.Ranks.Users.Premium.chatprefix").replace('&', 'ß');
	public static String ChatPrefixUltra     = cfg.getString("Config.Ranks.Users.Ultra.chatprefix").replace('&', 'ß');
	public static String ChatPrefixChampion  = cfg.getString("Config.Ranks.Users.Champion.chatprefix").replace('&', 'ß');
	public static String ChatPrefixLegende   = cfg.getString("Config.Ranks.Users.Legende.chatprefix").replace('&', 'ß');
	//Team 
	public static String ChatPrefixBuilder   = cfg.getString("Config.Ranks.Team.Builder.chatprefix").replace('&', 'ß');
	public static String ChatPrefixDeveloper = cfg.getString("Config.Ranks.Team.Developer.chatprefix").replace('&', 'ß');
	public static String ChatPrefixModerator = cfg.getString("Config.Ranks.Team.Moderator.chatprefix").replace('&', 'ß');
	public static String ChatPrefixAdmin     = cfg.getString("Config.Ranks.Team.Admin.chatprefix").replace('&', 'ß');
	public static String ChatPrefixOwner     = cfg.getString("Config.Ranks.Team.Owner.chatprefix").replace('&', 'ß');
	//Custom
	public static String ChatPrefixCustom    = cfg.getString("Config.Ranks.Custom.chatnamecolor").replace('&', 'ß');
	
//TextColors
	//Users
	public static String TextPrefixDefault   = cfg.getString("Config.Ranks.Users.Default.text").replace('&', 'ß');
	public static String TextPrefixSpieler   = cfg.getString("Config.Ranks.Users.Spieler.text").replace('&', 'ß');
	public static String TextPrefixPremium   = cfg.getString("Config.Ranks.Users.Premium.text").replace('&', 'ß');
	public static String TextPrefixUltra     = cfg.getString("Config.Ranks.Users.Ultra.text").replace('&', 'ß');
	public static String TextPrefixChampion  = cfg.getString("Config.Ranks.Users.Champion.text").replace('&', 'ß');
	public static String TextPrefixLegende   = cfg.getString("Config.Ranks.Users.Legende.text").replace('&', 'ß');
	//Team 
	public static String TextPrefixBuilder   = cfg.getString("Config.Ranks.Team.Builder.text").replace('&', 'ß');
	public static String TextPrefixDeveloper = cfg.getString("Config.Ranks.Team.Developer.text").replace('&', 'ß');
	public static String TextPrefixModerator = cfg.getString("Config.Ranks.Team.Moderator.text").replace('&', 'ß');
	public static String TextPrefixAdmin     = cfg.getString("Config.Ranks.Team.Admin.text").replace('&', 'ß');
	public static String TextPrefixOwner     = cfg.getString("Config.Ranks.Team.Owner.text").replace('&', 'ß');
	//Custom
	public static String TextPrefixCustom    = cfg.getString("Config.Ranks.Custom.text").replace('&', 'ß');
	
	
	 
	 public void onEnable() {
		 instance = this;
		
		 getLogger().info("###############################################");
		 getLogger().info("              [Prefix2000] Enable");
		 getLogger().info("                 Version "+getDescription().getVersion());
	 	 getLogger().info("Plugin erfolgreich geladen und aktiviert!");
	 	 getLogger().info("###############################################");
	 	
	 	 loadCfg();
	 	 
	 	 if (cfg.getString("Config.Settings.lastline") == null) {
	 		 cfg.set("Config.Settings.lastline", "ß4ßlViel Spaﬂ");
	 		 saveCfg();
	 	 }
	 	
	 	 lastline = cfg.getString("Config.Settings.lastline");
	 	 
	 	 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new OnChat(), this);
		 pm.registerEvents(new OnJoin(), this);
		 pm.registerEvents(new OnSneak(), this);
		 
		 
		 
		 getCommand("customprefix").setExecutor(new CommandCustomprefix());
		 getCommand("setlastsbline").setExecutor(new CommandSetLastLine());

	 	 
	 	 
	 	 instance = this;
	 	loadCfg();
	 	ScoreboardClass.loadCfg();
	 	this.saveDefaultConfig (); 
	 	this.getConfig (). options (). copyDefaults (true); 
	 	setupEconomy();
	 	setupPermissions();
	 	
	 	
	}
	 public void onDisable() {
		 cfg.set("Config.Settings.lastline", lastline);
		 saveCfg();
		 System.out.println("test");
	 }
	 
	 private boolean setupEconomy()
	    {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }
	 private boolean setupPermissions()
	    {
	        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            permission = permissionProvider.getProvider();
	        }
	        return (permission != null);
	    }
	

	
	
	
	
	
	
	public static void saveCfg() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Prefix2000] Ein Fehler beim Speichern der config.yml ist aufgetreten");
		}
	
	}
	public static  void loadCfg() {
		if(!file.exists()) { 
			try {
				file.createNewFile();
				System.out.println("[Prefix2000] Config wurde erstellt!");
			} catch (IOException e) {
				System.out.println("[Prefix2000] Config wurde geladen!");
			}
		cfg.options().copyDefaults(true);
		saveCfg();
	}
	}
	public void reloadCfg() {
		reloadConfig();
	}

}