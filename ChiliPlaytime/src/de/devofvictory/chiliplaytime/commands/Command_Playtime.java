package de.devofvictory.chiliplaytime.commands;

import de.devofvictory.chiliplaytime.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_Playtime extends Command{

	public Command_Playtime(String name) {
		super("playtime");
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer)sender;
			
			p.sendMessage(Main.Prefix+"§cBald verfügbar!");
			
			
//			p.sendMessage("§3§lWar§b§lGame §8» §7"+PlaytimeManager.getMinutes(p, "WarGame"));
//			p.sendMessage("§e§lSurvive §8» §7"+PlaytimeManager.getMinutes(p, "Survive"));
//			p.sendMessage("§e§lKnock§6§lDown §8» §7"+PlaytimeManager.getMinutes(p, "KnockDown"));
//			p.sendMessage("§c§lK§6§lO§c§lT§6§lK §8» §7"+PlaytimeManager.getMinutes(p, "KingOfTheCastle"));
//			p.sendMessage("§2§lCityBuild §8» §7"+PlaytimeManager.getMinutes(p, "CityBuild"));
//			p.sendMessage("");
//			p.sendMessage("§e§lG§6§ll§c§lo§4§lb§a§la§2§ll §8» "+PlaytimeManager.getMinutes(p));
			
		}else {
			sender.sendMessage(Main.Prefix + "§cDu musst ein Spieler sein!");
		}
		
	}
	

}
