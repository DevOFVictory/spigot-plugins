package de.devofvictory.prefix2000.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.devofvictory.prefix2000.main.Main;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class OnChat implements Listener{
	

	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		String msg = e.getMessage().replaceAll("%", "[PROZENT]");
		
		if(!msg.equalsIgnoreCase("")) {
		
		
		if(PermissionsEx.getUser(p).inGroup("default")) {
			p.sendMessage("§cBitte verifiziere dich, bovor du was schreibst! (§6/unlock§c)");
			e.setCancelled(true);
		}
		else if(PermissionsEx.getUser(p).inGroup("Spieler")) {
			msg.replaceAll("%", "PROZENT");
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);
			}catch (Exception error) {e.setFormat(Main.ChatPrefixSpieler+"§e"+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);}
				}else {e.setFormat(Main.ChatPrefixSpieler+"§e"+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);}
		
		
	}
		else if(PermissionsEx.getUser(p).inGroup("Premium")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixPremium+msg);
			}catch (Exception error) {e.setFormat(Main.ChatPrefixPremium+"§e"+p.getName()+" §8>> "+Main.TextPrefixPremium+msg);}
				}else {e.setFormat(Main.ChatPrefixPremium+"§e"+p.getName()+" §8>> "+Main.TextPrefixPremium+msg);}
		
		
	}
		else if(PermissionsEx.getUser(p).inGroup("Ultra")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixUltra+msg);
			}catch (Exception error) {e.setFormat(Main.ChatPrefixUltra+"§e"+p.getName()+" §8>> "+Main.TextPrefixUltra+msg);}
				}else {e.setFormat(Main.ChatPrefixUltra+"§e"+p.getName()+" §8>> "+Main.TextPrefixUltra+msg);}
		
		
			
	}
		else if(PermissionsEx.getUser(p).inGroup("Champion")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixChampion+msg);
			}catch (Exception error) {e.setFormat(Main.ChatPrefixChampion+"§e"+p.getName()+" §8>> "+Main.TextPrefixChampion+msg);}
				}else {e.setFormat(Main.ChatPrefixChampion+"§e"+p.getName()+" §8>> "+Main.TextPrefixChampion+msg);}
		
		
	}
		else if(PermissionsEx.getUser(p).inGroup("Legende")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixLegende+msg);
			}catch (Exception error) {e.setFormat(Main.ChatPrefixLegende+"§e"+p.getName()+" §8>> "+Main.TextPrefixLegende+msg);}
				}else {e.setFormat(Main.ChatPrefixLegende+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixLegende+msg);}
		
		
	}
		//Team
		else if(PermissionsEx.getUser(p).inGroup("Builder")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(" \n"+p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixCustom+msg+"§r\n ");
			}catch (Exception error) {e.setFormat(" \n"+Main.ChatPrefixBuilder+"§a"+p.getName()+" §8>> " +Main.TextPrefixBuilder+msg+"§r\n ");}
				}else {e.setFormat(" \n"+Main.ChatPrefixBuilder+"§a"+p.getName()+" §8>> " +Main.TextPrefixBuilder+msg+"§r\n ");}
		
		
	}	else if(PermissionsEx.getUser(p).inGroup("Dev")) {
		
		if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
			try {
			e.setFormat(" \n"+p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixCustom+msg+"§r\n ");
		}catch (Exception error) {e.setFormat(" \n"+Main.ChatPrefixDeveloper+"§b"+p.getName()+" §8>> " +Main.TextPrefixDeveloper+msg+"§r\n ");}
			}else {e.setFormat(" \n"+Main.ChatPrefixDeveloper+p.getName()+" §8>> "+Main.TextPrefixDeveloper+msg+"§r\n ");}
	
	
	}
		else if(PermissionsEx.getUser(p).inGroup("Moderator")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(" \n"+p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixCustom+msg+"§r\n ");
			}catch (Exception error) {e.setFormat(" \n"+Main.ChatPrefixModerator+"§6"+p.getName()+" §8>> " +Main.TextPrefixModerator+msg+"§r\n ");}
				}else {e.setFormat(" \n"+Main.ChatPrefixModerator+"§6"+p.getName()+" §8>> " +Main.TextPrefixModerator+msg+"§r\n ");}
		
		
	}
		else if(PermissionsEx.getUser(p).inGroup("Admin")) {
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(" \n"+p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixCustom+msg+"§r\n ");
			}catch (Exception error) {e.setFormat(" \n"+Main.ChatPrefixAdmin+"§c"+p.getName()+" §8>> " +Main.TextPrefixAdmin+msg+"§r\n ");}
				}else {e.setFormat(" \n"+Main.ChatPrefixAdmin+"§c"+p.getName()+" §8>> " +Main.TextPrefixAdmin+msg+"§r\n ");}
		
		
	}
		else if(PermissionsEx.getUser(p).inGroup("Owner")) {
			
			
			if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
				try {
				e.setFormat(" \n"+p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixCustom+msg+"§r\n ");
			}catch (Exception error) {e.setFormat(" \n"+Main.ChatPrefixOwner+"§c"+p.getName()+" §8>> " +Main.TextPrefixOwner+msg+"§r\n ");}
				}else {e.setFormat(" \n"+Main.ChatPrefixOwner+"§c"+p.getName()+" §8>> " +Main.TextPrefixOwner+msg+"§r\n ");}
		
			
	}else {
		msg.replaceAll("%", "PROZENT");
		if (p.getScoreboard().getTeam(p.getName()).getPlayers().contains(p)) {
			try {
			e.setFormat(p.getScoreboard().getTeam(p.getName()).getPrefix()+Main.ChatPrefixCustom+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);
		}catch (Exception error) {e.setFormat(Main.ChatPrefixSpieler+"§e"+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);}
			}else {e.setFormat(Main.ChatPrefixSpieler+"§e"+p.getName()+" §8>> "+Main.TextPrefixSpieler+msg);}
	}
		
		
		} 

}
}