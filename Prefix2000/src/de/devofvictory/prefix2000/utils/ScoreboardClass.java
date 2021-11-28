package de.devofvictory.prefix2000.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.devofvictory.prefix2000.main.Main;



public class ScoreboardClass {
	
	
	
	static File fileFromPlugin = new File("plugins/PlayTime", "playtimes.yml");
	static FileConfiguration pt = YamlConfiguration.loadConfiguration(fileFromPlugin );
	
	static File customprefixs = new File("plugins/Prefix2000", "customprefixes.yml");
	public static FileConfiguration cp = YamlConfiguration.loadConfiguration(customprefixs );
	
	public static  void saveCfg() {
		try {
			cp.save(customprefixs);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Prefix2000] Ein Fehler beim Speichern der config.yml ist aufgetreten");
		}
	
	}
	public static void loadCfg() {
		if(!customprefixs.exists()) { 
			try {
				customprefixs.createNewFile();
				System.out.println("[Prefix2000] Config wurde erstellt!");
			} catch (IOException e) {
				System.out.println("[Prefix2000] Config wurde geladen!");
			}
}
	}
	public void reloadCfg() {
		reloadCfg();
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static void createScoreboad(Player p) {
		try {p.getScoreboard().getObjective("Scoreboard").unregister();
		}catch (Exception e) {
			
		}
		
		
			Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
			sb.registerNewObjective("Scoreboard", "dummy");
			Objective o = sb.getObjective("Scoreboard");
		
			//PlaceHolders
			int hours = pt.getInt("PlayTimes."+p+".hours");
			int days = pt.getInt("PlayTimes."+p+".days");
		
			String small = Main.permission.getPrimaryGroup(p);
			String big = ""+small.charAt(0);
			big = big.toUpperCase();
			big = big+small.substring(1);
			
			o.setDisplayName("§5§lChiliPro.net");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.getScore("§1Name:").setScore(15);
			o.getScore("§8> §d"+p.getName()).setScore(14);
			o.getScore("§r ").setScore(13);
			o.getScore("§3Geld:").setScore(12);
			o.getScore("§8> §d"+Main.economy.getBalance(p.getName())+" §dChillis").setScore(11);
			o.getScore("§d ").setScore(10);
			o.getScore("§1Spielzeit:").setScore(9);
			o.getScore("§8> §d"+(hours+days*24)+" Stunden").setScore(8);
			o.getScore("§e ").setScore(7);
			o.getScore("§3Rang:").setScore(6);
			o.getScore("§8> §d"+big).setScore(5);
			o.getScore("§a----------------").setScore(4);
			o.getScore("§1TeamSpeak:").setScore(3);
			o.getScore("§8> §cchilipro.de").setScore(2);
			o.getScore(Main.lastline).setScore(1);
			p.setScoreboard(sb);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void upDateScoreboard(Player p) {
		Scoreboard sb = p.getScoreboard();
		
		
		
		try {p.getScoreboard().getObjective("Scoreboard").unregister();
		}catch (Exception e) {
			
		}
		
			sb.registerNewObjective("Scoreboard", "dummy");
			Objective o = sb.getObjective("Scoreboard");
		
			//PlaceHolders
			int hours = pt.getInt("PlayTimes."+p+".hours");
			int days = pt.getInt("PlayTimes."+p+".days");
		
			String small = Main.permission.getPrimaryGroup(p);
			String big = ""+small.charAt(0);
			big = big.toUpperCase();
			big = big+small.substring(1);
			
			o.setDisplayName("§5§lChiliPro.net");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.getScore("§1Name:").setScore(15);
			o.getScore("§8> §d"+p.getName()).setScore(14);
			o.getScore("§r ").setScore(13);
			o.getScore("§3Geld:").setScore(12);
			o.getScore("§8> §d"+Main.economy.getBalance(p.getName())+" §dChillis").setScore(11);
			o.getScore("§d ").setScore(10);
			o.getScore("§1Spielzeit:").setScore(9);
			o.getScore("§8> §d"+(hours+days*24)+" Stunden").setScore(8);
			o.getScore("§e ").setScore(7);
			o.getScore("§3Rang:").setScore(6);
			o.getScore("§8> §d"+big).setScore(5);
			o.getScore("§a----------------").setScore(4);
			o.getScore("§1TeamSpeak:").setScore(3);
			o.getScore("§8> §cchilipro.de").setScore(2);
			o.getScore(Main.lastline).setScore(1);
			p.setScoreboard(sb);
		
	}
}
