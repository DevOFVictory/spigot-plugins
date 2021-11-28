package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.ezentials.main.Main;

public class Command_IP implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("ip")) {
			
				
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player p = (Player)sender;
				String permOwn = "ezentials.ip.own";
				if (p.hasPermission(permOwn)) {
					
					p.sendMessage(Main.Prefix+"§aDeine IP-Adresse lautet: §2"+p.getAddress().toString().replaceFirst("/", ""));
				
					
				}else {
					sender.sendMessage(Main.noPerms(permOwn));
				}
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze: §6/ip <Spieler>");
				}
			}else if (args.length == 1){
				String permOther = "ezentials.ip.other";
				if (sender.hasPermission(permOther)) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null ) {
					
					sender.sendMessage(Main.Prefix+"§aDie IP-Adresse von §6"+target.getName()+" §alautet: §2"+target.getAddress().toString().replaceFirst("/", ""));
				}else {
					sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+"§c ist nicht online!");
					return true;
				}
				}else {
					sender.sendMessage(Main.noPerms(permOther));
				}
			} else {
				if (sender.hasPermission("ezentials.ip.own") || sender.hasPermission("ezentials.ip.other")) {
				sender.sendMessage(Main.Prefix+"§cBenutze: §6/ip [<Spieler>]");
				}else {
					sender.sendMessage(Main.noPerms("ezentials.ip"));
					
				}
			}
		
		}
		
		return true;
	}

}
