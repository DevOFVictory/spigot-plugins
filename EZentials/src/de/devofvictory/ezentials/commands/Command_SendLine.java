package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.ezentials.main.Main;

public class Command_SendLine implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sendline")) {
			String perm = "ezentials.cmd.sendline";
			if (sender.hasPermission(perm)) {
				if (args.length >= 2) {
					
					String message = "";
					for (int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					
					if (args[0].equalsIgnoreCase("*")) {
						Bukkit.broadcastMessage(message.replace('&', '§'));
						sender.sendMessage(Main.Prefix+"§aZeile wurde an alle Spieler gesendet!");
						return true;
					}
					
					if(Bukkit.getPlayer(args[0]) == null) {
						sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht online!");
						return true;
					}
					
					
					
					
					
					Player target = Bukkit.getPlayer(args[0]);
					target.sendMessage(message.replace('&', '§'));
					sender.sendMessage(Main.Prefix+"§aZeile wurde an §6"+target.getName()+" §agesendet!");
					return true;
					
					
					
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze §6/sendline <*/Spieler> <Nachricht>§c!");
				}
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}
	
	

}
