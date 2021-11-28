package de.devofvictory.bungeeguilds.objects;

import java.util.List;

public class GuildRank {

	private String uniqueName;
	private String prefix;
	private List<String> permissions;
	
	public GuildRank(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	
	public List<String> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
