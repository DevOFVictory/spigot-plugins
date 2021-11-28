package de.devofvictory.biomicploz.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.biomicploz.main.Main;

public class Command_Restart implements CommandExecutor{
	
	static int seconds = 10;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warnedrestart")) {
			
			if (sender.hasPermission("biomicplots.cmd.warnedrestart")) {
				
				
				if (args.length != 1) {
					sender.sendMessage(Main.Prefix+"§cBenutze §6/warnedrestart <Sekunden>§c!");
					return true;
				}
				
				seconds = Integer.parseInt(args[0]);
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					if (seconds >= 0) {
						Bukkit.getServer().broadcastMessage(Main.Prefix+"§6§lDer Server startet in §4§l"+seconds+" Sekunden §6§lneu!");
						seconds--;
					
					}else {
						Bukkit.shutdown();
					}
				}
			}, 0, 20);
		}
		}else {
			sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
		}
		return true;
	}

}
