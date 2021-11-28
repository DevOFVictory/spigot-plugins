package de.devofvictory.wargame.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;

public class NameTagHider {
	
	public static void hide(Player p) {
		
			if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName") == null) {
				Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("HideName");
			}
			
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName").setNameTagVisibility(NameTagVisibility.NEVER);
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName").setPrefix("§a");
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName").addEntry(p.getName());
	}
	
	public static void show(Player p) {
		if (isHidden(p)) {
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName").removeEntry(p.getName());
			ScoreBoardManager.setPrefix(p);
		}
	}
	
	public static boolean isHidden(Player p) {
		if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("HideName").getEntries().contains(p.getName()))
				return true;
		return false;
	}

}
