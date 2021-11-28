package de.devofvictory.prefix2000.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.prefix2000.listeners.OnJoin;
import de.devofvictory.prefix2000.main.Main;
import de.devofvictory.prefix2000.utils.ScoreboardClass;


public class CommandCustomprefix implements CommandExecutor{


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("customprefix")) {
			
			if (sender instanceof Player) {

				if (sender.hasPermission("prefix.admin.setcustomprefix")) {
					
					
					if (args.length == 0) {
						sender.sendMessage("[§5Prefix§d2000§r] §3Plugin Coded by §bDevOFVictory §c~16.11.18-23.11.18");
						return true;
					}
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("help")) {
							sender.sendMessage("[§5Prefix§d2000§r] §cHilfe: /customprefix <Spieler> <Prefix>");
						}else {sender.sendMessage("[§5Prefix§d2000§r] §cVerwende §6/customprefix help §cfür Hilfe!");}
					}
					else if (args.length == 2) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						
						if (target.isOnline()) {
						
						if (args[1].equalsIgnoreCase("none")) {
							
							
							ScoreboardClass.cp.set("Customprefixes."+target.getName(), null);
							ScoreboardClass.saveCfg();
							
							sender.sendMessage("[§5Prefix§d2000§r] §4Prefix von "+target.getName()+" §4entfermt!");
							return true;
						}
						
						
						
						String prefix = args[1].replace('&', '§');
						prefix = prefix.replaceAll("§§", "&");
						prefix = prefix+"§f|" ;
					
						if (args[1].length() <= 11) { 
						OnJoin.setCustomPrefix(target, prefix+Main.TabPrefixCustom);
						ScoreboardClass.cp.set("Customprefixes."+target.getName(), prefix);
						ScoreboardClass.saveCfg();
						sender.sendMessage("[§5Prefix§d2000§r] §aDer Prefix von §2"+target.getName()+" §awurde zu §r"+prefix+"§ageändert.");
						}else {sender.sendMessage("[§5Prefix§d2000§r] §cDer Prefix darf höchstens 11 Zeichen lang sein!");
						return false;}
						}else {sender.sendMessage("[§5Prefix§d2000§r] §cDieser Spieler ist nicht Online!");}
						
					}else {sender.sendMessage("[§5Prefix§d2000§r] §cBenutzung: /customprefix <Spieler> <Prefix>");}
					
					}else {sender.sendMessage("[§5Prefix§d2000§r] §cKeine Rechte!");}
			
		}else {System.out.println("[Prefix2000] Du musst ein Spieler sein!");}
			}
		
		return false;
	}

}
