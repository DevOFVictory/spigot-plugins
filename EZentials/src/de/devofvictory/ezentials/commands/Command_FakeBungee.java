package de.devofvictory.ezentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command_FakeBungee implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("bungee")) {
			sender.sendMessage("§9This server is running BungeeCord version \ngit:BungeeCord-Bootstrap:1.8-SNAPSHOT:80b3135:1150 by md_5");
		}else if (cmd.getName().equalsIgnoreCase("server")) {
			sender.sendMessage("§cYou don't have enough permissions to do this!");
		}
		return false;
	}
	

}
