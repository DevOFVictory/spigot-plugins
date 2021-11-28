package de.devofvictory.skykitpvp.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.skykitpvp.commands.Command_Build;
import de.devofvictory.skykitpvp.commands.Command_SetKit;
import de.devofvictory.skykitpvp.commands.Command_Setup;
import de.devofvictory.skykitpvp.commands.Command_Spawn;
import de.devofvictory.skykitpvp.commands.Command_Stats;
import de.devofvictory.skykitpvp.invs.DalyLootChest;
import de.devofvictory.skykitpvp.listeners.Listener_OnBuild;
import de.devofvictory.skykitpvp.listeners.Listener_OnCraftItemEvent;
import de.devofvictory.skykitpvp.listeners.Listener_OnDamage;
import de.devofvictory.skykitpvp.listeners.Listener_OnDamageByEntity;
import de.devofvictory.skykitpvp.listeners.Listener_OnDebug;
import de.devofvictory.skykitpvp.listeners.Listener_OnDrop;
import de.devofvictory.skykitpvp.listeners.Listener_OnEntityDeath;
import de.devofvictory.skykitpvp.listeners.Listener_OnEntityTarget;
import de.devofvictory.skykitpvp.listeners.Listener_OnEntiyInteract;
import de.devofvictory.skykitpvp.listeners.Listener_OnHanging_Break;
import de.devofvictory.skykitpvp.listeners.Listener_OnHungerChange;
import de.devofvictory.skykitpvp.listeners.Listener_OnInteract;
import de.devofvictory.skykitpvp.listeners.Listener_OnInteract_PerformSuperpower;
import de.devofvictory.skykitpvp.listeners.Listener_OnInvClick_Villager;
import de.devofvictory.skykitpvp.listeners.Listener_OnInvClick_Witch;
import de.devofvictory.skykitpvp.listeners.Listener_OnInventoryInteract;
import de.devofvictory.skykitpvp.listeners.Listener_OnItemPickUp;
import de.devofvictory.skykitpvp.listeners.Listener_OnItemSpawn;
import de.devofvictory.skykitpvp.listeners.Listener_OnJoinQuit;
import de.devofvictory.skykitpvp.listeners.Listener_OnMove;
import de.devofvictory.skykitpvp.listeners.Listener_OnPlayerDeath;
import de.devofvictory.skykitpvp.listeners.Listener_OnProjectileThrow;
import de.devofvictory.skykitpvp.listeners.Listener_OnSwapHand;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPower;
import de.devofvictory.skykitpvp.specialitems.CoinTracker;
import de.devofvictory.skykitpvp.specialitems.Granate;
import de.devofvictory.skykitpvp.specialitems.Slimeball;
import de.devofvictory.skykitpvp.superpowers.ARROW_RAIN;
import de.devofvictory.skykitpvp.superpowers.BAT_DISGUISE;
import de.devofvictory.skykitpvp.superpowers.BEE_ATTACK;
import de.devofvictory.skykitpvp.superpowers.FIRE_BALL;
import de.devofvictory.skykitpvp.superpowers.FIRE_RUNNER;
import de.devofvictory.skykitpvp.superpowers.HEALING_TREE;
import de.devofvictory.skykitpvp.superpowers.LIGHTNING;
import de.devofvictory.skykitpvp.superpowers.WOLF_ATTACK;
import de.devofvictory.skykitpvp.utils.ItemFrameManager;
import de.devofvictory.skykitpvp.utils.ItemStackSeralization;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.StatsManager;
import de.devofvictory.skykitpvp.utils.Variables;
import de.devofvictory.skykitpvp.utils.databasemanager.enums.OutputMessage;
import de.devofvictory.skykitpvp.utils.databasemanager.objects.DatabaseConnection;

public class Main extends JavaPlugin {

	public static String Prefix = Variables.prefix;

	private static Main plugin;
	
	private static DatabaseConnection mysqlConnection;
	
	private static File configFile;
	private static FileConfiguration cfg;
	
	private static File kitsFile;
	private static FileConfiguration kitsCfg;
	
