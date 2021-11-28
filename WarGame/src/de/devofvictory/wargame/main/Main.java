package de.devofvictory.wargame.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.chilipro.mt.main.MinigameTableMain;
import de.chilipro.mt.objects.MinigameTable;
import de.chilipro.mt.objects.PlayerUUIDStats;
import de.devofvictory.wargame.commands.Command_Infinitymode;
import de.devofvictory.wargame.commands.Command_Restart;
import de.devofvictory.wargame.commands.Command_SetTime;
import de.devofvictory.wargame.commands.Command_SpawnLoot;
import de.devofvictory.wargame.commands.Command_Start;
import de.devofvictory.wargame.commands.Command_Stats;
import de.devofvictory.wargame.commands.Command_Trust;
import de.devofvictory.wargame.commands.Command_WarItems;
import de.devofvictory.wargame.inventorys.INV_PlayerSpind;
import de.devofvictory.wargame.inventorys.Inv_Shop_Skins;
import de.devofvictory.wargame.inventorys.Inv_Trader;
import de.devofvictory.wargame.items.AK;
import de.devofvictory.wargame.items.C4;
import de.devofvictory.wargame.items.ChickenWing;
import de.devofvictory.wargame.items.Crown;
import de.devofvictory.wargame.items.Granate;
import de.devofvictory.wargame.items.JetPack;
import de.devofvictory.wargame.items.LaubBlaeser;
import de.devofvictory.wargame.items.MachineGun;
import de.devofvictory.wargame.items.MultiTool;
import de.devofvictory.wargame.items.Pistol;
import de.devofvictory.wargame.items.RocketLauncher;
import de.devofvictory.wargame.items.SchildTrank;
import de.devofvictory.wargame.items.SchrotFlinte;
import de.devofvictory.wargame.items.SlimeBoots;
import de.devofvictory.wargame.items.Sniper;
import de.devofvictory.wargame.items.Superladder;
import de.devofvictory.wargame.items.WhiteRide;
import de.devofvictory.wargame.items.WurfMine;
import de.devofvictory.wargame.listeners.Listener_OnBuild;
import de.devofvictory.wargame.listeners.Listener_OnBuildOutsideBorder;
import de.devofvictory.wargame.listeners.Listener_OnChestOpen;
import de.devofvictory.wargame.listeners.Listener_OnCommand;
import de.devofvictory.wargame.listeners.Listener_OnDamage;
import de.devofvictory.wargame.listeners.Listener_OnDeath;
import de.devofvictory.wargame.listeners.Listener_OnEntityLeave;
import de.devofvictory.wargame.listeners.Listener_OnFlyKick;
import de.devofvictory.wargame.listeners.Listener_OnHeld;
import de.devofvictory.wargame.listeners.Listener_OnInteract;
import de.devofvictory.wargame.listeners.Listener_OnInvClick;
import de.devofvictory.wargame.listeners.Listener_OnInventoryClose;
import de.devofvictory.wargame.listeners.Listener_OnJoin;
import de.devofvictory.wargame.listeners.Listener_OnMove;
import de.devofvictory.wargame.listeners.Listener_OnProjectileHit;
import de.devofvictory.wargame.listeners.Listener_OnQuit;
import de.devofvictory.wargame.listeners.Listener_OnServerListPing;
import de.devofvictory.wargame.listeners.Listener_OnShopOpen;
import de.devofvictory.wargame.listeners.Listener_OnSpindOpen;
import de.devofvictory.wargame.utils.Code;
import de.devofvictory.wargame.utils.FileManager;
import de.devofvictory.wargame.utils.Holograms;
import de.devofvictory.wargame.utils.LootChests;
import de.devofvictory.wargame.utils.NPCManager;
import de.devofvictory.wargame.utils.Skin;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.SpindManager;
import de.devofvictory.wargame.utils.TraderShop;
import de.devofvictory.wargame.utils.Vote;

public class Main extends JavaPlugin{
	
	public static String Prefix = "§3•§b●§3§lWar§b§lGame §8» ";
	
	final static int two = 0;
	
	public static boolean isGameRunning = false;
	public static boolean allowDamage = false;
	public static boolean isMatchRunning = false;
	
	static MinigameTable minigame;
	
	public static List<String> cfglist;
	
	public static String code = "#*00*#";
	
	public static HashMap<String, String> lastBlaeserHit = new HashMap<>();
	//					  target  hitter
	
	public static NPCManager npc = null;
	
	private static String consolePrefix = "[DatabseManager] ";
	
	
	private static Main plugin;
	
	public static boolean isTraderSpawned = false;
	
	static PlayerUUIDStats[] fiveBest;
	
	public static PlayerUUIDStats[] getFiveBest() {
		return fiveBest;
	}
	
	
	
	public static Holograms holo;
	
