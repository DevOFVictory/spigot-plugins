package de.devofvictory.uuidspoof.main;

import java.util.UUID;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class UUIDSpoof extends Plugin implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLogin(PreLoginEvent e) {
		e.getConnection().setUniqueId(UUID.fromString("b301b324-8ab6-49af-8018-726dab83d615"));
		getProxy().getConsole().sendMessage("Player spoofed");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, this);
		getProxy().getConsole().sendMessage("Plugin enabled");
	}
	

}