	private static File locsFile;
	private static FileConfiguration locsCfg;
	
	private static File invsFile;
	private static FileConfiguration invsCfg;

	@Override
	public void onEnable() {
		plugin = this;
		
		loadDefaults();
		loadFiles();
		loadVariables();
		loadInventories();
		loadKits();
		loadLocations();
		StatsManager.importTheFiveBest();
		
		setupMysqlConnection();
		registerCommands();
		registerListeners();
		setUpWorld();
		ItemFrameManager.registerItemFrames();
		
		
		
		
		if (Variables.itemFrames.size() > Variables.itemFrameAmount)
			ItemFrameManager.spawnFirstGold();
	}
	
	private void loadLocations() {
		
		double spawnX = getLocsCfg().getDouble("SpawnLocation.X");
		double spawnY = getLocsCfg().getDouble("SpawnLocation.Y");
		double spawnZ = getLocsCfg().getDouble("SpawnLocation.Z");
		String spawnWorld = getLocsCfg().getString("SpawnLocation.World");
		long spawnYaw = getLocsCfg().getLong("SpawnLocation.Yaw");
		long spawnPitch = getLocsCfg().getLong("SpawnLocation.Pitch");
		
		Variables.spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, spawnYaw, spawnPitch);
		
		
		double lootX = getLocsCfg().getDouble("LootLocation.X");
		double lootY = getLocsCfg().getDouble("LootLocation.Y");
		double lootZ = getLocsCfg().getDouble("LootLocation.Z");
		String lootWorld = getLocsCfg().getString("SpawnLocation.World");
		
		Variables.dailyloot_location = new Location(Bukkit.getWorld(lootWorld), lootX, lootY, lootZ);
		
		double skull1X = getLocsCfg().getDouble("Stats.Skulls.1.X");
		double skull1Y = getLocsCfg().getDouble("Stats.Skulls.1.Y");
		double skull1Z = getLocsCfg().getDouble("Stats.Skulls.1.Z");
		String skull1World = getLocsCfg().getString("Stats.Skulls.1.World");
		Location skull1Loc = new Location(Bukkit.getWorld(skull1World), skull1X, skull1Y, skull1Z);
		Variables.skullLocs[0] = skull1Loc;
		
		double skull2X = getLocsCfg().getDouble("Stats.Skulls.2.X");
		double skull2Y = getLocsCfg().getDouble("Stats.Skulls.2.Y");
		double skull2Z = getLocsCfg().getDouble("Stats.Skulls.2.Z");
		String skull2World = getLocsCfg().getString("Stats.Skulls.2.World");
		Location skull2Loc = new Location(Bukkit.getWorld(skull2World), skull2X, skull2Y, skull2Z);
		Variables.skullLocs[1] = skull2Loc;
		
		double skull3X = getLocsCfg().getDouble("Stats.Skulls.3.X");
		double skull3Y = getLocsCfg().getDouble("Stats.Skulls.3.Y");
		double skull3Z = getLocsCfg().getDouble("Stats.Skulls.3.Z");
		String skull3World = getLocsCfg().getString("Stats.Skulls.3.World");
		Location skull3Loc = new Location(Bukkit.getWorld(skull3World), skull3X, skull3Y, skull3Z);
		Variables.skullLocs[2] = skull3Loc;
		
		double skull4X = getLocsCfg().getDouble("Stats.Skulls.4.X");
		double skull4Y = getLocsCfg().getDouble("Stats.Skulls.4.Y");
		double skull4Z = getLocsCfg().getDouble("Stats.Skulls.4.Z");
		String skull4World = getLocsCfg().getString("Stats.Skulls.4.World");
		Location skull4Loc = new Location(Bukkit.getWorld(skull4World), skull4X, skull4Y, skull4Z);
		Variables.skullLocs[3] = skull4Loc;
		
