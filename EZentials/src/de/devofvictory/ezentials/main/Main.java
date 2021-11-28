package de.devofvictory.ezentials.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.ezentials.commands.Command_AFK;
//CommandClassesImports
import de.devofvictory.ezentials.commands.Command_AdvancedSudo;
import de.devofvictory.ezentials.commands.Command_Build;
import de.devofvictory.ezentials.commands.Command_DelayedReload;
import de.devofvictory.ezentials.commands.Command_EZItems;
import de.devofvictory.ezentials.commands.Command_Freeze;
import de.devofvictory.ezentials.commands.Command_GiveAll;
import de.devofvictory.ezentials.commands.Command_IP;
import de.devofvictory.ezentials.commands.Command_Maintenance;
import de.devofvictory.ezentials.commands.Command_Ping;
import de.devofvictory.ezentials.commands.Command_Rename;
import de.devofvictory.ezentials.commands.Command_SendLine;
import de.devofvictory.ezentials.commands.Command_SetLore;
import de.devofvictory.ezentials.commands.Command_ToggleJumppads;
import de.devofvictory.ezentials.commands.EZentilas;
//ListernsImports
import de.devofvictory.ezentials.listeners.Listener_OnBlockBreak;
import de.devofvictory.ezentials.listeners.Listener_OnBlockPlace;
import de.devofvictory.ezentials.listeners.Listener_OnChat;
import de.devofvictory.ezentials.listeners.Listener_OnCommand;
import de.devofvictory.ezentials.listeners.Listener_OnJoin;
import de.devofvictory.ezentials.listeners.Listener_OnLogin;
import de.devofvictory.ezentials.listeners.Listener_OnMove;
import de.devofvictory.ezentials.listeners.Listener_OnQuit;
import de.devofvictory.ezentials.listeners.Listeners_OnInventory;
import de.devofvictory.ezentials.tools.Tool_ProtectionShield;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	public static String Prefix = "§f[§3§lEZ§b§lentials§f] ";
	public static String noPerms(String perm) {
		return Prefix+"§cHier für hast du leider nicht genug Rechte :( \n§8>> §f(§6"+perm+"§f)";
	}
	
	public static File prefixfile = new File("plugins/Prefix2000", "config.yml");
	public static FileConfiguration prefixcfg = YamlConfiguration.loadConfiguration(prefixfile);
	
	public static File file = new File("plugins/EZentials", "config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static boolean JumpPadsEnable;
	
	public static String afkPrefix;
	public static int autoAfkTime;
	
	public static Boolean maintenance;
	public static String maintenanceMessage;
	
	
	public static HashMap<Player, Integer> afkTime = new HashMap<>();
	
	public static String key = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";
	
	public static Main instance() {
		return plugin;
	}
	
	public void onEnable() {
		
		 getLogger().info("###############################################");
		 getLogger().info("              [EZentials] Enable");
		 getLogger().info("                 Version: "+getDescription().getVersion() );
	 	 getLogger().info("Plugin loaded sucsessfully!");
	 	 getLogger().info("###############################################");
	 	 plugin = this;
	 	 
	 	 
	 	 
	 	 loadCfg();
	 	 
	 	 if (!getConfig().isSet("Ezentials.Config.afkPrefix")) {
	 		 getConfig().set("Ezentials.Config.afkPrefix", "[&dAFK-System&f]");
	 		 saveConfig();
	 	 }
	 	afkPrefix = getConfig().getString("Ezentials.Config.afkPrefix");
	 	
	 	if (!getConfig().isSet("Ezentials.Config.autoAfkTime")) {
	 		 getConfig().set("Ezentials.Config.autoAfkTime", 300);
	 		 saveConfig();
	 	 }
	 	autoAfkTime = getConfig().getInt("Ezentials.Config.autoAfkTime");
	 	

	 	 //versionChecker();
	 	 
//	 	 setupPermissions();

	 	 JumpPadsEnable = getConfig().getBoolean("Ezentials.Config.Jumppads.enable");
	 	 
	 	 maintenance = cfg.getBoolean("Ezentials.Config.Maintenance");
	 	 
	 	 maintenanceMessage = cfg.getString("Ezentials.Config.MaintenanceMessage");

	 	//CommandRegister
	 	 getCommand("giveall").setExecutor(new Command_GiveAll());
	 	 getCommand("freeze").setExecutor(new Command_Freeze());
	 	 getCommand("build").setExecutor(new Command_Build());
	 	 getCommand("asudo").setExecutor(new Command_AdvancedSudo());
	 	 getCommand("ezentials").setExecutor(new EZentilas());
	 	 getCommand("rename").setExecutor(new Command_Rename());
	   	 getCommand("setlore").setExecutor(new Command_SetLore());
	   	 getCommand("sendline").setExecutor(new Command_SendLine());
	   	 getCommand("delayedreload").setExecutor(new Command_DelayedReload());
	   	 getCommand("togglejumppads").setExecutor(new Command_ToggleJumppads());
	   	 getCommand("ping").setExecutor(new Command_Ping());
	   	 getCommand("afk").setExecutor(new Command_AFK());
	   	 getCommand("ezitems").setExecutor(new Command_EZItems());
	   	 getCommand("maintenance").setExecutor(new Command_Maintenance());
	   	 getCommand("ip").setExecutor(new Command_IP());
	   	 
	 	//ListenerRegister
		 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new Tool_ProtectionShield(), this);
		 
		 pm.registerEvents(new Listener_OnChat(), this);
		 pm.registerEvents(new Listener_OnMove(), this);
		 pm.registerEvents(new Listener_OnCommand(), this);
		 pm.registerEvents(new Listener_OnBlockBreak(), this);
		 pm.registerEvents(new Listener_OnBlockPlace(), this);
		 pm.registerEvents(new Listeners_OnInventory(), this);
		 pm.registerEvents(new Listener_OnJoin(), this);
		 pm.registerEvents(new Listener_OnQuit(), this);
		 pm.registerEvents(new Command_EZItems(), this);
		 pm.registerEvents(new Listener_OnLogin(), this);
		 
		 
		 for (Player all : Bukkit.getOnlinePlayers()) {
			 afkTime.put(all, 0);
		 }
		 
		 
		 Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					
						
					afkTime.put(all, afkTime.get(all)+1);
					
					if (afkTime.get(all) == 300 && !Command_AFK.afk.contains(all)) {
						
						Command_AFK.autoAfk(all);
						
					
				}
				}
			}
		}, 0, 1*20);
	}
	
	public void onDisable() {
		 getLogger().info("###############################################");
		 getLogger().info("              [EZentials] Disable");
		 getLogger().info("                 Version: "+getDescription().getVersion() );
	 	 getLogger().info("Plugin unloaded sucsessfully!");
	 	 getLogger().info("###############################################");
	 	 
	 	getConfig().set("Ezentials.Config.Jumppads.enable", JumpPadsEnable);
	 	saveConfig();
	 	
	 	getConfig().set("Ezentials.Config.Maintenance", maintenance);
	 	saveConfig();
	 	
	 	getConfig().set("Ezentials.Config.MaintenanceMessage", maintenanceMessage);
	 	saveConfig();
	 	
	 	try {
	 	
	 	for (World worlds : Bukkit.getWorlds()) {
	 		for (Entity entitys : worlds.getEntities()) {
	 			if (entitys instanceof ArmorStand) {
	 				if (entitys.getCustomName().contains("§f§l[§4§lAFK§f§l]")) {
	 					entitys.remove();
	 				}
	 			}
	 		}
	 	}
	 	
	 	}catch (NullPointerException ex) {
	 		
	 	}
	}
	
	
	
