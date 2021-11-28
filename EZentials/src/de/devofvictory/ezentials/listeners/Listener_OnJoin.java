package de.devofvictory.ezentials.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.devofvictory.ezentials.main.Main;

public class Listener_OnJoin implements Listener{
	
	@EventHandler (priority=EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("ezentials.versioncheck.notify")) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=63636").openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.getOutputStream().write((Main.key+63636).getBytes("UTF-8"));
				String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
				if (!version.equalsIgnoreCase(Main.instance().getDescription().getVersion())) {
					
					p.sendMessage(Main.Prefix+"§2Aktuelle Version: "+Main.instance().getDescription().getVersion());
					p.sendMessage(Main.Prefix+"§eEs ist eine neue Version vom Plugin verfügbar! \n§8Download >> §6https://www.spigotmc.org/resources/ezentials.63636/");
					p.sendMessage(Main.Prefix+"§2Neuste Version: "+version);
				}
			}catch (IOException error) {
				p.sendMessage(Main.Prefix+"§cAktuelle Version: "+Main.instance().getDescription().getVersion());
				p.sendMessage(Main.Prefix+"§4ERROR: §cDas Plugin konnte keine Verbindung zu SpigotMC.org aufbauen!");
				
			}
		}
		
		
		Main.afkTime.put(p, 0);
		
	}

}