		double skull5X = getLocsCfg().getDouble("Stats.Skulls.5.X");
		double skull5Y = getLocsCfg().getDouble("Stats.Skulls.5.Y");
		double skull5Z = getLocsCfg().getDouble("Stats.Skulls.5.Z");
		String skull5World = getLocsCfg().getString("Stats.Skulls.5.World");
		Location skull5Loc = new Location(Bukkit.getWorld(skull5World), skull5X, skull5Y, skull5Z);
		Variables.skullLocs[4] = skull5Loc;
		
		
		
		double sign1X = getLocsCfg().getDouble("Stats.Signs.1.X");
		double sign1Y = getLocsCfg().getDouble("Stats.Signs.1.Y");
		double sign1Z = getLocsCfg().getDouble("Stats.Signs.1.Z");
		String sign1World = getLocsCfg().getString("Stats.Signs.1.World");
		Location sign1Loc = new Location(Bukkit.getWorld(sign1World), sign1X, sign1Y, sign1Z);
		Variables.signLocs[0] = sign1Loc;
		
		double sign2X = getLocsCfg().getDouble("Stats.Signs.2.X");
		double sign2Y = getLocsCfg().getDouble("Stats.Signs.2.Y");
		double sign2Z = getLocsCfg().getDouble("Stats.Signs.2.Z");
		String sign2World = getLocsCfg().getString("Stats.Signs.2.World");
		Location sign2Loc = new Location(Bukkit.getWorld(sign2World), sign2X, sign2Y, sign2Z);
		Variables.signLocs[1] = sign2Loc;
		
		double sign3X = getLocsCfg().getDouble("Stats.Signs.3.X");
		double sign3Y = getLocsCfg().getDouble("Stats.Signs.3.Y");
		double sign3Z = getLocsCfg().getDouble("Stats.Signs.3.Z");
		String sign3World = getLocsCfg().getString("Stats.Signs.3.World");
		Location sign3Loc = new Location(Bukkit.getWorld(sign3World), sign3X, sign3Y, sign3Z);
		Variables.signLocs[2] = sign3Loc;
		
		double sign4X = getLocsCfg().getDouble("Stats.Signs.4.X");
		double sign4Y = getLocsCfg().getDouble("Stats.Signs.4.Y");
		double sign4Z = getLocsCfg().getDouble("Stats.Signs.4.Z");
		String sign4World = getLocsCfg().getString("Stats.Signs.4.World");
		Location sign4Loc = new Location(Bukkit.getWorld(sign4World), sign4X, sign4Y, sign4Z);
		Variables.signLocs[3] = sign4Loc;
		
