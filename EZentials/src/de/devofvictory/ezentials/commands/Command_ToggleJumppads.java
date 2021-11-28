package de.devofvictory.ezentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.ezentials.main.Main;

public class Command_ToggleJumppads implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("togglejumppads")) {
			String perm = "ezentials.cmd.togglejumppads";
			if (sender.hasPermission(perm)) {
				if (Main.JumpPadsEnable == true) {
					Main.JumpPadsEnable = false;
					sender.sendMessage(Main.Prefix+"§cDie Jumppads wurden §4deaktiviert!");
				}else {
					Main.JumpPadsEnable = true;
					sender.sendMessage(Main.Prefix+"§aDie Jumppads wurden §2aktiviert!");
				}
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}

}
