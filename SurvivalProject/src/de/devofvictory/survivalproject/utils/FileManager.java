package de.devofvictory.survivalproject.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File teleportersFile = new File("plugins/SurvivalProject","teleporters.yml");
	public static FileConfiguration teleportersCfg = YamlConfiguration.loadConfiguration(teleportersFile);
	
	public static void save(String file) {
		try {
			switch (file) {
			case "teleporters":
				teleportersCfg.save(teleportersFile);
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
		
		
		switch (file) {
		case "teleporters":
			if (!teleportersFile.exists()) {
				try {
					teleportersFile.createNewFile();
					teleportersCfg.load(teleportersFile);
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
