package de.devofvictory.skykitpvp.eco.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.eco.utils.EcoVariables;

public class Command_Eco implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.hasPermission("skykitpvp.eco.admin")) {
			if (args.length == 3) {
				
				if (Bukkit.getPlayer(args[1]) != null) {
					
					Player target = Bukkit.getPlayer(args[1]);
					
					try {
						int value = Integer.parseInt(args[2]);
						
						switch (args[0]) {
						case "set":
							EcoManager.setCoins(target, value);
							sender.sendMessage(EcoVariables.prefix+"§aDie Coins von §2"+target.getName()+" §awurden auf §2"+value+" §agesetzt!");
							target.sendMessage(EcoVariables.prefix+"§aDeine Coins wurden auf §2"+value+" §agesetzt!");
							break;
						case "give":
							EcoManager.addCoins(target, value);
							sender.sendMessage(EcoVariables.prefix+"§aZu den Coins von §2"+target.getName()+" §awurden §2"+value+" §aaddiert!");
							target.sendMessage(EcoVariables.prefix+"§aZu deinen Coins wurden §2"+value+" §aaddiert!");
							break;
						case "take":
							EcoManager.takeCoins(target, value);
							sender.sendMessage(EcoVariables.prefix+"§aVon den Coins von §2"+target.getName()+" §awurden §2"+value+" §asubtrahiert!");
							target.sendMessage(EcoVariables.prefix+"§aVon deinen Coins wurden §2"+value+" §asubtrahiert!");
							break;
						default:
							sender.sendMessage(EcoVariables.prefix+"§cBenutze: §6/eco <set/give/take> <Spieler> <Wert>§c!");
							break;
						}
						
					}catch (NumberFormatException ex) {
						sender.sendMessage(EcoVariables.prefix+"§cBitte benutze eine gültige Zahl!");
					}
					
				}else {
					sender.sendMessage(EcoVariables.prefix+"§6"+args[0]+" §cist nicht online!");
				}
				
			}else {
				sender.sendMessage(EcoVariables.prefix+"§cBenutze: §6/eco <set/give/take> <Spieler> <Wert>§c!");
			}
		}else {
			sender.sendMessage(EcoVariables.prefix+"§cDafür hast du keine Rechte!");
		}
		
		
		return true;
	}
	
}
