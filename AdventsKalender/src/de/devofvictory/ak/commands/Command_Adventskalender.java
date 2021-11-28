package de.devofvictory.ak.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.ak.main.Main;

public class Command_Adventskalender implements CommandExecutor{
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("adventskalender")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("adventskalender.use")) {
					
					Main.inv = p.getServer().createInventory(null, 27, "§6Advents§eKalender");
					
					p.openInventory(Main.inv);
					
				}else {sender.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");}
			}else {sender.sendMessage(Main.Prefix+"§cDu musst ein Spieler sein!");}
		}
		
		
		
		return false;
	}

}
