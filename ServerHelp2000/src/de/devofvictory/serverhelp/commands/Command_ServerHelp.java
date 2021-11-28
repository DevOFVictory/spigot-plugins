package de.devofvictory.serverhelp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.serverhelp.main.Main;

public class Command_ServerHelp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("serverhelp")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("serverhelp2000.reload")) {
						Main.getInstance().reloadConfig();
						Main.getInstance().amount = Main.getInstance().getConfig().getInt("AutoBroadCastAmount");
						sender.sendMessage(Main.Prefix+"§aThe config file was reloaded!");
					}
				}
			}
		}
		return true;
	}
	
	

}
