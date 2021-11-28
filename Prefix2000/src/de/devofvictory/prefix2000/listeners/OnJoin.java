package de.devofvictory.prefix2000.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import com.earth2me.essentials.api.UserDoesNotExistException;

import de.devofvictory.prefix2000.main.Main;
import de.devofvictory.prefix2000.utils.ScoreboardClass;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class OnJoin implements Listener, CommandExecutor{
	
	
	
	public static boolean hasCustomPrefix(Player p) {
		if (ScoreboardClass.cp.isSet("Customprefixes."+p.getName())) {
			return true;
		}else {return false;
		}
		
	}
	

	static String PrefixDefault   = "[§8LOCKED§r] §e";
	static String PrefixSpieler   = "[§2Spieler§r] §e";
	static String PrefixPremium   = "[§6Premium§r] §e";
	static String PrefixUltra     = "[§bUltra§r] §e";
	static String PrefixChampion  = "[§aChamp§r] §e";
	static String PrefixLegende   = "[§5Legende§r] §e";
	//Team 
	static String PrefixBuilder   = "[§2§lBuild§r] §2";
	static String PrefixDeveloper = "[§3§lDev§r] §b";
	static String PrefixModerator = "[§e§lMod§r] §6";
	static String PrefixAdmin     = "[§c§lAdmin§r] §c";


	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IllegalStateException, IllegalArgumentException, UserDoesNotExistException {
		Player p = e.getPlayer();
		
		ScoreboardClass.createScoreboad(p);
		for (Player all : Bukkit.getOnlinePlayers()) {
		try {all.getScoreboard().registerNewTeam(p.getName());
		}catch (Exception error){
			
		}
		}
		
		//Prefix
			if(p.getScoreboard().getTeam("00000Owner") == null) {
				registerSb(p);
			}
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (!hasCustomPrefix(p)) {
		
			
				setPrefix(all);
			
		}else {
			
				all.getScoreboard().getTeam(p.getName()).setPrefix(ScoreboardClass.cp.getString("Customprefixes."+p.getName()));
				registerSb(p);
				try {all.getScoreboard().getTeam(p.getName()).addPlayer(p);
				
				}catch (Exception error) {}
			}			
		}
			
		
		
		}
			
		
	
	
	public static void registerSb(Player p) {
		
		
		
		try {
		System.out.println("[Prefix2000] Prefixe werden geladen...");
		p.getScoreboard().registerNewTeam("00000Owner");
		p.getScoreboard().registerNewTeam("00001Admin");
		p.getScoreboard().registerNewTeam("00002Developer");
		p.getScoreboard().registerNewTeam("00003Builder");
		p.getScoreboard().registerNewTeam("00004Moderator");
		
		p.getScoreboard().registerNewTeam("00005Legende");
		p.getScoreboard().registerNewTeam("00006Champion");
		p.getScoreboard().registerNewTeam("00007Ultra");
		p.getScoreboard().registerNewTeam("00008Premium");
		p.getScoreboard().registerNewTeam("00009Spieler");
		p.getScoreboard().registerNewTeam("00010Default");
		
		p.getScoreboard().getTeam("00000Owner").setPrefix(Main.TabPrefixOwner);
		p.getScoreboard().getTeam("00001Admin").setPrefix(Main.TabPrefixAdmin);
		p.getScoreboard().getTeam("00002Developer").setPrefix(Main.TabPrefixDeveloper);
		p.getScoreboard().getTeam("00003Builder").setPrefix(Main.TabPrefixBuilder);
		p.getScoreboard().getTeam("00004Moderator").setPrefix(Main.TabPrefixModerator);
		
		p.getScoreboard().getTeam("00005Legende").setPrefix(Main.TabPrefixLegende);
		p.getScoreboard().getTeam("00006Champion").setPrefix(Main.TabPrefixChampion);
		p.getScoreboard().getTeam("00007Ultra").setPrefix(Main.TabPrefixUltra);
		p.getScoreboard().getTeam("00008Premium").setPrefix(Main.TabPrefixPremium);
		p.getScoreboard().getTeam("00009Spieler").setPrefix(Main.TabPrefixSpieler);
		p.getScoreboard().getTeam("00010Default").setPrefix(Main.TabPrefixDefault);
		System.out.println("[Prefix2000] Die Prefixe wurden erfolgreich aus der Config übernommen!");
	}catch (Exception error) {
		System.out.println("[Prefix2000] Die Prefixe wurden erfolgreich aus der Config übernommen!");
	}
	}
	public static void unRegisterSb(Player p) {
		
		p.getScoreboard().getTeam("00000Owner").unregister();
		p.getScoreboard().getTeam("00001Admin").unregister();
		p.getScoreboard().getTeam("00002Developer").unregister();
		p.getScoreboard().getTeam("00003Builder").unregister();
		p.getScoreboard().getTeam("00004Moderator").unregister();
		
		p.getScoreboard().getTeam("00005Legende").unregister();
		p.getScoreboard().getTeam("00006Champion").unregister();
		p.getScoreboard().getTeam("00007Ultra").unregister();
		p.getScoreboard().getTeam("00008Premium").unregister();
		p.getScoreboard().getTeam("00009Spieler").unregister();
		p.getScoreboard().getTeam("000010Default").unregister();
		 
	}
	
	

	@SuppressWarnings("deprecation")
	public static void setPrefix(Player p) {
		String team = "";
		for (Player all : Bukkit.getOnlinePlayers()) {

		if (!hasCustomPrefix(p)) {
		if(PermissionsEx.getUser(all).inGroup("default")) {
			team = "00010Default";
		}
		else if(PermissionsEx.getUser(all).inGroup("Spieler")) {
			team = "00009Spieler";
					
	}
		else if(PermissionsEx.getUser(all).inGroup("Premium")) {
			team = "00008Premium";
	}
		else if(PermissionsEx.getUser(all).inGroup("Ultra")) {
			team = "00007Ultra";
	}
		else if(PermissionsEx.getUser(all).inGroup("Champion")) {
			team = "00006Champion";
	}
		else if(PermissionsEx.getUser(all).inGroup("Legende")) {
			team = "00005Legende";
	}
		//Team
		else if(PermissionsEx.getUser(all).inGroup("Builder")) {
			team = "00003Builder";
			
	}	else if(PermissionsEx.getUser(all).inGroup("Dev")) {
		team = "00002Developer";
			
	}
		else if(PermissionsEx.getUser(all).inGroup("Moderator")) {
			team = "00004Moderator";
			
		
	}
		else if(PermissionsEx.getUser(all).inGroup("Admin")) {
			team = "00001Admin";
			
	}
		else if(PermissionsEx.getUser(all).inGroup("Owner")) {
			team = "00000Owner";
			
	}	else {
			team = "00009Spieler";
	}
		
		p.getScoreboard().getTeam(team).addPlayer(all);
		all.setDisplayName(all.getScoreboard().getTeam(team).getPrefix() + all.getName());
		}
	}}
	//addEntry(player's name)

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rlprefix")) {
		if(sender instanceof Player) {
			if(sender.hasPermission("Prefix2000.admin.reload")) {
				
				unRegisterSb((Player) sender);
				registerSb((Player) sender);
				sender.sendMessage("[§5Prefix§d2000§r] §aDas Plugin wurde neugeladen!");
			}else {sender.sendMessage("[§5Prefix§d2000§r] §cDir fehlt das Recht §6Prefix2000.admin.reload§c!");}
		}else {System.out.println("[Prefix2000] Du musst ein Spieler sein!");
		}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static void setCustomPrefix(Player p, String customprefix) {
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			try {all.getScoreboard().registerNewTeam(p.getName());
			}catch (Exception e) {
				
			}
			all.getScoreboard().getTeam(p.getName()).setPrefix(customprefix);
			all.getScoreboard().getTeam(p.getName()).addPlayer(p);
			
		}
		 
		 
		 
		 }
	
		 
	}
	
	


