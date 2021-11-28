package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import de.devofvictory.ezentials.main.Main;

public class Command_Build implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("build")) {
			String perm = "ezentials.cmd.build";
			if (sender.hasPermission(perm)) {
				if (args.length == 0) {
					if (sender instanceof Player) {
						if (((Player) sender).hasMetadata("nobuild")) {
							((Player) sender).removeMetadata("nobuild", Main.instance());
							sender.sendMessage(Main.Prefix+"§aDu kannst nun wieder bauen!");
						}else {
							((Player) sender).setMetadata("nobuild", new FixedMetadataValue(Main.instance(), true));
							sender.sendMessage(Main.Prefix+"§cDu kannst nun nicht mehr bauen!");
						}
					}else {sender.sendMessage(Main.Prefix+"§cNutze /build <Player>");
					}
				}else if (args.length == 1) {
					if (Bukkit.getPlayer(args[0]) == null) {
						sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht Online!");
						return true;
					}
					Player target = Bukkit.getPlayer(args[0]);
					if (((Player) target).hasMetadata("nobuild")) {
						((Player) target).removeMetadata("nobuild", Main.instance());
						target.sendMessage(Main.Prefix+"§aDu kannst nun wieder bauen!");
						sender.sendMessage(Main.Prefix+"§a"+target.getName()+" kann nun wieder bauen!");
					}else {
						((Player) target).setMetadata("nobuild", new FixedMetadataValue(Main.instance(), true));
						target.sendMessage(Main.Prefix+"§cDu kannst nun nicht mehr bauen!");
						sender.sendMessage(Main.Prefix+"§c"+target.getName()+" kann nun nicht mehr bauen!");
					}
				}else {
				
				}
				
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}
	
	

}
