package de.devofvictory.prefix2000.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.prefix2000.main.Main;

public class CommandSetLastLine implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setlastsbline")) {
			if (sender.hasPermission("prefix2000.admin.setlastsbline")) {
				
				if (args.length < 1) {
					sender.sendMessage(Main.Prefix+"§cBenutze §6/setlastsbline <Text>§c!");
					return true;
				}
				
				
				String message = "";
				for (int i = 0; i < args.length; i++) {
					message = message + args[i] + " ";
				}
				message = message.replace('&', '§');
				
				if (message.length() >= 33) {
					sender.sendMessage(Main.Prefix+"§cEingabe zu lang! Höchstens 32 Zeichen!");
					return true;
				}
				
				Main.lastline = message;
				try {
					Main.cfg.save(Main.file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sender.sendMessage(Main.Prefix+"§aDie letzte Zeile vom Scoreboard wurde erfolgreich gesetzt!");
				
			}else {
				sender.sendMessage(Main.Prefix+"§cKeine Rechte :(");
			}
		}
		return false;
	}

}
