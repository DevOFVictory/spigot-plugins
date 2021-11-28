package de.devofvictory.skykitpvp.eco.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.skykitpvp.eco.main.EcoMain;

public class EcoManager {
	
	private static HashMap<UUID, Integer> playerMoney = new HashMap<UUID, Integer>();
	
	public static int getCoins(Player player) {
		return playerMoney.containsKey(player.getUniqueId()) ? playerMoney.get(player.getUniqueId()) : 0;
	}
	
	public static int getCoinsFromMySQL(UUID uuid) {
		playerMoney.get(Bukkit.getPlayer("DevOFVictory").getUniqueId());
		return 0;
	}

	
	public static void setCoins(Player player, int coins) {
		playerMoney.put(player.getUniqueId(), coins);
	}
	
	public static void setCoinsInMySQL(UUID uuid) {
		
	}
	
	public static void addCoins(Player player, int coins) {
		setCoins(player, getCoins(player)+coins);
	}
	
	public static void takeCoins(Player player, int coins) {
		setCoins(player, getCoins(player)-coins);
	}
	
	public static boolean hasEnough(Player player, int coins) {
		return getCoins(player) >= coins;
	}
	
	public static void saveToMySql(Player p, boolean asnyc) {
		
		final String uuid = p.getUniqueId().toString();
		final String lastName = p.getName();
		final String coins = String.valueOf(EcoManager.getCoins(p));
		
		if (asnyc) {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if (EcoVariables.connection.select("*", "playerCoins", "uuid", p.getUniqueId().toString()) != null) {
					
					EcoVariables.connection.update("playerCoins", "lastname", lastName, "uuid", uuid);
					EcoVariables.connection.update("playerCoins", "coins", coins, "uuid", uuid);
					
				}else {
					ArrayList<String> infos = new ArrayList<String>();
					infos.add("uuid");
					infos.add("lastname");
					infos.add("coins");
					
					ArrayList<String> values = new ArrayList<String>();
					values.add(uuid);
					values.add(lastName);
					values.add(coins);
					
					EcoVariables.connection.insert("playerCoins", infos, values);
					
				}
				
			}
		}.runTaskAsynchronously(EcoMain.getInstance());
		
		}else {
			if (EcoVariables.connection.select("*", "playerCoins", "uuid", p.getUniqueId().toString()) != null) {
				
				EcoVariables.connection.update("playerCoins", "lastname", lastName, "uuid", uuid);
				EcoVariables.connection.update("playerCoins", "coins", coins, "uuid", uuid);
				
			}else {
				ArrayList<String> infos = new ArrayList<String>();
				infos.add("uuid");
				infos.add("lastname");
				infos.add("coins");
				
				ArrayList<String> values = new ArrayList<String>();
				values.add(uuid);
				values.add(lastName);
				values.add(coins);
				
				EcoVariables.connection.insert("playerCoins", infos, values);
			}
		}
	}
	
	public static void loadFromMySql(Player p, boolean async) {
		
		final String uuid = p.getUniqueId().toString();
		
		if (async) {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (EcoVariables.connection.select("*", "playerCoins", "uuid", uuid) != null) {
					EcoManager.setCoins(p, Integer.parseInt(EcoVariables.connection.select("coins", "playerCoins", "uuid", uuid)));
				}else {
					EcoManager.setCoins(p, EcoVariables.startCoins);
				}
			}
		}.runTaskAsynchronously(EcoMain.getInstance());
		
		}else {
			if (EcoVariables.connection.select("*", "playerCoins", "uuid", uuid) != null) {
				EcoManager.setCoins(p, Integer.parseInt(EcoVariables.connection.select("coins", "playerCoins", "uuid", uuid)));
			}else {
				EcoManager.setCoins(p, EcoVariables.startCoins);
			}
		}
	}
	
	
	

}
