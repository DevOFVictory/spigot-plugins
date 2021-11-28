package de.devofvictory.bungeeguilds.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.devofvictory.bungeeguilds.objects.Guild;
import de.devofvictory.bungeeguilds.objects.GuildRank;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GuildManager {
	
	public static List<Guild> registeredGuilds = new ArrayList<Guild>();
	public static List<GuildRank> registeredGuildRanks = new ArrayList<GuildRank>();
	public static HashMap<ProxiedPlayer, Guild> invations = new HashMap<ProxiedPlayer, Guild>();
	
	public static void registerGuild(Guild guild) {
		registeredGuilds.add(guild);
	}
	
	public static Guild getGuild(UUID uuid) {
		for (Guild guild : registeredGuilds) {
			if (guild.getMembers().containsKey(uuid)) {
				return guild;
			}
 		}
		return null;
	}
	
	public static boolean isInGuild(UUID uuid) {
		return getGuild(uuid) != null;
	}
	
	public static void registerGuildRank(GuildRank guildRank) {
		registeredGuildRanks.add(guildRank);
	}
	
	public static GuildRank getGuildRankByName(String name) {
		for (GuildRank guildRank : registeredGuildRanks) {
			if (guildRank.getUniqueName().equals(name)) {
				return guildRank;
			}
		}
		return null;
	}
	
	public static Guild getGuildByName(String name) {
		for (Guild guild : registeredGuilds) {
			if (guild.getName().equals(name)) {
				return guild;
			}
		}
		return null;
	}

}
