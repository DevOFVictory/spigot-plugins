package de.devofvictory.skykitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.utils.KitManager;

public class Command_SetKit implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("skykitpvp.cmd.setkit")) {

			if (args.length == 1) {

				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					Kit inputKit = KitManager.getKitByUniqueName(args[0].toLowerCase());
					
					if (inputKit != null) {
						
						KitManager.setKitForced(p, inputKit);
						p.sendMessage(Main.Prefix+"§aKit wurde erfolgreich gesetzt!");
						
					}else {
						p.sendMessage(Main.Prefix+"§cDieses Kit gibt es nicht! Die Kits heißen:");
						
						for (Kit kit : KitManager.getRegisteredKits()) {
							p.sendMessage(Main.Prefix+"§6- §e"+kit.getUniqueName());
						}
					}
				}

			} else if (args.length == 2) {
				Kit inputKit = KitManager.getKitByUniqueName(args[0].toLowerCase());
				
				if (inputKit != null) {
					
					Player t = Bukkit.getPlayer(args[1]);
					
					if (t != null) {
						KitManager.setKitForced(t, inputKit);
						sender.sendMessage(Main.Prefix+"§aKit wurde erfolgreich gesetzt!");
					}else {
						sender.sendMessage(Main.Prefix+"§cDieser Spieler ist nicht online!");
					}
					
					
					
				}else {
					sender.sendMessage(Main.Prefix+"§cDieses Kit gibt es nicht! Die Kits heißen:");
					
					for (Kit kit : KitManager.getRegisteredKits()) {
						sender.sendMessage(Main.Prefix+"§6- §e"+kit.getUniqueName());
					}
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cBenutze §6/setkit <Kit> [<Spieler>]");
			}

		} else {
			sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
		}
		return true;
	}

}
