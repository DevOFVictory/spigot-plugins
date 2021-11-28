package de.devofvictory.ezentials.commands;



import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.ezentials.main.Main;

public class Command_AdvancedSudo implements  CommandExecutor{
	
	// /asudo <Spieler> <"OP"/Perm> <ChatNachricht/Befehl>

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("asudo")) {
			String perm = "ezentials.cmd.asudo";
			if (sender.hasPermission(perm)) {
				if (args.length >= 3) {
					
					if (Bukkit.getPlayer(args[0]) == null) {
						sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht online!");
						return true;
					}
					Player target = Bukkit.getPlayer(args[0]);
					if (sender instanceof Player) {
//						Player p = (Player)sender;
						
					if (args[0].equalsIgnoreCase("*")) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							target = all;
						}
					}
					
					
//					if (target.hasPermission("ezentials.asudo.bypass")) {
//						if (!p.hasMetadata("ezentials.asudo.bypasstheperm")) {
//						sender.sendMessage(Main.Prefix+"§cDu darfst diesen Befehl nicht an diesen Spieler ausuführen!");
//						return true;
//					}
//					}
					}
					String msg = "";
					for (int i = 2; i < args.length; i++) {
						msg = msg + args[i] + " ";
					}
					
					if (args[2].startsWith("/")) {
						msg = msg.replaceFirst("/", "");
						
						if (args[1].equalsIgnoreCase("OP")) {
							if (target.isOp()) {
							Bukkit.dispatchCommand(target, msg);
							}else {
							target.setOp(true);
							Bukkit.dispatchCommand(target, msg);
							target.setOp(false);
							}
						}else if (args[1].equalsIgnoreCase("none")) {
							Bukkit.dispatchCommand(target, msg);
						}else {
							if (target.hasPermission(args[1])) {
								Bukkit.dispatchCommand(target, msg);
							}else {
								
//							PermissionsEx.getUser(target).addPermission(args[1]);
							Bukkit.dispatchCommand(target, msg);
//							PermissionsEx.getUser(target).removePermission(args[1]);
							}
						}
						sender.sendMessage(Main.Prefix+"§aDer Befehl wurde erfolgreich von §6"+target.getName()+" §aausgeführt!");
						
					}else {
						if (args[1].equalsIgnoreCase("OP")) {
							if (target.isOp()) {
							target.chat(msg);
							}else {
							target.setOp(true);
							target.chat(msg);
							target.setOp(false);
							}
						}else if (args[1].equalsIgnoreCase("none")) {
							
							target.chat(msg);
						
						}else {
							if (target.hasPermission(args[1])) {
								Bukkit.dispatchCommand(target, msg);
							}else {
								
//								PermissionsEx.getUser(target).addPermission(args[1]);
								target.chat(msg);
//								PermissionsEx.getUser(target).removePermission(args[1]);
							}
						}
					sender.sendMessage(Main.Prefix+"§aDie Nachricht wurde erfolgreich von §6"+target.getName()+" §ageschrieben!");
						
					}
					
				}else {
					sender.sendMessage(Main.Prefix+"§cBenutze §6/asudo <Spieler> <'OP'/'NONE'/Permission> <Nachricht/Befehl>");
					
				}
				
			}else {
				sender.sendMessage(Main.noPerms(perm));
			}
		}
		return false;
	}

}
