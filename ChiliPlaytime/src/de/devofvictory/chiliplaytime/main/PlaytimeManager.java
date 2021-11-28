package de.devofvictory.chiliplaytime.main;

import java.util.HashMap;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlaytimeManager {
	
	public static HashMap<ProxiedPlayer, Integer> globalPlaytime = new HashMap<ProxiedPlayer, Integer>();
	public static HashMap<ProxiedPlayer, Integer> wargamePlaytime = new HashMap<ProxiedPlayer, Integer>();
	public static HashMap<ProxiedPlayer, Integer> survivePlaytime = new HashMap<ProxiedPlayer, Integer>();
	public static HashMap<ProxiedPlayer, Integer> knockPlaytime = new HashMap<ProxiedPlayer, Integer>();
	public static HashMap<ProxiedPlayer, Integer> kotcPlaytime = new HashMap<ProxiedPlayer, Integer>();
	public static HashMap<ProxiedPlayer, Integer> citybuildPlaytime = new HashMap<ProxiedPlayer, Integer>();
	
	public static void addMinute(ProxiedPlayer p, String group) {
		switch (group) {
		case "WarGame":
			wargamePlaytime.put(p, wargamePlaytime.get(p)+1);
			break;
		case "Survive":
			survivePlaytime.put(p, survivePlaytime.get(p)+1);
			break;
		case "KnockDown":
			knockPlaytime.put(p, knockPlaytime.get(p)+1);
			break;
		case "KingOfTheCastle":
			kotcPlaytime.put(p, kotcPlaytime.get(p)+1);
			break;
		case "CityBuild":
			citybuildPlaytime.put(p, citybuildPlaytime.get(p)+1);
			break;
		default:
			break;
		}
	}
	
	public static int getMinutes(ProxiedPlayer p, String group) {
		
		switch (group) {
		case "WarGame":
			return wargamePlaytime.get(p);
		case "Survive":
			return survivePlaytime.get(p);
		case "KnockDown":
			return knockPlaytime.get(p);
		case "KingOfTheCastle":
			return kotcPlaytime.get(p);
		case "CityBuild":
			return citybuildPlaytime.get(p);
		default:
			return 0;
		}
	}
	
	public static int getMinutes(ProxiedPlayer p) {
		return globalPlaytime.get(p);
	}
	
	

}
