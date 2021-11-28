package de.devofvictory.chiliplaytime.utils;

import de.devofvictory.chiliplaytime.main.Main;
import de.devofvictory.chiliplaytime.main.PlaytimeManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DBSaver {
	
	public static void importPlayTime(ProxiedPlayer p) {
		
		try {
			PlaytimeManager.globalPlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "players", "uuid", p.getUniqueId().toString())));
			PlaytimeManager.wargamePlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "wargame", "uuid", p.getUniqueId().toString())));
			PlaytimeManager.knockPlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "knockdown", "uuid", p.getUniqueId().toString())));
			PlaytimeManager.citybuildPlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "citybuild", "uuid", p.getUniqueId().toString())));
			PlaytimeManager.kotcPlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "kotc", "uuid", p.getUniqueId().toString())));
			PlaytimeManager.survivePlaytime.put(p, Integer.parseInt(Main.getMysqlConnection().select("playtime", "survive", "uuid", p.getUniqueId().toString())));
			
		}catch (NumberFormatException ex) {
		}
		
	}
	
	public static void exportPlayTime(ProxiedPlayer p, String group) {
		
		if (Main.getMysqlConnection().select("*", "players", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p)), "uuid", p.getUniqueId().toString());
		}
		
		if (Main.getMysqlConnection().select("*", "wargame", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p, "WarGame")), "uuid", p.getUniqueId().toString());
		}
		
		if (Main.getMysqlConnection().select("*", "knockdown", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p)), "uuid", p.getUniqueId().toString());
		}
		
		if (Main.getMysqlConnection().select("*", "survive", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p)), "uuid", p.getUniqueId().toString());
		}
		
		if (Main.getMysqlConnection().select("*", "kotc", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p)), "uuid", p.getUniqueId().toString());
		}
		
		if (Main.getMysqlConnection().select("*", "citybuild", "uuid", p.getUniqueId().toString()) != null) {
			Main.getMysqlConnection().update("players", "playtime", String.valueOf(PlaytimeManager.getMinutes(p)), "uuid", p.getUniqueId().toString());
		}
		
			
		
		
	}

}
