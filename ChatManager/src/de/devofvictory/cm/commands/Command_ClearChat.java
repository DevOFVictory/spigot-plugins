package de.devofvictory.cm.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.cm.main.Main;

public class Command_ClearChat implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clearchat")) {
			if (sender instanceof Player) {
			if (args.length == 0) {
				
			if( sender.hasPermission("chatmanager.clearchat.all"))	{
				sender.sendMessage(Main.Prefix+"§aDu hast den Chat geleert!");
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (!all.hasPermission("chatmanager.chatclear.bypass")) {
						
						for(int i = 0; i < 105; ++i) {
							all.sendMessage(" ");
						}
						
						all.sendMessage("§aDer Chat wurde von §2"+sender.getName()+" §ageleert!");
					}else {all.sendMessage("§aFür alle Spieler wurde der Chat von §2"+sender.getName()+" §ageleert!");}
				}
				
			}else {sender.sendMessage(Main.Prefix+"§cDu darfst nicht von allen Spielern den Chat leeren!");}
		}else if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target.getName().equalsIgnoreCase(sender.getName())) {
				
				if (sender.hasPermission("chatmanager.clearchat.own")) {
					for(int i = 0; i < 105; ++i) {
						sender.sendMessage(" ");
					}
					sender.sendMessage("§aDu hast deinen Chat geleert!");
				}else {sender.sendMessage(Main.Prefix+"§cDu darfst deinen eigenen Chat nicht leeren!");}
			}else {
			if (sender.hasPermission("chatmanager.clearchat.other")) {

			for(int i = 0; i < 105; ++i) {
				target.sendMessage(" ");
			}
			target.sendMessage("§aDein Chat wurde von §2" +sender.getName()+" §ageleert!");
			sender.sendMessage(Main.Prefix+"§aDu hast den Chat von §2"+target.getName()+" §ageleert!");
			
			}else {sender.sendMessage(Main.Prefix+"§cDu darfst nicht von anderen Spielern den Chat leeren");}
			}
		}else {sender.sendMessage(Main.Prefix+"§cBenutzung: /clearchat [<Spieler>]");}
			}else {System.out.println("[ChatManager] Du musst ein Spieler sein!");}

		}
		return false;
	}
	
	

}
