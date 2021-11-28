package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.devofvictory.ezentials.main.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;


public class Command_Ping implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length == 0) {
				String perm = "ezentials.ping.own";
				if (sender instanceof Player) {
					Player p = (Player) sender;
				if (p.hasPermission(perm)) {
						p.sendMessage(Main.Prefix+"§aDein Ping beträgt §2"+getPing(p)+" §ams!");
						return true;
					}else {
						p.sendMessage(Main.noPerms(perm));
						return true;
					}
				}else {
					sender.sendMessage(Main.Prefix+"§cFür diesen Command musst du ein Spieler sein!");
				}
				
			}else if (args.length == 1){
				if (sender.hasPermission("ezentials.ping.other")) {

				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]); 
						sender.sendMessage(Main.Prefix+"§aDer Ping von §6"+t.getName()+"§a beträgt §2"+getPing(t)+" §ams!");
						return true;
					
				}else {
					sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §cist nicht online!");
					return true;
				}
				}else {
					sender.sendMessage(Main.noPerms("ezentials.ping.other"));
					return true;
				}
				
			}else {
				if (sender.hasPermission("ezentials.ping.own") || sender.hasPermission("ezentials.ping.other")) {
					sender.sendMessage("§cBenutze: §6/ping §coder §6/ping <Spieler>");
					return true;
				}else {
					sender.sendMessage(Main.noPerms("ezentials.ping.own/other"));
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getPing(Player p) {
		CraftPlayer pingc = (CraftPlayer)p;
		EntityPlayer pinge = pingc.getHandle();
		return pinge.ping;
	}

}
