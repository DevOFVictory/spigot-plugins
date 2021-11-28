package de.devofvictory.survivalproject.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.survivalproject.main.Main;

public class Command_EC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player)sender;
			
			if (p.hasPermission("survivalproject.ec")) {
				
				if (args.length == 1) {
				
				Player target = Bukkit.getPlayer(args[0]);
				
				if (target != null) {
					p.openInventory(target.getEnderChest());
				}else {
					p.sendMessage(Main.Prefix+"§c"+args[0]+" ist nicht online!");
				}	
				
				}else {
					p.sendMessage(Main.Prefix+"§cFalsche Benutzung!");
				}
				
			}
		}
		
		return true;
	}
	
	

}
