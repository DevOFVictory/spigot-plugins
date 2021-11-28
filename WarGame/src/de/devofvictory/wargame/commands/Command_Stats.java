package de.devofvictory.wargame.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;

public class Command_Stats implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					
					Player p = (Player)sender;
					
					p.sendMessage(Main.Prefix+"[]==========Stats==========[]");
					p.sendMessage(Main.Prefix+"§eCoins: "+Main.getMinigame().getCoins(p));
					p.sendMessage(Main.Prefix+"§eKills: "+Main.getMinigame().getStats(p).getKills());
					p.sendMessage(Main.Prefix+"§eDeaths: "+Main.getMinigame().getStats(p).getDeaths());
					p.sendMessage(Main.Prefix+"§eKD: ca. "+Main.getMinigame().getStats(p).getKD(2));
					p.sendMessage(Main.Prefix+"§eWins: "+Main.getMinigame().getStats(p).getWins());
					p.sendMessage(Main.Prefix+"[]=========================[]");
					
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze: §6/stats <Spieler>§c!");
				}
			}else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("reload")) {
					
					if (sender.hasPermission("wargame.stats.reload")) {
						
						sender.sendMessage(Main.Prefix+"§aJoa...");
					}else {
						sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("import")) {
					
					if (sender.hasPermission("wargame.stats.import")) {
						sender.sendMessage(Main.Prefix+"§aStatistiken wurden importiert!");
					}else {
						sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
					}
					
					return true;
				}else if (args[0].equalsIgnoreCase("export")) {
					
					if (sender.hasPermission("wargame.stats.export")) {
						sender.sendMessage(Main.Prefix+"§aStatistiken wurden exportiert!");
					}else {
						sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
					}
					
					return true;
				}
				
				String uuid = "";
				
				if (Bukkit.getPlayer(args[0]) != null) {
					uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
				}else {
					
					sender.sendMessage(Main.Prefix+"§cDieser Spieler ist nicht nicht online!");
					
					
				}
				
				try {
					sender.sendMessage(Main.Prefix+"[]==========Stats==========[]");
					sender.sendMessage(Main.Prefix+"§eCoins: "+Main.getMinigame().getOfflineCoins(UUID.fromString(uuid)));
					sender.sendMessage(Main.Prefix+"§eKills: "+Main.getMinigame().getOfflineStats(UUID.fromString(uuid)).getKills());
					sender.sendMessage(Main.Prefix+"§eDeaths: "+Main.getMinigame().getOfflineStats(UUID.fromString(uuid)).getDeaths());
					sender.sendMessage(Main.Prefix+"§eKD: ca. "+Main.getMinigame().getOfflineStats(UUID.fromString(uuid)).getKD(2));
					sender.sendMessage(Main.Prefix+"§eWins: "+Main.getMinigame().getOfflineStats(UUID.fromString(uuid)).getWins());
					sender.sendMessage(Main.Prefix+"[]=========================[]");
				
				}catch (NullPointerException ex) {
					sender.sendMessage(Main.Prefix+"§cDieser Spieler hat noch keine Statistiken!");
				}
			}else {
				// /stats <Name> <add/remove/set> <value> <WarCoins/Kills/Deaths/Wins> 
				
//				if (sender.hasPermission("wargame.stats.change")) {
//					
//					if (args.length == 4) {
//						
//						String uuid = "";
//						
//						if (Bukkit.getPlayer(args[0]) != null) {
//							uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
//						}else {
//							Bukkit.getOfflinePlayer(args[0]).toString();
//						}
//						
//						switch (args[1]) {
//						case "add":
//							switch (args[3]) {
//							case "WarCoins":
//								StatsManager.addWarCoins(uuid, Integer.valueOf(args[2]));
//								
//							case "Kills":
//								StatsManager.addKills(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Deaths":
//								StatsManager.addDeaths(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Wins":
//								StatsManager.addWins(uuid, Integer.valueOf(args[2]));
//								break;
//							default:
//								sender.sendMessage(Main.Prefix+"§cDie Einheiten lauten: §6WarCoins, Kills, Deaths, §6Wins§c!");
//								return true;
//							}
//							break;
//
//						case "remove":
//							switch (args[3]) {
//							case "WarCoins":
//								StatsManager.takeWarCoins(uuid, Integer.valueOf(args[2]));
//								Bukkit.broadcastMessage("UUID: "+uuid);
//								break;
//							case "Kills":
//								StatsManager.takeKills(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Deaths":
//								StatsManager.takeDeaths(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Wins":
//								StatsManager.takeWins(uuid, Integer.valueOf(args[2]));
//								break;
//							default:
//								sender.sendMessage(Main.Prefix+"§cDie Einheiten lauten: §6WarCoins, Kills, Deaths, §6Wins§c!");
//								return true;
//							}
//							break;
//							
//						case "set":
//							switch (args[3]) {
//							case "WarCoins":
//								StatsManager.warCoins.put(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Kills":
//								StatsManager.kills.put(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Deaths":
//								StatsManager.deaths.put(uuid, Integer.valueOf(args[2]));
//								break;
//							case "Wins":
//								StatsManager.wins.put(uuid, Integer.valueOf(args[2]));
//								break;
//							default:
//								sender.sendMessage(Main.Prefix+"§cDie Einheiten lauten: §6WarCoins, Kills, Deaths, §6Wins§c!");
//								return true;
//							}
//							break;
//						}
//						sender.sendMessage(Main.Prefix+"§aStatistikveränderung wurde vorgenommen!");
//						
//						
//					}else {
//						sender.sendMessage(Main.Prefix+"§cBenutze: §6/stats <Name> <add/remove/set> <Wert> <WarCoins/Kills/Deaths/KD/Wins>§c!");
//					}
//					
//				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze: §6/stats [<Spieler>]§c!");
				}
				
			}
		
		return true;
		
	}

	
	

}
