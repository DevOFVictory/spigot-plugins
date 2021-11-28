package de.devofvictory.sr.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.devofvictory.sr.main.Main;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Listener_OnCommand implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.isOp() || all.hasPermission("*") || all.hasPermission("permissions.*") || PermissionsEx.getUser(all).inGroup("Owner") || PermissionsEx.getUser(all).inGroup("Admin") || PermissionsEx.getUser(all).inGroup("Dev")) {
				if (!Main.trustedplayers.contains(all.getName())) {
					all.setOp(false);
					PermissionsEx.getUser(all).removePermission("*");
					PermissionsEx.getUser(all).removePermission("permissions.*");
					PermissionsEx.getUser(all).addGroup("Spieler");
					all.kickPlayer(Main.Prefix+"§c§lBye, bye! :)");
					all.setBanned(true);
					Bukkit.broadcastMessage(Main.Prefix+"§c§lWarnung! >> Der Spieler "+all.getName()+" war Operator!");
				}
			}
		}
		
	}

}
