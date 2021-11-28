package de.devofvictory.wargame.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpindManager {
	
	public static HashMap<UUID, List<String>> playerSkins = new HashMap<>();
	public static HashMap<UUID, List<String>> playerSkids = new HashMap<>();
	
	public static void importSpinds() {
		FileManager.create("spinds");
		for (String key : FileManager.spindsCfg.getKeys(false)) {
			playerSkins.put(UUID.fromString(key), FileManager.spindsCfg.getStringList(key+".skins"));
		}
	}
	
	public static void exportSpinds() {
		for (UUID uuid : playerSkins.keySet()) {
			FileManager.spindsCfg.set(uuid.toString()+".skins", getSkins(uuid));
//			FileManager.spindsCfg.set(uuid.toString()+".skids", playerSkids);
		}
		FileManager.save("spinds");
	}
	
	public static List<String> getSkins(UUID uuid) {
		return playerSkins.get(uuid);
	}
	
	public static void addSkin(UUID uuid, String skinName) {
		List<String> skins = playerSkins.get(uuid);
		skins.add(skinName);
		playerSkins.put(uuid, skins);
	}
	
	public static void setSkinsIfNessasry(UUID uuid) {
		if (getSkins(uuid) == null) {
			List<String> list = new ArrayList<>();
			playerSkins.put(uuid, list);
		}
	}

}
