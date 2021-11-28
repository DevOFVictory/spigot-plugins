package de.devofvictory.buyperms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.buyperms.main.Main;

public class Command_buyperms implements CommandExecutor{

	//Commands:
	// /buyperms create <NAME> <Permission> <Price>
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("buyperms")) {		
			if(sender instanceof Player) {
				
				if(args.length == 0) {
					sender.sendMessage(Main.Prefix+"§aProgrammiert von DevOFVictory");
					
				}
				else if(args.length == 3) {
					if (sender.hasPermission("buyperms.admin.create")) {
						
						if(args[0].equalsIgnoreCase("create")) {
							
							
						}
						
						
					}else {sender.sendMessage(Main.Prefix+"§cDir fehlt das Recht §6buyperms.admin.create§c!");}
				}
					
					
				}
			}
		
		
		return false;
	}
	
	

}