//	private boolean setupPermissions()
//    {
//        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
//        if (permissionProvider != null) {
//            permission = permissionProvider.getProvider();
//        }
//        return (permission != null);
//    }
	
	void versionChecker() {
		
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=63636").openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.getOutputStream().write((key+63636).getBytes("UTF-8"));
			String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
			if (!version.equalsIgnoreCase(getDescription().getVersion())) {
				this.getServer().getConsoleSender().sendMessage(Main.Prefix+"§eEs ist eine neue Version verfügbar vom Plugin! \n §6https://www.spigotmc.org/resources/ezentials.63636/");
				
			}else {
				this.getServer().getConsoleSender().sendMessage(Main.Prefix+"§aDie Version vom Plugin ist auf dem neustem Stand!");
			}
		}catch (IOException e) {
			this.getServer().getConsoleSender().sendMessage(Main.Prefix+"§4ERROR: §cDas Plugin konnte keine Verbindung zu SpigotMC.org aufbauen!");
			
		}

	}
	public static void saveCfg() throws IOException {
		instance().saveConfig();
	
	}
	public static void loadCfg() {
		if(!file.exists() || !cfg.isSet("Ezentials.Config.AutoUpdater.sendUpdateMessageOnJoin")) { 
			try {
				file.createNewFile();
				System.out.println("[EZentials] Config was created!");
			} catch (IOException e) {
				System.out.println("[EZentials] Config was loaded!");
			}
			instance().saveDefaultConfig();
		 	instance().getConfig().options().copyDefaults(true);
		 	try {
				saveCfg();
			} catch (IOException e1) {
				
			}
		 	try {
				saveCfg();
			} catch (IOException e) {
				
				
			}
	}
	}
	
	
	
}


