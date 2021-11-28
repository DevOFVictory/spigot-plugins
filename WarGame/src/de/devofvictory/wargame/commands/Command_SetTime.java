package de.devofvictory.wargame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;
import de.devofvictory.wargame.utils.StartGame;

public class Command_SetTime implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("wargame.settime")) {
			
			if (Code.trusted.contains(sender.getName())) {
			
			if (args.length == 2) {
				StartGame.setTime(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
				sender.sendMessage(Main.Prefix+"§aSpielzeit wurde verstellt!");
			}else {
				sender.sendMessage(Main.Prefix+"§cVerwende §6/settime <Sekunden> <Minuten>§c!");
			}
			
			}else {
				Code.askForCode((Player) sender);
			}
		}else {
			sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
		}
		return false;
	}

}
