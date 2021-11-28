package de.devofvictory.wargame.utils;

import java.util.HashMap;

public class TraderShop {
	
	public static HashMap<String, Integer> skins = new HashMap<>();
	public static HashMap<String, Integer> skids = new HashMap<>();
	
	public static HashMap<String, Integer> getSkins() {
		
		return skins;
		
	}
	
	public static HashMap<String, Integer> getSkids() {
		
		return skids;
		
	}
	
	public static void importShop() {
		
//		FileManager.create("skids");
		FileManager.create("skins");
		
		for (String names : FileManager.skinsCfg.getKeys(false)) {
			skins.put(names, FileManager.skinsCfg.getInt(names));
		}
		
		
	}
	

}
