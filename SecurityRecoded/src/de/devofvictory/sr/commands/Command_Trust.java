package de.devofvictory.sr.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.sr.main.Main;

public class Command_Trust implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("trust")) {
			String perm = Main.getInstance().getDescription().getFullName()+".cmd.trust";
			
			/*if (!(sender instanceof Player)) {
				sender.sendMessage(Main.Prefix+"§cFür diesen Befehl musst du ein Spieler sein!");
				return true;
			}*/
			if (args.length != 2) {
				sender.sendMessage(Main.Prefix+"§cBitte benutze §6/trust <Spieler> <Passwort>");
				return true;
			}
			if (!sender.hasPermission(perm)) {
				sender.sendMessage(Main.noperms(perm));
				return true;
			}
			if (Bukkit.getPlayer(args[0]) == null) {
				sender.sendMessage(Main.Prefix+"§cDer Spieler §6"+args[0]+" §c ist nicht online!");
				return true;
			}
			if (!args[1].equals("SeCretP_W%CHILI")) {
				Player p = (Player)sender;
				p.kickPlayer("§c§lFALSCHES PASSWORT 1/3");
				Bukkit.broadcastMessage(Main.Prefix+"§c§lWarnung! >> Der Spieler "+p.getName()+" hat ein falsches Passwort eingegeben!");
				return true;
			}
			Player p = (Player)sender;
			Player target = Bukkit.getPlayer(args[0]);
			if (!Main.trustedplayers.contains(target.getName())) {
				Main.trustedplayers.add(target.getName());
				p.sendMessage(Main.Prefix+"§2Dem Spieler §a"+target.getName()+" §2wurde vertraut!");
			}else {
				Main.trustedplayers.remove(target.getName());
				p.sendMessage(Main.Prefix+"§4Dem Spieler §c"+target.getName()+" §4wird nun nichtmehr vertaut!");
			}
		}
		return false;
	}

}