	@Override
	public void onEnable() {
		
		minigame = new MinigameTable("wargame");
		
		MinigameTableMain.getInstance().registerMinigame(minigame);
		
		
		Bukkit.getConsoleSender().sendMessage(Prefix+"§aPlugin loaded successfully!");
		
		plugin = this;
		
//		FileManager.create("stats");
//		StatsManager.importStats();
		
		
		FileManager.create("skins");
		TraderShop.importShop();
		
		FileManager.create("spinds");
		SpindManager.importSpinds();
		
//		FileManager.create("skids");
//		TraderShop.importShop();
		
		getCommand("spawnloot").setExecutor(new Command_SpawnLoot());
		getCommand("waritems").setExecutor(new Command_WarItems());
		getCommand("infinitymode").setExecutor(new Command_Infinitymode());
		getCommand("start").setExecutor(new Command_Start());
		getCommand("settime").setExecutor(new Command_SetTime());
		getCommand("trust").setExecutor(new Command_Trust());
		getCommand("stats").setExecutor(new Command_Stats());
		getCommand("warnedrestart").setExecutor(new Command_Restart());
		
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getPluginManager().registerEvents(new WurfMine(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new LaubBlaeser(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInvClick(), this);
		Bukkit.getPluginManager().registerEvents(new Pistol(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDeath(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnMove(), this);
		Bukkit.getPluginManager().registerEvents(new Sniper(), this);
		Bukkit.getPluginManager().registerEvents(new RocketLauncher(), this);
		Bukkit.getPluginManager().registerEvents(new MachineGun(), this);
		Bukkit.getPluginManager().registerEvents(new AK(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnProjectileHit(), this);
		Bukkit.getPluginManager().registerEvents(new SchrotFlinte(), this);
		Bukkit.getPluginManager().registerEvents(new Granate(), this);
		Bukkit.getPluginManager().registerEvents(new WhiteRide(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnQuit(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDamage(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuild(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnChestOpen(), this);
		Bukkit.getPluginManager().registerEvents(new MultiTool(), this);
		Bukkit.getPluginManager().registerEvents(new ChickenWing(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnEntityLeave(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInteract(), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorClass(), this);
		Bukkit.getPluginManager().registerEvents(new SchildTrank(), this);
		Bukkit.getPluginManager().registerEvents(new Code(), this);
		Bukkit.getPluginManager().registerEvents(new Vote(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnCommand(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInventoryClose(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnHeld(), this);
		Bukkit.getPluginManager().registerEvents(new Skin(), this);
		Bukkit.getPluginManager().registerEvents(new JetPack(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnFlyKick(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnShopOpen(), this);
		Bukkit.getPluginManager().registerEvents(new Inv_Trader(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnServerListPing(), this);
		Bukkit.getPluginManager().registerEvents(new Inv_Shop_Skins(), this);
		Bukkit.getPluginManager().registerEvents(new Crown(), this);
		Bukkit.getPluginManager().registerEvents(new SlimeBoots(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuildOutsideBorder(), this);
		Bukkit.getPluginManager().registerEvents(new C4(), this);
		Bukkit.getPluginManager().registerEvents(new INV_PlayerSpind(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnSpindOpen(), this);
		Bukkit.getPluginManager().registerEvents(new LootChests(), this);
		Bukkit.getPluginManager().registerEvents(new Superladder(), this);
		
		reloadConfig();
		saveConfig();
		getConfig().getStringList("locs").add("466 32 -350");
		
		Vote.timeVote.put("Tag", 0);
		Vote.timeVote.put("Nacht", 0);
		
		Vote.livesVote.put(1, 0);
		Vote.livesVote.put(3, 0);
		Vote.livesVote.put(5, 0);
		
		//new BossBarUpdater(new BossBar()).runTaskTimer(plugin, 0, 20);
		
		try {
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("WarGame").unregister();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			Pistol.isRealoding.put(all, false);
			Sniper.isRealoding.put(all, false);
			RocketLauncher.isRealoding.put(all, false);
			MachineGun.isRealoding.put(all, false);
			AK.isRealoding.put(all, false);
			SchrotFlinte.isRealoding.put(all, false);
			Pistol.lastShoot.put(all.getName(), Long.valueOf(System.currentTimeMillis()));
			AK.lastShoot.put(all.getName(), Long.valueOf(System.currentTimeMillis()));
			SchrotFlinte.lastShoot.put(all.getName(), Long.valueOf(System.currentTimeMillis()));
			WhiteRide.isRealoding.put(all, false);
			SpectatorClass.specDelay.put(all.getName(), Long.valueOf(System.currentTimeMillis()));
			
			AK.shootsLeft.put(all, AK.shoots);
			MachineGun.shootsLeft.put(all, MachineGun.shoots);
			Pistol.shootsLeft.put(all, Pistol.shoots);
			RocketLauncher.shootsLeft.put(all, RocketLauncher.shoots);
			Sniper.shootsLeft.put(all, Sniper.shoots);
			WhiteRide.shootsLeft.put(all, WhiteRide.shoots);
			SchrotFlinte.shootsLeft.put(all, SchrotFlinte.shoots);
			
			
		}
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					world.setTime(6000);
					world.setStorm(false);
					world.setThundering(false);
					world.setWeatherDuration(Integer.MAX_VALUE);
					world.setGameRuleValue("doDaylightCycle", "false");
					for (Entity en : world.getEntities()) {
						if (en.getType() != EntityType.PLAYER && en.getType() != EntityType.ARMOR_STAND && en.getType() != EntityType.ITEM_FRAME)
						en.remove();
					}
					
					
					
					//-2.467 20 50.700
				}
//				StatsManager.setLobbyStats();
				Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
				
				
			}
		}, 1*20);
			
		////////////////////////////////////////////////////////
			
			
		fiveBest = minigame.getFiveBest();
		
		
	}
	
	public static String getConsolePrefix() {
		return consolePrefix;
	}
	
	public static MinigameTable getMinigame() {
		return minigame;
	}
	
	
	@Override
	public void onDisable() {
		SpindManager.exportSpinds();
	}
	
	public static Main getInstance() {
		return plugin;
	}
	
	
	public static void connectToServer(Player p, String server) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		}catch (IOException error){
			error.printStackTrace();
		}
		p.sendPluginMessage(Main.getInstance(), "BungeeCord", baos.toByteArray());
	}

}
