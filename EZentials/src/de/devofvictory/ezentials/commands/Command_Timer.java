package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.ezentials.main.Main;

public class Command_Timer implements CommandExecutor{
	
	public int start;
	public String message = "";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("timer")) {
			String perm = "ezentials.cmd.timer";
			if (sender.hasPermission(perm)) {
				if (args.length > 2) {
					
					
					
					for (int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					
					try {
					start = Integer.parseInt(args[0]);
					}catch (NumberFormatException e) {
						sender.sendMessage(Main.Prefix+"§cBitte verwende eine gültige Zahl!");
						return true;
					}
					Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance(), new Runnable() {
						
						@Override
						public void run() {
							if (start > 0) {
								
								start = start-1;
								
								Bukkit.broadcastMessage(message+"§ain §5"+start);
							
							}else {
								
							}
						}
					}, 0, 1*20);
						
						

				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze: §6/timer <Sekunden> <Name>");
				}
				
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}

}
