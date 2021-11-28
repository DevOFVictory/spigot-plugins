package de.devofvictory.bungeeguilds.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.devofvictory.bungeeguilds.utils.GuildManager;
import net.md_5.bungee.BungeeCord;

public class Guild {
	
	private String name;
	private UUID owner;
	private HashMap<UUID, GuildRank> members;
	private List<UUID> mutedMembers;
	private String discordInvition;
	
	public Guild(String name, UUID owner) {
		this.name = name;
		this.owner = owner;
		this.members = new HashMap<UUID, GuildRank>();
		this.members.put(owner, GuildManager.getGuildRankByName("admin"));
		this.mutedMembers = new ArrayList<UUID>();
		this.discordInvition = "NONE";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public HashMap<UUID, GuildRank> getMembers() {
		return members;
	}

	public void setMembers(HashMap<UUID, GuildRank> members) {
		this.members = members;
	}

	public List<UUID> getMutedMembers() {
		return mutedMembers;
	}

	public void setMutedMembers(List<UUID> mutedMembers) {
		this.mutedMembers = mutedMembers;
	}
	
	public void setDiscordInvition(String discordInvition) {
		this.discordInvition = discordInvition;
	}
	public String getDiscordInvition() {
		return discordInvition;
	}
	
	public List<UUID> getOnlineMembers() {
		List<UUID> online = new ArrayList<UUID>();
		for (UUID uuid : members.keySet()) {
			if (BungeeCord.getInstance().getPlayer(uuid) != null) {
				online.add(uuid);
			}
		}
		return online;
	}
	
	

}
