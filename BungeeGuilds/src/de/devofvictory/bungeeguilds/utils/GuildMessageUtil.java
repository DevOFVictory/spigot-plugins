package de.devofvictory.bungeeguilds.utils;

import java.util.HashMap;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class GuildMessageUtil {
	
	private static HashMap<String, String> messages = new HashMap<String, String>();
	
	public static void sendFormattedMessage(CommandSender pp, String msgKey) {
		pp.sendMessage(TextComponent.fromLegacyText(getPrefix()+(messages.get(msgKey) != null ? messages.get(msgKey) : "§8[Message '"+msgKey+"' is not set]")));
	}
	
	public static String getPrefix() {
		return messages.get("Prefix") != null ? messages.get("Prefix") : "§8[§e§lBungee§6§lGuilds§8] §8";
	}
	
	public static void sendMessage(CommandSender pp, String message) {
		pp.sendMessage(TextComponent.fromLegacyText(getPrefix()+message));
	}
	


}
