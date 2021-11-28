package de.devofvictory.cm.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import de.devofvictory.cm.main.Main;

public class Command_CmdSpy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cmdspy")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("chatmanager.cmdspy.use")) {

					if (args.length < 1) {

						if (p.hasMetadata("cmdspy")) {
							p.removeMetadata("cmdspy", Main.getInstance());
							p.sendMessage(Main.CmdSpyPrefix + "§cDer CommandSpy wurde deaktiviert!");
						} else {
							p.setMetadata("cmdspy", new FixedMetadataValue(Main.getInstance(), true));
							p.sendMessage(Main.CmdSpyPrefix
									+ "§aDer CommandSpy wurde aktiviert! §6Du siehst nun alle Nachrichten von §nSpielern!");
						}

					} else {
						p.sendMessage(Main.CmdSpyPrefix + "§dZurzeit benutzen diese Spieler den CommandSpy:");
						for (Player all : Bukkit.getOnlinePlayers()) {
							if (all.hasMetadata("cmdspy")) {
								p.sendMessage(Main.CmdSpyPrefix + "- §c" + all.getName());
							}
						}

					}

				} else {
					p.sendMessage(Main.Prefix + "§cDu hast hier für keine Rechte :(");
				}
			}
		}
		return false;
	}

}
