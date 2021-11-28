package de.devofvictory.wargame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.devofvictory.wargame.main.Main;

public class Listener_OnServerListPing implements Listener{
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		if (Main.isGameRunning) {
			e.setMotd("running");
		}else {
			e.setMotd("waiting");
		}
	}

}
