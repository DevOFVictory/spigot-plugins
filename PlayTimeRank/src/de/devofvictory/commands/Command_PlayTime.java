package de.devofvictory.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.devofvictory.playtime.Main;


public class Command_PlayTime implements CommandExecutor{
	
	
	
 @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("PlayTime")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if (args.length == 0) {
					if (p.hasPermission("playtimerank.playtime.own")) {
				
				
				p.sendMessage(Main.Prefix+"§5Hier ist deine aktuelle Spiel- bzw. Online-Zeit");
				p.sendMessage("§k§c-----------------------------------------------------");
				p.sendMessage(Main.Prefix+"§eMinuten:  "  +Main.pt.getInt("PlayTimes."+p+".minutes"));
				p.sendMessage(Main.Prefix+"§eStunden: "  +Main.pt.getInt("PlayTimes."+p+".hours"));
				p.sendMessage(Main.Prefix+"§eTage:     "  +Main.pt.getInt("PlayTimes."+p+".days"));
				p.sendMessage(Main.Prefix+"§eWochen:  "  +Main.pt.getInt("PlayTimes."+p+".weeks"));
				p.sendMessage("§k§c-----------------------------------------------------");
				}else {p.sendMessage(Main.Prefix+"§cDir fehlt das Recht §6playtimerank.playtime.own§c!");}
					}
				else if (args.length == 1) {
					if (sender.hasPermission("playtimerank.playtime.other")) {
						
						p.sendMessage(Main.Prefix+"§5Hier ist "+args[0]+"'s Onlinezeit:");
						p.sendMessage("§k§c-----------------------------------------------------");
						p.sendMessage(Main.Prefix+"§eMinuten:  "  +Main.pt.getInt("PlayTimes.CraftPlayer{name="+args[0]+"}.minutes"));
						p.sendMessage(Main.Prefix+"§eStunden: "  +Main.pt.getInt("PlayTimes.CraftPlayer{name="+args[0]+"}.hours"));
						p.sendMessage(Main.Prefix+"§eTage:     "  +Main.pt.getInt("PlayTimes.CraftPlayer{name="+args[0]+"}.days"));
						p.sendMessage(Main.Prefix+"§eWochen:  "  +Main.pt.getInt("PlayTimes.CraftPlayer{name="+args[0]+"}.weeks"));
						p.sendMessage("§k§c-----------------------------------------------------");
						
					}else {p.sendMessage(Main.Prefix+"§cDir fehlt das Recht §6playtimerank.playtime.other§c!");}
					
				}else {if (p.hasPermission("playtimerank.playtime.other")) {
					p.sendMessage(Main.Prefix+"§cBitte verwende /playtime oder /playtime <Spieler>");
				}else {p.sendMessage(Main.Prefix+"§cBitte verwende /playtime");}
				}
				
				 
			}else {System.out.println("[PlayTimeRank] Du musst ein Spieler sein!");}
		}
		return false;
	}
}
