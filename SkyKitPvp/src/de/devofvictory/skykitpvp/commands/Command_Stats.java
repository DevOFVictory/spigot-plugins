package de.devofvictory.skykitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.StatsManager;

public class Command_Stats implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (args.length == 0) {
				if (p.hasPermission("skykitpvp.stats.own")) {
					

					
					p.sendMessage(Main.Prefix+"§7=-=-=-=-=-=-=-=-=-=");
					p.sendMessage(Main.Prefix+"§dKills: §5"+StatsManager.getKills(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dTode: §5"+StatsManager.getDeaths(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dK/D: §5"+StatsManager.getKD(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§7=-=-=-=-=-=-=-=-=-=");
				}else {
					p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
				}
			}else {
				if (p.hasPermission("skykitpvp.stats.other")) {
					
					Player target = Bukkit.getPlayer(args[0]);
					
					if (target != null) {
					
					
					p.sendMessage(Main.Prefix+"§7=-=-=["+target.getName()+"]-=-=-=");
					p.sendMessage(Main.Prefix+"§dKills: §5"+StatsManager.getKills(target.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dTode: §5"+StatsManager.getDeaths(target.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dK/D: §5"+StatsManager.getKD(target.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§7=-=-=-=-=-=-=-=-=-=");
					
					}else {
						p.sendMessage(Main.Prefix+"§cDieser Spieler ist nicht online");
					}
					
				}else {
					if (p.hasPermission("skykitpvp.stats.own")) {
					
					p.sendMessage(Main.Prefix+"§7=-=-=-=-=-=-=-=-=-=");
					p.sendMessage(Main.Prefix+"§dKills: §5"+StatsManager.getKills(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dTode: §5"+StatsManager.getDeaths(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§dK/D: §5"+StatsManager.getKD(p.getUniqueId().toString()));
					p.sendMessage(Main.Prefix+"§7=-=-=-=-=-=-=-=-=-=");
					
					}else {
						p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
					}
				}
			}
			
			
			
		}
		return true;
	}


}
