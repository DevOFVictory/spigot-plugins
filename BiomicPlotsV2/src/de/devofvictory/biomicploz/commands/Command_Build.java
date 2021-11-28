package de.devofvictory.biomicploz.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.Utils;

public class Command_Build implements CommandExecutor{
	
	public static ArrayList<Player> powermode = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("powermode")) {
			if (sender instanceof Player && sender.hasPermission("biomicplots.cmd.powermode")) {
				Player p = (Player)sender;
				if (!powermode.contains(p)) {
					
					powermode.add(p);
					p.sendMessage(Main.Prefix+"§aDu bist nun im Powermode!");
				}else {
					powermode.remove(p);
					p.sendMessage(Main.Prefix+"§cDu bist nun nicht mehr im Powermode!");
				}
			}else {
				Utils.sendNoSpamMsg((Player)sender, Main.Prefix+ "§cDafür hast du keine Rechte!");
			}
		}
		
		return true;
	}
	
	

}
