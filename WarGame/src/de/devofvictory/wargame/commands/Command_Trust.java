package de.devofvictory.wargame.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;

public class Command_Trust implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("trust")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				if (args.length == 0) {
				
					if (!Code.trusted.contains(p.getName())) {
						Code.askForCode(p);
					}else {
						p.sendMessage(Main.Prefix+"§cDu bist nun nicht mehr vertraut!");
						Code.trusted.remove(p.getName());
					}
				
				}else {
					
					if (Code.trusted.contains(p.getName())) {
					Code.trusted.add(args[0]);
					p.sendMessage(Main.Prefix+"§a"+args[0]+" wurde vertraut!");
					Bukkit.getPlayer(args[0]).sendMessage(Main.Prefix+"§a"+p.getName()+" hat dich/dir vertraut!");
					
					}else {
						Code.askForCode(p);
					}
				}
				
			}else {
				sender.sendMessage(Main.Prefix+"§cDu musst ein Spieler sein!");
			}
		}
		return true;
	}
	
	

}
