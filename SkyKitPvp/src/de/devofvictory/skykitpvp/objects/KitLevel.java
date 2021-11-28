package de.devofvictory.skykitpvp.objects;

import java.util.HashMap;

public class KitLevel {
	
	private int levelInt;
	private int minKillsForUpgrade;
	private int regenerationTime;
	private String inventoryName;
	private HashMap<String, String> variables;
	
	
	public KitLevel(int levelInt, int minKillsForUpgrade, int regenerationTime, String inventoryName) {
		this.levelInt = levelInt;
		this.minKillsForUpgrade = minKillsForUpgrade;
		this.regenerationTime = regenerationTime;
		this.inventoryName = inventoryName;
		this.variables = new HashMap<String, String>();
	}
	
	public int getLevelInt() {
		return levelInt;
	}
	
	public HashMap<String, String> getVariables() {
		return variables;
	}
	
	public int getMinKillsForUpgrade() {
		return minKillsForUpgrade;
	}
	
	public void setMinKillsForUpgrade(int minKillsForUpgrade) {
		this.minKillsForUpgrade = minKillsForUpgrade;
	}
	
	public int getRegenerationTime() {
		return regenerationTime;
	}
	
	public void setRegenerationTime(int regenerationTime) {
		this.regenerationTime = regenerationTime;
	}
	
	public String getInventoryName() {
		return inventoryName;
	}
	
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	
	public String getVariableValue(String key) {
		if (variables.containsKey(key)) {
			return variables.get(key);
		}else {
			return "";
		}
	}
	
	public void setVariableValue(String key, String value) {
		variables.put(key, value);
	}
	

}
