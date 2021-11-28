package de.devofvictory.prefix2000.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;


import de.devofvictory.prefix2000.utils.ScoreboardClass;

public class OnSneak implements Listener{

	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		
		Player p = e.getPlayer();
		
		ScoreboardClass.upDateScoreboard(p);
		
	}
}
