package de.chilipro.chililobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.chilipro.chililobby.main.Main;

public class Listener_OnSignChange implements Listener{
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[WarGame]")) {
			e.setLine(0, "-*-");
			e.setLine(1, "§3§lWar§b§lGame");
			e.setLine(2, "§aClick to join");
			e.setLine(3, "-*-");
			e.getPlayer().sendMessage(Main.Prefix+"§3§lWar§b§lGame §a- Schild erfolgreich erstellt!");
			
		}else if (e.getLine(0).equalsIgnoreCase("[Crystal]")) {
			e.setLine(0, "-*-");
			e.setLine(1, "§5§lC§d§lr§5§ly§d§ls§5§lt§d§la§5§ll");
			e.setLine(2, "§aClick to join");
			e.setLine(3, "-*-");
			e.getPlayer().sendMessage(Main.Prefix+"§5§lC§d§lr§5§ly§d§ls§5§lt§d§la§5§ll §a- Schild erfolgreich erstellt!");
			
		}else if (e.getLine(0).equalsIgnoreCase("[CityBuild]")) {
			e.setLine(0, "-*-");
			e.setLine(1, "§2§lCityBuild");
			e.setLine(2, "§aClick to join");
			e.setLine(3, "-*-");
			e.getPlayer().sendMessage(Main.Prefix+"§2§lCityBuild §a- Schild erfolgreich erstellt!");
			
		}else if (e.getLine(0).equalsIgnoreCase("[Knock]")) {
			e.setLine(0, "-*-");
			e.setLine(1, "§6§lKnock");
			e.setLine(2, "§aClick to join");
			e.setLine(3, "-*-");
			e.getPlayer().sendMessage(Main.Prefix+"§6§lKnock §a- Schild erfolgreich erstellt!");
			
		}else if (e.getLine(0).equalsIgnoreCase("[PvP]")) {
			e.setLine(0, "-*-");
			e.setLine(1, "§7§lP§8v§7§lP");
			e.setLine(2, "§aClick to join");
			e.setLine(3, "-*-");
			e.getPlayer().sendMessage(Main.Prefix+"§7§lP§8v§7§lP §a- Schild erfolgreich erstellt!");
			
		}
	}

}
