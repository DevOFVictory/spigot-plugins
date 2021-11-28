package de.devofvictory.survivalproject.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.survivalproject.main.Main;

public class Command_Invsee implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.hasPermission("system.invsee")) {
			if (sender instanceof Player) {
			
				if (args.length == 1) {
					
					if (Bukkit.getPlayer(args[0]) != null) {
						
						Player p = (Player)sender;
						p.openInventory(Bukkit.getPlayer(args[0]).getInventory());
						
					}else {
						sender.sendMessage(Main.Prefix+"§c"+args[0]+" ist nicht online!");
					}
					
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze: §6/invsee <Player>");
				}
				
			}
		}else {
			sender.sendMessage(Main.Prefix+"§cNope.");
		}
		
		return true;
	}
	

}
