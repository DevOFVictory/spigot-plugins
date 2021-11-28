package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.ezentials.main.Main;

public class Command_Rank implements CommandExecutor{

	// /rank <Dev> <Rang> 
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rank")) {
			String perm = "ezentials.cmd.rank";
			if (sender.hasPermission(perm)) {
				if (args.length==2) {
					
					if (Bukkit.getPlayer(args[0]) != null) {
						
						if (args[1].equalsIgnoreCase("locked")) {
							if (sender.hasPermission("ezentials.setrank.locked")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set default");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §8LOCKED");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §8LOCKED §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §8Locked §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Spieler")) {
							if (sender.hasPermission("ezentials.setrank.Spieler")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Spieler");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §2Spieler");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §2Spieler §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §2Spieler §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Premium")) {
							if (sender.hasPermission("ezentials.setrank.Premium")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Premium");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §6Premium");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §6Premium §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §6Premium §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Ultra")) {
							if (sender.hasPermission("ezentials.setrank.Ultra")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Ultra");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §bUltra");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §bUltra §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §bUltra §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Champion")) {
							if (sender.hasPermission("ezentials.setrank.Champion")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Champion");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §eChampion");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §eChampion §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §eChampion §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Legende")) {
							if (sender.hasPermission("ezentials.setrank.Legende")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Legende");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §5Legende");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §5Legende §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §5Legende §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Moderator")) {
							if (sender.hasPermission("ezentials.setrank.Moderator")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Moderator");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §e§lModerator");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §e§lModerator §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §e§lModerator §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Dev")) {
							if (sender.hasPermission("ezentials.setrank.Dev")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Dev");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §3§lDev");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §3§lDev §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §3§lDev §chast du keine Rechte!");
							}
						}
						else if (args[1].equalsIgnoreCase("Builder")) {
							if (sender.hasPermission("ezentials.setrank.Builder")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Builder");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §a§lBuilder");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §a§lBuilder §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §a§lBuilder §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Admin")) {
							if (sender.hasPermission("ezentials.setrank.Admin")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Admin");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §c§lAdmin");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §c§lAdmin §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §c§lAdmin §chast du keine Rechte!");
							}
						}else if (args[1].equalsIgnoreCase("Owner")) {
							if (sender.hasPermission("ezentials.setrank.Owner")) {
								Player target = Bukkit.getPlayer(args[0]);
								Main.instance().getServer().dispatchCommand(Main.instance().getServer().getConsoleSender(),"pex user "+target.getName()+" group set Owner");
								target.kickPlayer("§a§lDein Rang wurde verändert! \n§6Neuer Rang: §4§lOwner");
								sender.sendMessage(Main.Prefix+"§aDer Rang von §6"+target.getName()+" §awurde zu §4§lOwner §ageändert!");
							
							}else {
								sender.sendMessage(Main.Prefix+"§cFür den Rang §4§lOwner §chast du keine Rechte!");
							}
						}else {
							sender.sendMessage(Main.Prefix+"§cMomentan gibt es den Rang §6"+args[1]+" §cbei uns nicht...");
						}
						
						
						
					}else {
						sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht Online!");
					}
					
				}else {sender.sendMessage(Main.Prefix+"§cBenutze §6/rank <Spieler> <Rang>§c!");}
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}

}