		double sign5X = getLocsCfg().getDouble("Stats.Signs.5.X");
		double sign5Y = getLocsCfg().getDouble("Stats.Signs.5.Y");
		double sign5Z = getLocsCfg().getDouble("Stats.Signs.5.Z");
		String sign5World = getLocsCfg().getString("Stats.Signs.5.World");
		Location sign5Loc = new Location(Bukkit.getWorld(sign5World), sign5X, sign5Y, sign5Z);
		Variables.signLocs[4] = sign5Loc;
		
		
		
		
	}

	@SuppressWarnings("deprecation")
	private void setUpWorld() {
		for (Entity en : Variables.getSpawnLocation().getWorld().getEntities()) {
			if (en.getType() != EntityType.PLAYER && en.getType() != EntityType.ARMOR_STAND && en.getType() != EntityType.ITEM_FRAME && en.getType() != EntityType.ZOMBIE && en.getType() != EntityType.WANDERING_TRADER && en.getType() != EntityType.WITCH) {
				en.remove();
			}
		}
		
		Variables.getSpawnLocation().getWorld().setGameRuleValue("doMobSpawning", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("doWeatherCycle", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("doDaylightCycle", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("doTraderSpawning", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("mobGriefing", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("keepInventory", "true");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("doFireTick", "false");
		Variables.getSpawnLocation().getWorld().setGameRuleValue("announceAdvancements", "false");
		
		Variables.getSpawnLocation().getWorld().setThundering(false);
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.setLevel(0);
			all.setExp(0);
			all.getInventory().clear();
			all.sendMessage(Main.Prefix+"§eBitte wähle dein Kit erneut aus!");
		}
		
	}
	
	private static void loadFiles() {
		configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
		cfg = YamlConfiguration.loadConfiguration(configFile);
		
		kitsFile = new File(Main.getInstance().getDataFolder(), "kits.yml");
		kitsCfg = YamlConfiguration.loadConfiguration(kitsFile);
		
		locsFile = new File(Main.getInstance().getDataFolder(), "locations.yml");
		locsCfg = YamlConfiguration.loadConfiguration(locsFile);
		
		invsFile = new File(Main.getInstance().getDataFolder(), "inventories.yml");
		invsCfg = YamlConfiguration.loadConfiguration(invsFile);
	}

	private static void loadVariables() {
		
		Variables.mysql_host = getCfg().getString("MySQL.host");
		Variables.mysql_user = getCfg().getString("MySQL.user");
		Variables.mysql_password = getCfg().getString("MySQL.password");
		Variables.mysql_database = getCfg().getString("MySQL.database");
		
		Variables.signLine0 = getCfg().getString("SignLayout.0").replace('&', '§');
		Variables.signLine1 = getCfg().getString("SignLayout.1").replace('&', '§');
		Variables.signLine2 = getCfg().getString("SignLayout.2").replace('&', '§');
		Variables.signLine3 = getCfg().getString("SignLayout.3").replace('&', '§');
		
		Variables.dailyloot_cooldown = getCfg().getLong("DailyChest.Cooldown");
		Variables.dailyloot_from = getCfg().getInt("DailyChest.Minimim");
		Variables.dailyloot_to = getCfg().getInt("DailyChest.Maximum");
		
		Variables.villagerName = getCfg().getString("NpcNames.Villager").replace('&', '§');
		Variables.zombieName = getCfg().getString("NpcNames.Zombie").replace('&', '§');
		Variables.witchName = getCfg().getString("NpcNames.Witch").replace('&', '§');
		
		Variables.prefix = getCfg().getString("Prefix").replace('&', '§');
		Main.Prefix = Variables.prefix;
		Variables.falldamage = getCfg().getBoolean("FallDamage");
		Variables.jumpPadPower = getCfg().getDouble("JumpPadPower");
		Variables.pvpHeight = getCfg().getInt("PvPHeight");
		Variables.deathHeight = getCfg().getInt("DeathHight");
		Variables.coinsPerKill = getCfg().getInt("CoinsPerKill");
		Variables.coinsPerFrame = getCfg().getInt("CoinsPerFrame");
		Variables.itemFrameAmount = getCfg().getInt("ItemFrameAmount");
		
	}
	
	public static File getConfigFile() {
		return configFile;
	}
	
	public static File getKitsFile() {
		return kitsFile;
	}
	
	public static FileConfiguration getKitsCfg() {
		return kitsCfg;
	}
	
	public static File getLocsFile() {
		return locsFile;
	}
	
	public static FileConfiguration getLocsCfg() {

		return locsCfg;
	}
	
	public static File getInvsFile() {
		return invsFile;
	}
	
	public static FileConfiguration getInvsCfg() {
		return invsCfg;
	}
	
	public static void reloadCfg() {
		try {
			getCfg().load(getConfigFile());
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadVariables();
	}
	
	public static void reloadKits() {
		
		KitManager.unregisterAllKits();
		
		try {
			getKitsCfg().load(getKitsFile());
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadKits();
	}

	public static FileConfiguration getCfg() {
		return cfg;
	}

	private void setupMysqlConnection() {
		mysqlConnection = new DatabaseConnection(Variables.mysql_host, Variables.mysql_database, Variables.mysql_user, Variables.mysql_password);
		mysqlConnection.setOutputMessage(OutputMessage.ONLY_ERRORS);
		mysqlConnection.connect();
		
		ArrayList<String> playerstatsValues = new ArrayList<String>();
		playerstatsValues.add("uuid VARCHAR(100)");
		playerstatsValues.add("lastname VARCHAR(16)");
		playerstatsValues.add("kills VARCHAR(50)");
		playerstatsValues.add("deaths VARCHAR(50)");
		
		mysqlConnection.createTableIfNotExist("playerstats", playerstatsValues, "uuid");
		
		ArrayList<String> kitLevelValues = new ArrayList<String>();
		kitLevelValues.add("uuid VARCHAR(100)");
		kitLevelValues.add("lastname VARCHAR(16)");
		
		for (Kit kit : KitManager.getRegisteredKits()) {
			String uniqueName = kit.getUniqueName();
			kitLevelValues.add(uniqueName+" VARCHAR(50)");
		}
		
		mysqlConnection.createTableIfNotExist("playerkitlevels", kitLevelValues, "uuid");
		
		
		ArrayList<String> kitKillsValues = new ArrayList<String>();
		kitKillsValues.add("uuid VARCHAR(100)");
		kitKillsValues.add("lastname VARCHAR(16)");
		
		for (Kit kit : KitManager.getRegisteredKits()) {
			String uniqueName = kit.getUniqueName();
			kitKillsValues.add(uniqueName+" VARCHAR(50)");
		}
		
		mysqlConnection.createTableIfNotExist("playerkitkills", kitKillsValues, "uuid");
		
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			StatsManager.readFromMysql(all, true);
		}
		
	}

	private static void loadKits() {
		
		for (String key : getKitsCfg().getKeys(false)) {
			
			String displayName;
			String uniqueName;
			int price;
			SuperPower superpower;
			Material displayType;
			
			displayName = getKitsCfg().getString(key+".DisplayName").replace('&', '§');
			uniqueName = getKitsCfg().getString(key+".UniqueName");
			price = getKitsCfg().getInt(key+".Price");
			superpower = SuperPower.valueOf(getKitsCfg().getString(key+".SuperPower"));
			displayType = Material.valueOf(getKitsCfg().getString(key+".DisplayType"));
			
			Kit kit = new Kit(uniqueName, displayName, price, displayType, superpower);
			
			int level = 1;
			
			while (getKitsCfg().isSet(key+".Levels."+level+".MinKillsForUpgrade")) {
				
				int minKillsForUpgrade = getKitsCfg().getInt(key+".Levels."+level+".MinKillsForUpgrade");
				int regenerationTime = getKitsCfg().getInt(key+".Levels."+level+".RegenerationTime");
				String inventoryName = getKitsCfg().getString(key+".Levels."+level+".Inventory");
				
				KitLevel kitLevel = new KitLevel(level, minKillsForUpgrade, regenerationTime, inventoryName);
				List<String> variableValues = getKitsCfg().getStringList(key+".Levels."+level+".Variables");
				
				for (String string : variableValues) {
					String[] splitted = string.split("=");
					String varKey = splitted[0];
					String varValue = splitted[1];
					
					kitLevel.setVariableValue(varKey, varValue);
				}
				kit.addKitLevel(kitLevel);
				
				level++;
			}
			
			KitManager.registerKit(kit);
		}
		
	}

	private void loadDefaults() {
		getInstance().saveResource("config.yml", false);
		getInstance().saveResource("kits.yml", false);
		getInstance().saveResource("locations.yml", false);
		getInstance().saveResource("inventories.yml", false);
		
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoinQuit(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuild(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDamage(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnMove(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDamageByEntity(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnEntiyInteract(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnHanging_Break(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnEntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnPlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDebug(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInvClick_Villager(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnHungerChange(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDrop(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInventoryInteract(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInteract(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInteract_PerformSuperpower(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInvClick_Witch(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnItemPickUp(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnItemSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnProjectileThrow(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnEntityTarget(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnSwapHand(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnCraftItemEvent(), this);
		
		Bukkit.getPluginManager().registerEvents(new Command_Spawn(), this);
		
		Bukkit.getPluginManager().registerEvents(new CoinTracker(), this);
		Bukkit.getPluginManager().registerEvents(new Granate(), this);
		Bukkit.getPluginManager().registerEvents(new Slimeball(), this);
		Bukkit.getPluginManager().registerEvents(new DalyLootChest(), this);
		
		Bukkit.getPluginManager().registerEvents(new HEALING_TREE(), this);
		Bukkit.getPluginManager().registerEvents(new FIRE_RUNNER(), this);
		Bukkit.getPluginManager().registerEvents(new FIRE_BALL(), this);
		Bukkit.getPluginManager().registerEvents(new ARROW_RAIN(), this);
		Bukkit.getPluginManager().registerEvents(new BEE_ATTACK(), this);
		Bukkit.getPluginManager().registerEvents(new WOLF_ATTACK(), this);
		Bukkit.getPluginManager().registerEvents(new LIGHTNING(), this);
		Bukkit.getPluginManager().registerEvents(new BAT_DISGUISE(), this);

	}

	private void registerCommands() {
		getInstance().getCommand("build").setExecutor(new Command_Build());
		getInstance().getCommand("setup").setExecutor(new Command_Setup());
		getInstance().getCommand("setkit").setExecutor(new Command_SetKit());
		getInstance().getCommand("stats").setExecutor(new Command_Stats());
		getInstance().getCommand("spawn").setExecutor(new Command_Spawn());

	}

	private void loadInventories() {
		for (String key : getInvsCfg().getKeys(false)) {
			String value = getInvsCfg().getString(key);
			try {
				Inventory inv = ItemStackSeralization.fromBase64(value);
				Variables.inventories.put(key, inv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private void saveInventories() {
		
		
		for (String key : Variables.inventories.keySet()) {
			String base64 = ItemStackSeralization.toBase64(Variables.inventories.get(key));
			getInvsCfg().set(key, base64);
		}
		
		try {
			getInvsCfg().save(getInvsFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			StatsManager.saveToMysql(all, false);
		}
		
		saveLocations();
		saveInventories();
	}

	private void saveLocations() {
		
		getLocsCfg().set("SpawnLocation.X", Variables.spawnLocation.getX());
		getLocsCfg().set("SpawnLocation.Y", Variables.spawnLocation.getY());
		getLocsCfg().set("SpawnLocation.Z", Variables.spawnLocation.getZ());
		getLocsCfg().set("SpawnLocation.Yaw", Variables.spawnLocation.getYaw());
		getLocsCfg().set("SpawnLocation.Pitch", Variables.spawnLocation.getPitch());
		getLocsCfg().set("SpawnLocation.World", Variables.spawnLocation.getWorld().getName());
		
		getLocsCfg().set("LootLocation.X", Variables.dailyloot_location.getX());
		getLocsCfg().set("LootLocation.Y", Variables.dailyloot_location.getY());
		getLocsCfg().set("LootLocation.Z", Variables.dailyloot_location.getZ());
		getLocsCfg().set("LootLocation.World", Variables.dailyloot_location.getWorld().getName());
		
		for (int i = 0; i<Variables.skullLocs.length; i++) {
			getLocsCfg().set("Stats.Skulls."+(i+1)+".X", Variables.skullLocs[i].getX());
			getLocsCfg().set("Stats.Skulls."+(i+1)+".Y", Variables.skullLocs[i].getY());
			getLocsCfg().set("Stats.Skulls."+(i+1)+".Z", Variables.skullLocs[i].getZ());
			getLocsCfg().set("Stats.Skulls."+(i+1)+".World", Variables.skullLocs[i].getWorld().getName());
		}
		
		for (int i = 0; i<Variables.signLocs.length; i++) {
			getLocsCfg().set("Stats.Signs."+(i+1)+".X", Variables.signLocs[i].getX());
			getLocsCfg().set("Stats.Signs."+(i+1)+".Y", Variables.signLocs[i].getY());
			getLocsCfg().set("Stats.Signs."+(i+1)+".Z", Variables.signLocs[i].getZ());
			getLocsCfg().set("Stats.Signs."+(i+1)+".World", Variables.signLocs[i].getWorld().getName());
		}
		
		try {
			getLocsCfg().save(getLocsFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String getConsolePrefix() {
		return "[SkyKitPvp] ";
	}

	public static Main getInstance() {
		return plugin;
	}
	
	public static DatabaseConnection getMysqlConnection() {
		return mysqlConnection;
	}

}
