package de.devofvictory.skykitpvp.eco.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.eco.utils.EcoVariables;

public class Command_Coins implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				if (p.hasPermission("skykitpvp.eco.coins.own")) {
					p.sendMessage(EcoVariables.prefix+"§aDu hast §2"+EcoManager.getCoins(p)+ " §aCoins.");
				}else {
					p.sendMessage(EcoVariables.prefix+"§cDafür hast du keine Rechte!");
				}
				
			}else {
				sender.sendMessage(EcoVariables.prefix+"§cDafür musst du ein Spieler sein!");
			}
		}else {
			if (sender.hasPermission("skykitpvp.eco.coins.other")) {
				
				if (Bukkit.getPlayer(args[0]) != null) {
					
					Player target = Bukkit.getPlayer(args[0]);
					sender.sendMessage(EcoVariables.prefix+"§2"+target.getName()+" §ahat §2"+EcoManager.getCoins(target)+ " §aCoins!");
					
				}else {
					sender.sendMessage(EcoVariables.prefix+"§cDieser Spieler ist nicht online!");
				}
				
			}else {
				if (sender.hasPermission("skykitpvp.eco.coins.own")) {
					sender.sendMessage(EcoVariables.prefix+"§aDu hast §2"+EcoManager.getCoins((Player)sender)+ " §aCoins.");
				}else {
					sender.sendMessage(EcoVariables.prefix+"§cDafür hast du keine Rechte!");
				}
			}
		}
		
		return true;
	} 
	
	

}
