package de.devofvictory.wargame.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File statsFile = new File("../../../DataFiles/WarGame","stats.yml");
	public static FileConfiguration statsCfg = YamlConfiguration.loadConfiguration(statsFile);
	
	public static File skinsFile = new File("../../../DataFiles/WarGame","skins.yml");
	public static FileConfiguration skinsCfg = YamlConfiguration.loadConfiguration(skinsFile);
	
	public static File skidsFile = new File("../../../DataFiles/WarGame","skids.yml");
	public static FileConfiguration skidsCfg = YamlConfiguration.loadConfiguration(skidsFile);
	
	public static File spindsFile = new File("../../../DataFiles/WarGame","spinds.yml");
	public static FileConfiguration spindsCfg = YamlConfiguration.loadConfiguration(spindsFile);
	
	public static void save(String file) {
		try {
			switch (file) {
			case "stats":
				statsCfg.save(statsFile);
				break;
			case "skins":
				skinsCfg.save(skinsFile);
				break;
			case "skids":
				skidsCfg.save(skidsFile);
				break;
			case "spinds":
				spindsCfg.save(spindsFile);
				break;
			default:
				System.out.println("Konnte Parameter nicht finden!");
				break;
			}
			
		}catch (IOException ex) {
			// TODO: handle exception
		}
	}
	
	public static void create(String file) {
		
		Bukkit.broadcastMessage("Moin1");
		
		switch (file) {
		case "stats":
			Bukkit.broadcastMessage("Moin2");
			if (!statsFile.exists()) {
				Bukkit.broadcastMessage("Moin3");
				try {
					statsFile.createNewFile();
					statsCfg.load(statsFile);
					Bukkit.broadcastMessage("Moin4");
				} catch (IOException | InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "skins":
			if (!skinsFile.exists()) {
				try {
					skinsFile.createNewFile();
					skinsCfg.load(skinsFile);
				} catch (IOException | InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "skids":
			if (!skidsFile.exists()) {
				try {
					skinsFile.createNewFile();
					skidsCfg.load(skinsFile);
				} catch (IOException | InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "spinds":
			if (!spindsFile.exists()) {
				try {
					spindsFile.createNewFile();
					spindsCfg.load(spindsFile);
				} catch (IOException | InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			System.out.println("Konnte Parameter nicht finden!");
			break;
		}
		
		
		
	}

}
