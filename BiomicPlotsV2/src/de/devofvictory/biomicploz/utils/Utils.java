package de.devofvictory.biomicploz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.devofvictory.biomicploz.main.Main;

public class Utils {
	
	static HashMap<Player, String> lastMessage = new HashMap<>();
	
	public static void sendNoSpamMsg(Player p, String msg) {
		if (lastMessage.containsKey(p)) {
			
			if (!lastMessage.get(p).equals(msg)) {
				
				if (!msg.startsWith(Main.Prefix))
					msg = Main.Prefix + msg;
				
				p.sendMessage(msg);
				lastMessage.put(p, msg);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						lastMessage.remove(p);
						
					}
				}, 5*20);
			}
		}else {
			
			if (!msg.startsWith(Main.Prefix))
				msg = Main.Prefix + msg;
			
			p.sendMessage(msg);
			lastMessage.put(p, msg);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					lastMessage.remove(p);
					
				}
			}, 5*20);
			
		}
	}
	public static List<String> getStringList(List<UUID> UUIDList) {
		List<String> stringList = new ArrayList<>();
		
		for (UUID uuid : UUIDList) {
			stringList.add(uuid.toString());
		}
		
		return stringList;
		
	}
	
	public static List<UUID> getUUIDList(List<String> list) {
		List<UUID> uuidList = new ArrayList<>();
		
		for (String uuid : list) {
			uuidList.add(UUID.fromString(uuid));
		}
		
		return uuidList;
		
	}
	
	public static List<String> getNamesList(List<UUID> uuidList) {
		List<String> names = new ArrayList<>();
		
		for (UUID uuid : uuidList) {
			String name = getCashedName(uuid);
			if (name != null) {
				names.add(getCashedName(uuid));
			}else {
				names.add("Unbekannt");
			}
		}
		
		return names;
	}
	
	public static String getMergedWithString(Plot plot) {
		
		ArrayList<Plot> mergedOnThis = plot.getMergedOnThisPlots();
		
		if (mergedOnThis.isEmpty()) {
			if (plot.getPlotMergedOn() == null) {
				return "Keinem";
			}else {
				return ""+plot.getPlotMergedOn().getID();
			}
		}else {
			String s = "";
			
			for (int i = 0; i<mergedOnThis.size(); i++) {
				s += ", "+mergedOnThis.get(i).getID();
			}
			
			return s.replaceFirst(", ", "");
		}
	}
	
	public static String getBeautifulList(List<String> stringList) {
		
		String string = "";
		
		for (String i : stringList) {
			string += ", " + i;
		}
		if (!string.isEmpty()) {
			return string.replaceFirst(", ", "");
		}else {
			return "Keiner";
		}
	}
	
	@SuppressWarnings("deprecation")
	public static UUID getCashedUUID(String name) {
		if (Bukkit.getPlayer(name) != null) {
			return Bukkit.getPlayer(name).getUniqueId();
		}else {
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			
			if (op.hasPlayedBefore()) {
				return op.getUniqueId();
			}else {
				return null;
			}
		}	
	}
	
	public static String getCashedName(UUID uuid) {
		if (Bukkit.getPlayer(uuid) != null) {
			return Bukkit.getPlayer(uuid).getName();
		}else {
			OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
			
			if (op != null) {
				return op.getName();
			}else {
				return "Unbekannt";
			}
		}	
	}

		
	
	public static int getDifference(int a, int b) {
		
		int dif = a-b;
		
		if (dif < 0) {
			dif = dif*(-1);
		}
		
		return dif;
	}

}
