package de.devofvictory.wargame.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreBoardManager {
	
	public static void createScoreboard() {
		
		try {
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("WarGame").unregister();
		}catch (Exception e) {
		}
		
		try {
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("Kills").unregister();
		}catch (Exception e) {
		}
		
		Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective obj = sb.registerNewObjective("WarGame", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§3§lWar§b§lGame");
		Objective killcounter = sb.registerNewObjective("Kills", "playerKillCount");
		killcounter.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		try {
			sb.getTeam("minutes").unregister();
			sb.getTeam("seconds").unregister();
			sb.getTeam("playerLeft").unregister();
			sb.getTeam("placeholder1").unregister();
		} catch (Exception e) {
		}
		Team minutes = sb.registerNewTeam("minutes");
		Team seconds = sb.registerNewTeam("seconds");
		Team playerLeft = sb.registerNewTeam("playerLeft");
		Team placeHolder1 = sb.registerNewTeam("placeholder1");
		
		minutes.setPrefix("§5§lMinuten §8:");
		seconds.setPrefix("§5§lSekunden §8:");
		placeHolder1.setPrefix("§f»");
		playerLeft.setPrefix("§6Lebende §8:");
		
		minutes.addEntry("§1");
		seconds.addEntry("§2");
		placeHolder1.addEntry("§3");
		playerLeft.addEntry("§4");
		
		
		obj.getScore("§2").setScore(4);
		obj.getScore("§1").setScore(3);
		obj.getScore("§3").setScore(2);
		obj.getScore("§4").setScore(1);
		
	}
	
	public static void upDateScoreboard(int secondsx, int minutesx, int living) {
		Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
		Team minutes = sb.getTeam("minutes");
		Team seconds = sb.getTeam("seconds");
		Team playerLeft = sb.getTeam("playerLeft");
		
		
		minutes.setSuffix("§e "+minutesx);
		seconds.setSuffix("§e "+secondsx);
		playerLeft.setSuffix("§e "+living);
	}
	
	public static void setPrefix(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if (sb.getTeam("player") == null) {
			sb.registerNewTeam("player");
		}
		
		Team t = sb.getTeam("player");
		
		t.setPrefix("§a");
		
		t.addEntry(p.getName());
		
			}

}
