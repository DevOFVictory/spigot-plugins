package listeners;



import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.Main;
import utils.Util_Tab;

public class Listener_OnJoin implements Listener{
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Util_Tab.sendTab(p, Main.headerOutput.replace('&', '§'), Main.footerOutput.replace('&', '§'));
		
	}
	

}
