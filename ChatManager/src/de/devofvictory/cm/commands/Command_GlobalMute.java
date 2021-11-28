package de.devofvictory.cm.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.cm.main.Main;

public class Command_GlobalMute implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] argrs) {
		
		if (cmd.getName().equalsIgnoreCase("globalmute")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("chatmanager.globalmute.use")) {
					
					if(Main.globalMute) {
						Main.globalMute = false;
						p.sendMessage(Main.Prefix+"§aDu hast den globalen Chat erfolgreich aktiviert!");
						Bukkit.broadcastMessage("§2§lDer globale Chat wurde von §6§l"+p.getName()+" §2§lwieder frei geschaltet!");
					}else {
						Main.globalMute = true;
						
						p.sendMessage(Main.Prefix+"§cDu hast den globalen Chat erfolgreich deaktiviert!");
						Bukkit.broadcastMessage("§4§lDer globale Chat wurde von §6§l"+p.getName()+" §4§lstummgeschaltet!");
					}
					
				}else {sender.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");}
				
				
			}else {sender.sendMessage(Main.Prefix+"§cDu musst ein Spieler sein!");}
		}
		
		return false;
	}

}
