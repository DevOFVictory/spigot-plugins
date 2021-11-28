package de.chilipro.chililobby.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.chilipro.chililobby.listeners.Listener_OnJoin;
import de.chilipro.chililobby.main.Main;

public class Command_Build implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("build")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission("chililobby.cmd.build")) {
					if (Main.allowBuild.contains(p.getName())) {
						Main.allowBuild.remove(p.getName());
						p.sendMessage(Main.Prefix+"§cBaumodus wurde deaktiviert!");
						p.setGameMode(GameMode.SURVIVAL);
						
						Main.builditems.put(p.getName(), p.getInventory().getContents());
						
						Listener_OnJoin.giveJoinItems(p);
						
						
						
					}else {
						Main.allowBuild.add(p.getName());
						p.sendMessage(Main.Prefix+"§aBaumodus wurde aktiviert!");
						p.setGameMode(GameMode.CREATIVE);
						
						if(Main.builditems.containsKey(p.getName())) {
							p.getInventory().setContents(Main.builditems.get(p.getName()));
						}else {
							p.getInventory().clear();
							
						}
					}
					
					
				}else {
					Main.noPerms(p);
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cHier für musst du ein Spieler sein!");
			}
		}
		return true;
	}
	

}
