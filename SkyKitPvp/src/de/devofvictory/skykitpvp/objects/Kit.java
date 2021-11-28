package de.devofvictory.skykitpvp.objects;

import java.util.ArrayList;

import org.bukkit.Material;

public class Kit {
	
	private int price;
	private Material displayType;
	private String displayName;
	private SuperPower superPower;
	private String uniqueName;
	private ArrayList<KitLevel> kitLevels;
	
	public Kit(String uniqueName, String displayName, int price, Material displayType, SuperPower superPower) {
		this.price = price;
		this.displayType = displayType;
		this.displayName = displayName;
		this.superPower = superPower;
		this.uniqueName = uniqueName;
		this.kitLevels = new ArrayList<KitLevel>();
	}
	
	
	
	public Material getDisplayType() {
		return this.displayType;
	}
	
	public int getMaxLevel() {
		return kitLevels.size();
	}
	
	public void addKitLevel(KitLevel level) {
		if (!kitLevels.contains(level))
			kitLevels.add(level);
	}
	
	public void removeKitLevel(KitLevel level) {
		if (kitLevels.contains(level))
			kitLevels.remove(level);
	}
	
	public ArrayList<KitLevel> getKitLevels() {
		return kitLevels;
	}
	
	public String getUniqueName() {
		return uniqueName;
	}
	
	
	public SuperPower getSuperPower() {
		return superPower;
	}
	
	public void setSuperPower(SuperPower superPower) {
		this.superPower = superPower;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public KitLevel getKitLevelForLevel(int levelInt) {
		for (KitLevel level : kitLevels) {
			if (level.getLevelInt() == levelInt) {
				return level;
			}
		}
		return null;
	}
	
	
	
	public void setDisplayType(Material displayType) {
		this.displayType = displayType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
