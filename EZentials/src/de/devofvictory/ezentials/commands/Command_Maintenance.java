package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import de.devofvictory.ezentials.main.Main;

public class Command_Maintenance implements CommandExecutor{
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("maintenance")) {
			String perm = "ezentials.cmd.maintenance";
			if (sender.hasPermission(perm)) {
				
				if (!Main.maintenance) {
				
				if (args.length == 0) {
					Main.maintenance = true;
					Main.maintenanceMessage = "none";
					kickAll();
					sender.sendMessage(Main.Prefix+"§aWartungen wurden §2aktiviert!");
				}else {
					Main.maintenance = true;
					String msg = "";
					for (int i = 0; i<args.length; i++) {
						msg = msg + args[i] + " ";
					}
					msg = msg.replace('&', '§');
					Main.maintenanceMessage = msg;
					kickAll();
					sender.sendMessage(Main.Prefix+"§aWartungen wurden §2aktiviert! §8>> §f"+msg);
				}
				
				}else {
					Main.maintenance = false;
					sender.sendMessage(Main.Prefix+"§cWartungen wurden §4deaktiviert!");
				}
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
			
		}
		return false;
	}
	
	private void kickAll() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (!all.hasPermission("ezentials.maintenance.bypass")) {
				if (Main.maintenanceMessage.equalsIgnoreCase("none")) {
				all.kickPlayer("§c§lDu wurdest wegen Wartungen vom Server gekickt.");
				}else {
					all.kickPlayer("§c§lDu wurdest wegen Wartungem vom Server gekickt. §8>> §f"+Main.maintenanceMessage);
				}
			}
		}
	}

}



