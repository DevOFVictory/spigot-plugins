package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import de.devofvictory.ezentials.main.Main;

public class Command_Freeze implements CommandExecutor{
	
	public static Location loc;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Freeze")) {
			String perm = "ezentials.cmd.freeze";
			if (sender.hasPermission(perm)) {
				if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (target.hasMetadata("freezed")) {
							target.removeMetadata("freezed", Main.instance());
							sender.sendMessage(Main.Prefix+"§c"+target.getName()+" wurde aufgetaut");
							target.sendMessage(Main.Prefix+"§aDu wurdest wieder aufgetaut!");
						}else {
							target.setMetadata("freezed", new FixedMetadataValue(Main.instance(), true));
							sender.sendMessage(Main.Prefix+"§a"+target.getName()+" wurde eingefroren");
							target.sendMessage(Main.Prefix+"§cDu wurdest eingefroren");
							loc = target.getLocation();
						}
						
					
				}else {sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht online!");}
			}else {sender.sendMessage(Main.Prefix+"§cNutze §6/freeze <Spieler>§c!");}
			}else {sender.sendMessage(Main.noPerms(perm));}
		}
		return false;
	}

}
