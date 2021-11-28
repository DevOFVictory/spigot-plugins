package de.devofvictory.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.playtime.Main;

public class Command_PlayTimeRankReset implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if (cmd.getName().equalsIgnoreCase("PlayTimeRankReset")) {
			if (sender.hasPermission("playtimerank.admin.reset")) {
				
				sender.sendMessage(Main.Prefix+"§cDiese Funktion folgt in den nächsten Updates... lösche einfach plugin/PlayTime/config.yml");
			}else { sender.sendMessage(Main.Prefix+ "§cDir fehlt das Recht §6playtimerank.admin.reset§c!");}
		}
		
		return false;
	}

}
