package de.devofvictory.ezentials.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devofvictory.ezentials.main.Main;

public class EZentilas implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ezentials")) {
			if (args.length == 0) {
				sender.sendMessage(Main.Prefix+"§9EZentials §aPlugin v."+Main.instance().getDescription().getVersion());
				sender.sendMessage(Main.Prefix+">>");
				sender.sendMessage(Main.Prefix+"§cVersion: §6v."+Main.instance().getDescription().getVersion());
				sender.sendMessage(Main.Prefix+"§cAutor: §6DevOFVictory");
				sender.sendMessage(Main.Prefix+"§cBegonnen am §622.12.2018");
				sender.sendMessage(Main.Prefix+"§2Ein Plugin im Auftrag von §6[none]");
				sender.sendMessage(Main.Prefix+"-----------------------------------------------");
				sender.sendMessage(Main.Prefix+"§bHilfe: §e§l/ezentials help");
		
			}else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(Main.Prefix+"§9EZentials §aPlugin v."+Main.instance().getDescription().getVersion());
				sender.sendMessage(Main.Prefix+">>");
				sender.sendMessage(Main.Prefix+"§cVersion: §6v."+Main.instance().getDescription().getVersion());
				sender.sendMessage(Main.Prefix+"§cAutor: §6DevOFVictory");
				sender.sendMessage(Main.Prefix+"§cBegonnen am §622.12.2018");
				sender.sendMessage(Main.Prefix+"§2Ein Plugin im Auftrag von §6[none]");
				sender.sendMessage(Main.Prefix+"-----------------------------------------------");
				sender.sendMessage(Main.Prefix+"§bHilfe: §e§l/ezentials help");
				
				}else if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage("§e§lHilfe für EZentials");
					sender.sendMessage("- §6/ezentials help -> §7Rufe diese Hilfe auf!");
					sender.sendMessage("- §6/ezentials versioncheck -> §7Überprüfe nach Updates!");
					sender.sendMessage("- §6/freeze <Spieler> -> §7Friere einen Spieler ein!");
					sender.sendMessage("- §6/giveall -> §7Gebe das Item in deiner Hand jedem Spieler!");
					sender.sendMessage("- §6/build [<Spieler>] -> §7Nehme/Gebe einem Spieler die Baurechte!");
					sender.sendMessage("- §6/rename <Name> -> §7Bennene das Item in deiner Hand um!");
					sender.sendMessage("- §6/setlore <Lore> -> §7Setze die Beschreibung vom Item in deiner Hand!");
					sender.sendMessage("- §6/sendline <*/Spieler> <Nachricht> -> §7Sende eine Zeile ohne Prefix an den/die Spieler!");
					sender.sendMessage("- §6/rank <Spieler> <Rang> -> §7Setze den Rang vom Spieler!");
					sender.sendMessage("- §6/asudo <Spieler> <'OP'/'NONE'/Permission> <Nachricht/befehl> -> §7Führe Befehle und Chatnachrichten von anderen Spielern mit bestimmten Rechten aus");
					sender.sendMessage("- §6/togglejumppads -> §7(De-)aktiviere Jumppads auf dem Server!");
				}else if (args[0].equalsIgnoreCase("versioncheck")) {
					if (!sender.hasPermission("ezentials.versioncheck")) {
						sender.sendMessage(Main.noPerms("ezentials.versioncheck"));
						return true;
					}
					try {
						HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=63636").openConnection();
						connection.setDoOutput(true);
						connection.setRequestMethod("POST");
						connection.getOutputStream().write((Main.key+63636).getBytes("UTF-8"));
						String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
						if (!version.equalsIgnoreCase(Main.instance().getDescription().getVersion())) {
							
							sender.sendMessage(Main.Prefix+"§2Aktuelle Version: "+Main.instance().getDescription().getVersion());
							sender.sendMessage(Main.Prefix+"§eEs ist eine neue Version verfügbar vom Plugin! \n §6https://www.spigotmc.org/resources/ezentials.63636/");
							sender.sendMessage(Main.Prefix+"§2Neuste Version: "+version);
						}else {
							sender.sendMessage(Main.Prefix+"§2Aktuelle Version: "+Main.instance().getDescription().getVersion());
							sender.sendMessage(Main.Prefix+"§aDie Version vom Plugin ist auf dem neustem Stand!");
							sender.sendMessage(Main.Prefix+"§2Neuste Version: "+version);
						}
					}catch (IOException e) {
						sender.sendMessage(Main.Prefix+"§cAktuelle Version: "+Main.instance().getDescription().getVersion());
						sender.sendMessage(Main.Prefix+"§4ERROR: §cDas Plugin konnte keine Verbindung zu SpigotMC.org aufbauen!");
						
					}
	
				}
				} else {sender.sendMessage(Main.Prefix+"§cNutze §6/ezentials help §cfür Hilfe!");
				}
			}else {sender.sendMessage(Main.Prefix+"§cNutze §6/ezentials help §cfür Hilfe!");
			}
			
		return false;
	}

}
