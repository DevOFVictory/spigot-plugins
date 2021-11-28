package de.devofvictory.wargame.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.StartGame;
import de.devofvictory.wargame.utils.StartWartePhase;

public class Command_Start implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("start")) {
		if (sender.hasPermission("wargame.startgame")) {
			if (!Main.isGameRunning) {
			new StartGame(Bukkit.getServer().getOnlinePlayers().size());
			Bukkit.getScheduler().cancelTask(StartWartePhase.scheduler);
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.setLevel(0);
				all.setExp(0);
			}
			}else {
				sender.sendMessage(Main.Prefix+"§cDas Spiel läuft bereits!");
			}
			
		}else {
			sender.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
		}
		
		}
		return false;
	}
	
	

}
