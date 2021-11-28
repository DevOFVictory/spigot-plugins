package de.devofvictory.survivalproject.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;

import de.devofvictory.survivalproject.main.Main;

public class Command_Spucken implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spucken")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				p.launchProjectile(LlamaSpit.class);
				
				p.sendMessage(Main.Prefix+"§aDu hast gespuckt!");
				
			}
		}
		return true;
	}
	
	

}
