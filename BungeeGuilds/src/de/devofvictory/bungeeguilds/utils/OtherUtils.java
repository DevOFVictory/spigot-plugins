package de.devofvictory.bungeeguilds.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class OtherUtils {
	
	public static boolean hasPermissions(ProxiedPlayer pp, String perm) {
		if (pp.hasPermission("guild."+perm)) {
			return true;
		}else {
			GuildMessageUtil.sendFormattedMessage(pp, "NoPerm");
			return false;
		}
	}
	
	public static boolean isGuildNameValid(String guildName) {
		if (guildName.length() >= 3 && guildName.length() <= 15) {
			if (guildName.matches("^[a-zA-Z0-9_]+$")) {
				return true;
			}
		}else {
			return false;			
		}
		return false;
	}

}
