package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.ezentials.main.Main;

public class Command_DelayedReload implements CommandExecutor{
	
	static int delay = 60;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delayedreload")) {
			String perm = "ezentials.cmd.delayedreload";
			if (sender.hasPermission(perm)) {
				
				if (args.length == 2) {
					
					try {
					delay = Integer.parseInt(args[0]);
					}catch (NumberFormatException error) {
						sender.sendMessage(Main.Prefix+"§cBitte benutze eine Zahl als Delay!");
						return true;
					}
					Bukkit.broadcastMessage("§c§lAchtung, Achtung! Der Server wird in §4"+delay+" §cSekunden reloaded!");
					Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance(), new Runnable() {
						
						
						@Override
						public void run() {
							delay = delay-1;
							Bukkit.broadcastMessage("§4Reload in §l"+delay+"§4!");
							
							if (delay == 0) {
								Bukkit.getServer().reload();
							}
						}
					}, 1*20, 1*20);
					Main.instance().getServer().reload();
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze §6/delayedreload <Delay(SEC)> <YES/NO>");
				}
				
			}else sender.sendMessage(Main.noPerms(perm));
		}
		return false;
	}

}
