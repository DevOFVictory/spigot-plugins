package de.devofvictory.wargame.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;


@SuppressWarnings("deprecation")
public class StartGame implements Listener{
	
	public static List<Player> living = new ArrayList<>();
	public static List<Player> spectators = new ArrayList<>();
	
	public static HashMap<String, Creature> ride = new HashMap<>();
	
	public static Location middle = new Location(Bukkit.getWorld("map"), 430, 100, -409);
	
	public static HashMap<Player, Integer> livesLeft = new HashMap<>();
	
	
	public StartGame(int playerAmount) {
		if (!Main.isGameRunning) {
			Skin.changeAllSkins();
			Bukkit.broadcastMessage(Main.Prefix+"§aDas Spiel wurde gestartet!");
			Bukkit.broadcastMessage(Main.Prefix+"§eTiltedTowers-Map gebaut von SeanBits.");
			Main.isGameRunning = true;
			Main.allowDamage = true;
			Main.isMatchRunning = true;
			
			setDefaultChests();
			
			Vote.setResult();
			Bukkit.broadcastMessage(Main.Prefix+"§3Tageszeit: "+Vote.votedTime);
			Bukkit.broadcastMessage(Main.Prefix+"§3Lebensanzahl: "+Vote.votedLives);
			
			WorldBorder border = Bukkit.getWorld("map").getWorldBorder();
			border.setCenter(middle.getX(), middle.getZ());
			border.setSize(150*2);
			
			if (Vote.votedTime.equalsIgnoreCase("Nacht")) {
				Bukkit.getWorld("map").setTime(18000);
			}else {
				Bukkit.getWorld("map").setTime(6000);
			}
			
			Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
			Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
			Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
			Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
			Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
			
		for (Player all : Bukkit.getOnlinePlayers()) {
			
			switch (Vote.votedLives) {
			case 1:
				livesLeft.put(all, 1);
				break;

			case 3:
				livesLeft.put(all, 3);
				break;
			case 5:
				livesLeft.put(all, 5);
				break;
			}
			
			new ClearUtil(all);
			Location loc = new Location(middle.getWorld(), ThreadLocalRandom.current().nextInt(middle.getBlockX()-113, middle.getBlockX()+113), 100, ThreadLocalRandom.current().nextInt(middle.getBlockZ()-113, middle.getBlockZ()+113));
			all.teleport(loc);
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					living.add(all);
					Chicken c = (Chicken) all.getWorld().spawnCreature(all.getLocation(), CreatureType.CHICKEN);
					c.setPassenger((Entity)all);
					ride.put(all.getName(), c);
					all.getInventory().clear();
					new ClearUtil(all);
					ScoreBoardManager.createScoreboard();
					
					ItemStack shovel = new ItemStack(Material.DIAMOND_SPADE);
					ItemMeta shovelMeta = shovel.getItemMeta();
					shovelMeta.setDisplayName("§6§lMultiTool");
					shovelMeta.spigot().setUnbreakable(true);
					shovel.setItemMeta(shovelMeta);
					all.getInventory().setItem(3, shovel);
					
					ItemStack sword = new ItemStack(Material.WOOD_SWORD);
					ItemMeta swordMeta = sword.getItemMeta();
					swordMeta.setDisplayName("§6§lSchwert");
					swordMeta.spigot().setUnbreakable(true);
					sword.setItemMeta(swordMeta);
					Enchantment ench = Enchantment.DURABILITY;
					sword.addEnchantment(ench, 3);
					all.getInventory().setItem(5, sword);
					
					ItemStack chickenwing = new ItemStack(Material.FEATHER,1);
					ItemMeta chickenwingMeta = chickenwing.getItemMeta();
					chickenwingMeta.setDisplayName("§6§lChickenWing");
					chickenwing.setItemMeta(chickenwingMeta);
					all.getInventory().setItem(4, chickenwing);
					
					all.setMaxHealth(40);
					all.setHealth(20);
					all.setHealthScale(40);
					all.setFoodLevel(40);
					
					Bukkit.getWorld("map").setGameRuleValue("reducedDebugInfo", "true");
					
					NameTagHider.hide(all);
					SpectatorClass.updateInventorsLoop();
					
					
					all.closeInventory();
				}
			}, 10);
			
			all.spigot().setCollidesWithEntities(true);
			
		}
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					if (Main.isMatchRunning) {
					LootChests.spawnLootChest(middle, 113, 20);
					Bukkit.broadcastMessage(Main.Prefix+"§aLootdrops wurden gespawnt!");
					}
				}
			}, 120*20, 120*20);
				
				
		
		}
		startTimer();
	}
	
	
	
	
	
	public static int minutes = 4;
	public static int seconds = 59;
	static int sched = 0;
	
	public static void startTimer() {
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
					seconds--;
				if (seconds <= 0) {
					if (minutes > 0) {
						minutes -=1;
						seconds = 60;
					}else {
						minutes = 0;
						seconds = 0;
						ScoreBoardManager.upDateScoreboard(seconds, minutes, living.size());
						new StartDeathMatch();
						
					}

						
					
				}
				ScoreBoardManager.upDateScoreboard(seconds, minutes, living.size());
			}
			
		}, 20, 1*20);
		
		
	}
	
	

	public static void setDeath(Player p) {
		living.remove(p);
		SpectatorClass.setSpectator(p, p.getKiller());
		p.sendMessage(Main.Prefix+"§4§lDu bist ausgeschieden!");
		
		
		if (living.size() == 1) {
			for (Player living : living) {
				setWinner(living);
			}
		}else if (living.size() == 0){
			Bukkit.shutdown();
		}
		
	}
	
	public static void setTime(int seconds, int minutes) {
		StartGame.seconds = seconds;
		StartGame.minutes = minutes;
	}
	
	public static void setWinner(Player p) {
		

		Bukkit.getServer().broadcastMessage(Main.Prefix+"§5"+p.getName()+" §dhat das Spiel gewonnen!");
		

			new EndGame(p);

	}
	
	public static List<String> allStrings;
	
	private static void setDefaultChests() {
		Main.getInstance().reloadConfig();
		allStrings = Main.getInstance().getConfig().getStringList("locs");
		
		int amount = 35;
		Random r = new Random();
		
		for (int i = 0; i < amount; i++) {
			int randomInt = r.nextInt(allStrings.size());
			
			String s = allStrings.get(randomInt);
			String[] xyz = s.split(" ");
			
			int x = Integer.valueOf(xyz[0]);
			int y = Integer.valueOf(xyz[1]);
			int z = Integer.valueOf(xyz[2]);
			
			Location loc = new Location(Bukkit.getWorld("map"), x, y, z);
			loc.getBlock().setType(Material.EMERALD_BLOCK);
			
		
		}
		
		
	}
	
	

}
