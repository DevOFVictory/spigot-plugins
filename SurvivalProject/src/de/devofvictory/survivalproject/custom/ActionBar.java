package de.devofvictory.survivalproject.custom;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBar {
	
	public static void sendActionBar(Player player, String text) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
	}

}
