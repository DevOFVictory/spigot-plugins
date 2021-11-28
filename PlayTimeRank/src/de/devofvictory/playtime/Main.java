package de.devofvictory.playtime;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.commands.Command_PlayTime;
import ru.tehkode.permissions.bukkit.PermissionsEx;



public class Main extends JavaPlugin implements CommandExecutor{
	
	
	public static File playtime = new File("plugins//PlayTime//playtimes.yml");
	public static YamlConfiguration pt = YamlConfiguration.loadConfiguration(playtime);
	
	
	public static FileConfiguration config;
	public static File cfile;
	
	
	
	public static String Prefix;
	

	
	public void onEnable() {
		 getLogger().info("###############################################");
		 getLogger().info("              [PlayTimeRank] Enable");
		 getLogger().info("                 Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich geladen und aktiviert!");
	 	 getLogger().info("###############################################");
	 	 
	 	 //loadPt();
	 	 
	 	 
	 	 config = getConfig();
	 	 config.options().copyDefaults(true);
	 	 saveConfig();
	 	 
	 	 cfile = new File("plugins//PlayTime//config.yml");
	 	 try {
			cfile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	
	 	Prefix = config.getString("Prefix").replace('&', '§');
	 	 
	 	 
	 	 getCommand("PlayTime").setExecutor(new Command_PlayTime());
	 	 
	 	 
	 	 
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {				
				
				for(Player players : Bukkit.getOnlinePlayers()) {
					

					pt.set("PlayTimes."+players+ ".minutes", pt.getInt("PlayTimes."+players+".minutes")+1);
					config = YamlConfiguration.loadConfiguration(cfile);
					
					if (pt.getInt("PlayTimes."+players+".minutes") >59) {

						pt.set("PlayTimes."+players+".minutes", 0);
						pt.set("PlayTimes."+players+".hours", pt.getInt("PlayTimes."+players+".hours")+1);
						saveConfig();
						reloadConfig();
						pt = YamlConfiguration.loadConfiguration(playtime);
						players.sendMessage(Main.Prefix+"§aGlückwunsch, du hast wieder eine Stunde Spielzeit voll. :)");
						
					}
					if (pt.getInt("PlayTimes."+players+".hours") >23) {
					pt.set("PlayTimes."+players+".hours", 0);
					pt.set("PlayTimes."+players+".days", pt.getInt("PlayTimes."+players+".days")+1);
					try {
						pt.save(playtime);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pt = YamlConfiguration.loadConfiguration(playtime);
					players.sendMessage(Main.Prefix+"§aGlückwunsch, du hast wieder einen Tag Spielzeit voll. :)");
					}
					if (pt.getInt("PlayTimes."+players+".days") >6) {
						pt.set("PlayTimes."+players+".days", 0);
						pt.set("PlayTimes."+players+".weeks", pt.getInt("PlayTimes."+players+".weeks")+1);
						try {
							pt.save(playtime);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pt = YamlConfiguration.loadConfiguration(playtime);
						players.sendMessage(Main.Prefix+"§aGlückwunsch, du hast wieder eine Woche Spielzeit voll. :)");
					}
					
					
				}
				
			}
			
			
		}, 0, 60*20);
	 	 }
		
	
	
	public void onDisable() {
		 getLogger().info("###############################################");
		 getLogger().info("              [PlayTimeRank] Disable");
		 getLogger().info("                 Version 1.0" );
	 	 getLogger().info("Plugin erfolgreich entladen und deaktiviert!");
	 	 getLogger().info("###############################################");
	 	 savePt();
	 	 
	 	 
	 	 
	}
	
	public void saveCfg() {
		try {
			getConfig().save(cfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

	
	

	
	//-------------------------------------------------------------------
	private void savePt() {
		try {
			pt.save(playtime);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[PlayTimeRank] Ein Fehler beim Speichern der PlayTime-File ist aufgetreten");
		}
	
	}
	public void loadPt() {
		
		getConfig().options().copyDefaults(true);
		savePt();
	}
	public void reloadPt() {
		reloadConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("playtimerankreload")) {
			if(sender instanceof Player) {
				if (sender.hasPermission("playtimerank.admin.reload")) {
					reloadConfig();
				Main.Prefix = getConfig().getString("Prefix").replace('&', '§');
				saveConfig();
				sender.sendMessage(Prefix +"§aDie Config für Das PlayTimeRank-Plugin wurde reloaded.");
				}else {sender.sendMessage(Prefix+"§cDir fehlt das Recht §6playtimerank.admin.reload§c!");}
			}else {System.out.println("[PlayTimeRank] Du musst ein Spieler sein!");}
		}
		
	
	
	
		else if(cmd.getName().equalsIgnoreCase("SetPlayTime")) {
			if(sender instanceof Player) {
			if (sender.hasPermission("playtimerank.admin.setplaytime")) {
				
				if (args.length != 5 && args.length != 1) {
					sender.sendMessage(Main.Prefix+"§cBitte verwende /setplaytime <Spieler> <Minuten> <Stunden> <Tage> <Wochen>");
				}
				else if (args.length == 5) {
					Player player = (Player) Bukkit.getServer().getPlayer(args[0]);
					int minutes = Integer.parseInt(args[1]);
					int hours = Integer.parseInt(args[2]);
					int days = Integer.parseInt(args[3]);
					int weeks = Integer.parseInt(args[4]);
							
							
					if (player != null) {
						pt.set("PlayTimes."+player+ ".minutes", minutes);
						pt.set("PlayTimes."+player+ ".hours", hours);
						pt.set("PlayTimes."+player+ ".days", days);
						pt.set("PlayTimes."+player+ ".weeks", weeks);
						savePt();
						pt = YamlConfiguration.loadConfiguration(playtime);
						
						player.sendMessage(Prefix+"§5Deine Spielzeit wurde umgestellt! Hier deine neuen Werte:");
						player.sendMessage("§k§c-----------------------------------------------------");
						player.sendMessage(Prefix+"§eMinuten:  "  +pt.getInt("PlayTimes."+player+".minutes"));
						player.sendMessage(Prefix+"§eStunden: "  +pt.getInt("PlayTimes."+player+".hours"));
						player.sendMessage(Prefix+"§eTage:     "  +pt.getInt("PlayTimes."+player+".days"));
						player.sendMessage(Prefix+"§eWochen:  "  +pt.getInt("PlayTimes."+player+".weeks"));
						player.sendMessage("§k§c-----------------------------------------------------");
						
						sender.sendMessage(Prefix+ "§aDie Spielminuten von §2"+args[0]+" §awurden auf §2"+args[1]+" §aMinuten, §2"+args[2]+" §aStunden, §2"+args[3]+" §aTage und §2"+args[4]+" §aWochen gestellt!");
						
					} else {sender.sendMessage(Prefix+"§cDer Spieler §6"+args[0]+" §c ist nicht online!");}
				}
			
			}else {sender.sendMessage(Prefix+"§cDir fehlt das Recht §6playtimerank.admin.setplaytime§c!");}
		}}
		
		
		
		else if (cmd.getName().equalsIgnoreCase("upgrade")) {
			String senderName = sender.getName();
			if (sender instanceof Player) {
				if (sender.hasPermission("playtimerank.user.upgrade")) {
				if(!PermissionsEx.getUser((Player) sender).inGroup("owner") && !PermissionsEx.getUser((Player) sender).inGroup("dev") && !PermissionsEx.getUser((Player) sender).inGroup("builder") && !PermissionsEx.getUser((Player) sender).inGroup("admin") && !PermissionsEx.getUser((Player) sender).inGroup("moderator")) {
			if (args.length != 1) {
				sender.sendMessage(Prefix+"§cBitte benutze /upgrade <Rang>");
			}
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("Premium")) {
					if (!PermissionsEx.getUser((Player) sender).inGroup("Premium")) {
					if(!PermissionsEx.getUser((Player) sender).inGroup("ultra") && !PermissionsEx.getUser((Player) sender).inGroup("legende") && !PermissionsEx.getUser((Player) sender).inGroup("champion")) {
					if (pt.getInt("PlayTimes."+sender+".days") >= 1 || pt.getInt("PlayTimes."+sender+".weeks") >= 1) {
						getServer().dispatchCommand(getServer().getConsoleSender(),"pex user "+senderName+" group set Premium");
						((Player) sender).kickPlayer("§2Glückwunsch! §a Dein Rang wurde auf Premium upgegraded! §5Viel Spaß! :) §ePlugin by DevOFVictory");
						getLogger().info(">>");
						getLogger().info(sender.getName()+" hat jetzt den Rang Premium");
						getLogger().info(">>");
						Bukkit.broadcastMessage(Prefix+"§k§r§5"+sender.getName()+" hat Dank seiner Onlinezeit den Premium-Rang erhalten!");
					}else {sender.sendMessage(Prefix+"§cDu hast nicht genügend Onlinezeit!");
					sender.sendMessage(Prefix+"§eDu brachst mind. §61 Tag / 24 Stunden §eSpielzeit!");
					}
					}else {sender.sendMessage(Prefix+"§cDu hast bereits einen höhreren Rang als Premium!");}
					}else {sender.sendMessage(Prefix+"§cDu besitzt bereits den §6Premium-Rang§c!");}
				}
				else if (args[0].equalsIgnoreCase("Ultra")) {
					if (!PermissionsEx.getUser((Player) sender).inGroup("Ultra")) {
					if(!PermissionsEx.getUser((Player) sender).inGroup("champion") && !PermissionsEx.getUser((Player) sender).inGroup("legende")) {
					
						if (pt.getInt("PlayTimes."+sender+".days") >= 3 || pt.getInt("PlayTimes."+sender+".weeks") >= 1) {
							getServer().dispatchCommand(getServer().getConsoleSender(),"pex user "+senderName+" group set Ultra");
							((Player) sender).kickPlayer("§2Glückwunsch! §a Dein Rang wurde auf Ultra upgegraded! §5Viel Spaß! :) §ePlugin by DevOFVictory");
							getLogger().info(">>");
							getLogger().info(sender.getName()+" hat jetzt den Rang Ultra");
							getLogger().info(">>");
							Bukkit.broadcastMessage(Prefix+"§k§r§5"+sender.getName()+" hat Dank seiner Onlinezeit den Ultra-Rang erhalten!");
					}else {sender.sendMessage(Prefix+"§cDu hast nicht genügend Onlinezeit!");
						   sender.sendMessage(Prefix+"§eDu brachst mind. §63 Tage §eSpielzeit");
					}
					}else {sender.sendMessage(Prefix+"§cDu hast bereits einen höhreren Rang als Ultra!");}
					}else {sender.sendMessage(Prefix+"§cDu besitzt bereits den §6Ultra-Rang§c!");}
				}
				else if (args[0].equalsIgnoreCase("Champion")) {
					if (!PermissionsEx.getUser((Player) sender).inGroup("Champion")) {
					if(!PermissionsEx.getUser((Player) sender).inGroup("legende")) {
					
						if (pt.getInt("PlayTimes."+sender+".days") >= 5 || pt.getInt("PlayTimes."+sender+".weeks") >= 1) {
							getServer().dispatchCommand(getServer().getConsoleSender(),"pex user "+senderName+" group set Champion");
							((Player) sender).kickPlayer("§2Glückwunsch! §a Dein Rang wurde auf Champion upgegraded! §5Viel Spaß! :) §ePlugin by DevOFVictory");
							getLogger().info(">>");
							getLogger().info(sender.getName()+" hat jetzt den Rang Champion");
							getLogger().info(">>");
							Bukkit.broadcastMessage(Prefix+"§k§r§5"+sender.getName()+" hat Dank seiner Onlinezeit den Champion-Rang erhalten!");
					}else {sender.sendMessage(Prefix+"§cDu hast nicht genügend Onlinezeit!");
						   sender.sendMessage(Prefix+"§eDu brachst mind. §65 Tage §eSpielzeit!");
						   }
					
					}else {sender.sendMessage(Prefix+"§cDu hast bereits einen höhreren Rang als Champion!");}
					}else {sender.sendMessage(Prefix+"§cDu besitzt bereits den §6Champion-Rang§c!");}
					}
				
				else if (args[0].equalsIgnoreCase("Legende")) {
					if (!PermissionsEx.getUser((Player) sender).inGroup("Legende")) {
					if (pt.getInt("PlayTimes."+sender+".weeks") >= 1 || pt.getInt("PlayTimes."+sender+".days") >= 7) {
						
							getServer().dispatchCommand(getServer().getConsoleSender(),"pex user "+senderName+" group set Legende");
							((Player) sender).kickPlayer("§2Glückwunsch! §a Dein Rang wurde auf Legende upgegraded! §5Viel Spaß! :) §ePlugin by DevOFVictory");
							getLogger().info(">>");
							getLogger().info(sender.getName()+" hat jetzt den Rang Legende");
							getLogger().info(">>");
							Bukkit.broadcastMessage(Prefix+"§k§r§5"+sender.getName()+" hat Dank seiner Onlinezeit den Legende-Rang erhalten!");
					
					}else {sender.sendMessage(Prefix+"§cDu hast nicht genügend Onlinezeit!");
						   sender.sendMessage(Prefix+"§eDu brachst mind. §67 Tage / 1 Woche §eSpielzeit!");
					}
					}else {sender.sendMessage(Prefix+"§cDu besitzt bereits den §6Legende-Rang§c!");}
						
				}else {sender.sendMessage(Prefix+"§cDiesen Rang gibt es nicht!");
					   sender.sendMessage(Prefix+"§eDie Ränge heißen: §6Premium§e, §6Ultra§e, §6Champion§e und §6Legende§e!");
				}//-----------------------------------------------------------------------------------------
			}}else {sender.sendMessage(Prefix+"§cDa du ein Teammitglied bist, kannst du diesen Befehl nicht nutzen!");}
			
		}else {sender.sendMessage(Prefix+"§cDir fehlt das Recht §6playtimerank.user.upgrade§c!");}
		} else {getLogger().info("Du musst ein Spieler sein");}
			}
		return false;
	
	}
}
