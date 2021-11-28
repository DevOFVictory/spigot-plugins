package de.devofvictory.skykitpvp.utils;

import static java.util.stream.Collectors.toMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;

public class StatsManager {
	
	private static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	
	private static ArrayList<String> top5UUIDs = new ArrayList<String>();
	
	private static HashMap<UUID, String> hashedNames = new HashMap<UUID, String>();
	
	public static void setKills(String uuid, int amount) {
		kills.put(uuid, amount);
	}
	
	public static int getKills(String uuid) {
		return kills.containsKey(uuid) ? kills.get(uuid) : 0;
	}
	
	public static void setDeaths(String uuid, int amount) {
		deaths.put(uuid, amount);
	}
	
	public static int getDeaths(String uuid) {
		return deaths.containsKey(uuid) ? deaths.get(uuid) : 0;
	}
	
	public static void addKill(String uuid) {
		setKills(uuid, getKills(uuid)+1);
	}
	
	public static void addDeath(String uuid) {
		setDeaths(uuid, getDeaths(uuid)+1);
	}
	
	public static void readFromMysql(Player p, boolean async) {
		
		final String uuid = p.getUniqueId().toString();
		
		if (async) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if (Main.getMysqlConnection().select("*", "playerstats", "uuid", uuid.toString()) != null) {
					setKills(uuid, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", uuid.toString())));
					setDeaths(uuid, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", uuid.toString())));
				}else {
					setKills(uuid, 0);
					setDeaths(uuid, 0);
				}
				
				
				if (Main.getMysqlConnection().select("*", "playerkitlevels", "uuid", uuid.toString()) != null) {
					
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						int kitLevel = 0;
						try {
							kitLevel = Integer.parseInt(Main.getMysqlConnection().select(kit.getUniqueName(), "playerkitlevels", "uuid", uuid.toString()));
						}catch (NumberFormatException | NullPointerException e) {}
						if (kitLevel > 0) {
							KitManager.addKit(p, kit);
							KitManager.setKitLevel(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, kitLevel);
						}
					}
					
				}else {
					for (Kit kit : KitManager.getRegisteredKits()) {
						KitManager.setKitLevel(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, 0);
						
						if (KitManager.getKitLevel(p, kit) > 0) {
							KitManager.addKit(p, kit);
						}
					}
				}
				
				if (Main.getMysqlConnection().select("*", "playerkitkills", "uuid", uuid.toString()) != null) {
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						
						int kitKills = 0;
						try {
							kitKills = Integer.parseInt(Main.getMysqlConnection().select(kit.getUniqueName(), "playerkitkills", "uuid", uuid.toString()));
						}catch (NumberFormatException | NullPointerException e) {}
						
						KitManager.setKitKills(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, kitKills);
					}
					
				}else {
					for (Kit kit : KitManager.getRegisteredKits()) {
						KitManager.setKitKills(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, 0);
					}
				}
				
			}
		}.runTaskAsynchronously(Main.getInstance());
		
		}else {
			if (Main.getMysqlConnection().select("*", "playerstats", "uuid", uuid.toString()) != null) {
				setKills(uuid, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", uuid.toString())));
				setDeaths(uuid, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", uuid.toString())));
			}else {
				setKills(uuid, 0);
				setDeaths(uuid, 0);
			}
			
			if (Main.getMysqlConnection().select("*", "playerkitlevels", "uuid", uuid.toString()) != null) {
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					
					int kitLevel = 0;
					try {
						kitLevel = Integer.parseInt(Main.getMysqlConnection().select(kit.getUniqueName(), "playerkitlevels", "uuid", uuid.toString()));
					}catch (NumberFormatException | NullPointerException e) {}
					
					KitManager.setKitLevel(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, kitLevel);
					if (KitManager.getKitLevel(p, kit) > 0) {
						KitManager.addKit(p, kit);
					}
				}
				
			}else {
				for (Kit kit : KitManager.getRegisteredKits()) {
					KitManager.setKitLevel(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, 0);
					if (KitManager.getKitLevel(p, kit) > 0) {
						KitManager.addKit(p, kit);
					}
				}
			}
			
			if (Main.getMysqlConnection().select("*", "playerkitkills", "uuid", uuid.toString()) != null) {
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					
					int kitKills = 0;
					try {
						kitKills = Integer.parseInt(Main.getMysqlConnection().select(kit.getUniqueName(), "playerkitkills", "uuid", uuid.toString()));
					}catch (NumberFormatException | NullPointerException e) {}
					
					KitManager.setKitKills(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, kitKills);
				}
				
			}else {
				for (Kit kit : KitManager.getRegisteredKits()) {
					KitManager.setKitKills(OtherUtils.getPlayer(UUID.fromString(uuid)), kit, 0);
				}
			}
		}
	}
	
	public static HashMap<String, Integer> getSortedHashMap(HashMap<String, Integer> map) {
		HashMap<String, Integer> sorted = new HashMap<>();
		
		sorted = map
		        .entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		        .collect(
		            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                LinkedHashMap::new));
		
		return sorted;
	}
	
	
	public static void saveToMysql(Player p, boolean async) {
		
		String uuid = p.getUniqueId().toString();
		String name = p.getName();
		String kills = String.valueOf(getKills(uuid));
		String deaths = String.valueOf(getDeaths(uuid));
		
		if (async) {
		new BukkitRunnable() {
			
			// playerkitkills
			
			@Override
			public void run() {
				if (Main.getMysqlConnection().select("*", "playerstats", "uuid", uuid.toString()) != null) {
					
					Main.getMysqlConnection().update("playerstats", "lastname", name, "uuid", uuid);
					Main.getMysqlConnection().update("playerstats", "kills", kills, "uuid", uuid);
					Main.getMysqlConnection().update("playerstats", "deaths", deaths, "uuid", uuid);
					
				}else {
					ArrayList<String> infos = new ArrayList<String>();
					infos.add("uuid");
					infos.add("lastname");
					infos.add("kills");
					infos.add("deaths");
					
					ArrayList<String> values = new ArrayList<String>();
					values.add(uuid.toString());
					values.add(name);
					values.add(kills);
					values.add(deaths);
					
					Main.getMysqlConnection().insert("playerstats", infos, values);
				}
				
				
				if (Main.getMysqlConnection().select("*", "playerkitlevels", "uuid", uuid.toString()) != null) {
					
					Main.getMysqlConnection().update("playerkitlevels", "lastname", name, "uuid", uuid);
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						String uniqueName = kit.getUniqueName();
						
						Main.getMysqlConnection().update("playerkitlevels", uniqueName, String.valueOf(KitManager.getKitLevel(p, kit)), "uuid", uuid);
					}
					
					
				}else {
					ArrayList<String> infos = new ArrayList<String>();
					infos.add("uuid");
					infos.add("lastname");
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						String uniqueName = kit.getUniqueName();
						
						infos.add(uniqueName);
					}
					
					ArrayList<String> values = new ArrayList<String>();
					values.add(uuid.toString());
					values.add(name);
				
					for (Kit kit : KitManager.getRegisteredKits()) {
						values.add(String.valueOf(KitManager.getKitLevel(p, kit)));
					}
					
					Main.getMysqlConnection().insert("playerkitlevels", infos, values);
				}
				
				
				if (Main.getMysqlConnection().select("*", "playerkitkills", "uuid", uuid.toString()) != null) {
					
					Main.getMysqlConnection().update("playerkitkills", "lastname", name, "uuid", uuid);
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						String uniqueName = kit.getUniqueName();
						
						Main.getMysqlConnection().update("playerkitkills", uniqueName, String.valueOf(KitManager.getKitKills(p, kit)), "uuid", uuid);
					}
					
					
				}else {
					ArrayList<String> infos = new ArrayList<String>();
					infos.add("uuid");
					infos.add("lastname");
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						String uniqueName = kit.getUniqueName();
						
						infos.add(uniqueName);
					}
					
					ArrayList<String> values = new ArrayList<String>();
					values.add(uuid.toString());
					values.add(name);
				
					for (Kit kit : KitManager.getRegisteredKits()) {
						values.add(String.valueOf(KitManager.getKitKills(p, kit)));
					}
					
					Main.getMysqlConnection().insert("playerkitkills", infos, values);
				}
				
				
			}
		}.runTaskAsynchronously(Main.getInstance());
		
		}else {

			if (Main.getMysqlConnection().select("*", "playerstats", "uuid", uuid.toString()) != null) {
				
				Main.getMysqlConnection().update("playerstats", "lastname", name, "uuid", uuid);
				Main.getMysqlConnection().update("playerstats", "kills", kills, "uuid", uuid);
				Main.getMysqlConnection().update("playerstats", "deaths", deaths, "uuid", uuid);
				
			}else {
				ArrayList<String> infos = new ArrayList<String>();
				infos.add("uuid");
				infos.add("lastname");
				infos.add("kills");
				infos.add("deaths");
				
				ArrayList<String> values = new ArrayList<String>();
				values.add(uuid.toString());
				values.add(name);
				values.add(kills);
				values.add(deaths);
				
				Main.getMysqlConnection().insert("playerstats", infos, values);
			}
			
			
			if (Main.getMysqlConnection().select("*", "playerkitlevels", "uuid", uuid.toString()) != null) {
				
				Main.getMysqlConnection().update("playerkitlevels", "lastname", name, "uuid", uuid);
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					String uniqueName = kit.getUniqueName();
					
					Main.getMysqlConnection().update("playerkitlevels", uniqueName, String.valueOf(KitManager.getKitLevel(p, kit)), "uuid", uuid);
				}
				
				
			}else {
				ArrayList<String> infos = new ArrayList<String>();
				infos.add("uuid");
				infos.add("lastname");
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					String uniqueName = kit.getUniqueName();
					
					infos.add(uniqueName);
				}
				
				ArrayList<String> values = new ArrayList<String>();
				values.add(uuid.toString());
				values.add(name);
			
				for (Kit kit : KitManager.getRegisteredKits()) {
					values.add(String.valueOf(KitManager.getKitLevel(p, kit)));
				}
				
				Main.getMysqlConnection().insert("playerkitlevels", infos, values);
			}
			
			
			if (Main.getMysqlConnection().select("*", "playerkitkills", "uuid", uuid.toString()) != null) {
				
				Main.getMysqlConnection().update("playerkitkills", "lastname", name, "uuid", uuid);
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					String uniqueName = kit.getUniqueName();
					
					Main.getMysqlConnection().update("playerkitkills", uniqueName, String.valueOf(KitManager.getKitKills(p, kit)), "uuid", uuid);
				}
				
				
			}else {
				ArrayList<String> infos = new ArrayList<String>();
				infos.add("uuid");
				infos.add("lastname");
				
				for (Kit kit : KitManager.getRegisteredKits()) {
					String uniqueName = kit.getUniqueName();
					
					infos.add(uniqueName);
				}
				
				ArrayList<String> values = new ArrayList<String>();
				values.add(uuid.toString());
				values.add(name);
			
				for (Kit kit : KitManager.getRegisteredKits()) {
					values.add(String.valueOf(KitManager.getKitKills(p, kit)));
				}
				
				Main.getMysqlConnection().insert("playerkitkills", infos, values);
			}
			
		}
		
	}

	@SuppressWarnings("deprecation")
	public static void reloadStatsWall() {
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				HashMap<String, Integer> sorted = getSortedHashMap(kills);
				
				for (int i = 0; i<Variables.skullLocs.length; i++) {
					
					
					Location loc = Variables.skullLocs[i];
					
					if (loc.getBlock() != null && loc.getBlock().getType() == Material.PLAYER_WALL_HEAD) {
						Skull skull = (Skull) loc.getBlock().getState();
						
						if (sorted.keySet().toArray().length > i && !sorted.keySet().toArray()[i].equals("N/A")) {
							skull.setOwner(getName((String) sorted.keySet().toArray()[i]));
						}else {
							skull.setOwner("MHF_Question");
						}
						skull.update();
						
					}
				}
				
				for (int i = 0; i<Variables.signLocs.length; i++) {
					Location loc = Variables.signLocs[i];
					
					if (loc.getBlock() != null && loc.getBlock().getType() == Material.OAK_WALL_SIGN) {
						Sign sign = (Sign) loc.getBlock().getState();
						
						if (sorted.keySet().toArray().length > i && !sorted.keySet().toArray()[i].equals("N/A")) {
							
							
							sign.setLine(0, getFormatted(Variables.signLine0, (String) sorted.keySet().toArray()[i]));
							sign.setLine(1, getFormatted(Variables.signLine1, (String) sorted.keySet().toArray()[i]));
							sign.setLine(2, getFormatted(Variables.signLine2, (String) sorted.keySet().toArray()[i]));
							sign.setLine(3, getFormatted(Variables.signLine3, (String) sorted.keySet().toArray()[i]));
							
							sign.update();
							
						}else {
							sign.setLine(0, "");
							sign.setLine(1, "§cUnbekannt");
							sign.setLine(2, "");
							sign.setLine(3, "");
							sign.update();
						}
						
					}
				}
				
			}
		}.runTask(Main.getInstance());
		
		
	}
	
	static String getFormatted(String old, String uuid) {
		String newS = old.replaceAll("%kills%", String.valueOf(getKills(uuid)));
		newS = newS.replaceAll("%deaths%",  String.valueOf(getDeaths(uuid)));
		newS = newS.replaceAll("%kd%", String.valueOf(getKD(uuid)));
		newS = newS.replaceAll("%name%", String.valueOf(getName(uuid)));
		return newS;
	}
	
	public static double getKD(String uuid) {
		double kd;
		
		if (getDeaths(uuid) == 0) {
			kd = getKills(uuid);
		}else {
			Double killsD = new Double(getKills(uuid));
			Double deathsD = new Double(getDeaths(uuid));
			kd = OtherUtils.round(killsD/deathsD, 3);
		}
		return kd;
	}
	

	
	
	public static String getName(String uuid) {
		OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		
		if (of == null) {
			if (!hashedNames.containsKey(UUID.fromString(uuid))) {
				return Main.getMysqlConnection().select("lastname", "playerstats", "uuid", uuid);
			}else {
				return hashedNames.get(UUID.fromString(uuid));
			}
		}else {
			return of.getName();
		}
	}
	
	public static void importTheFiveBest() {

		// SELECT uuid FROM wargame ORDER BY wins DESC LIMIT 5
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ResultSet rs = Main.getMysqlConnection().executeQueryWithResultSet("SELECT uuid FROM playerstats ORDER BY kills DESC LIMIT 5");

				try {
					
					while (rs.next()) {	
						String uuid = rs.getString("uuid");
						top5UUIDs.add(uuid);
					}
					
					while (top5UUIDs.size() < 5) {
						top5UUIDs.add("N/A");
					}
					
				} catch (SQLException e) {
					Bukkit.broadcastMessage("§cEIN FEHLER IST AUFGETRETEN!");
					e.printStackTrace();
				}

				String one = top5UUIDs.get(0);
				String two = top5UUIDs.get(1);
				String three = top5UUIDs.get(2);
				String four = top5UUIDs.get(3);
				String five = top5UUIDs.get(4);
				
				if (!one.equals("N/A")) {
					kills.put(one, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", one)));
					deaths.put(one, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", one)));
				}
				
				if (!two.equals("N/A")) {
					kills.put(two, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", two)));
					deaths.put(two, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", two)));
				}

				if (!three.equals("N/A")) {
					kills.put(three, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", three)));
					deaths.put(three, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", three)));
				}
				
				if (!four.equals("N/A")) {
					kills.put(four, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", four)));
					deaths.put(four, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", four)));
				}

				if (!five.equals("N/A")) {
					kills.put(five, Integer.parseInt(Main.getMysqlConnection().select("kills", "playerstats", "uuid", five)));
					deaths.put(five, Integer.parseInt(Main.getMysqlConnection().select("deaths", "playerstats", "uuid", five)));
				}
				
				StatsManager.reloadStatsWall();
				
			}
		}.runTaskAsynchronously(Main.getInstance());

		

	}
}
