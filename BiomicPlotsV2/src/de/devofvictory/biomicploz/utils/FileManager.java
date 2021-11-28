package de.devofvictory.biomicploz.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File plotsFile = new File("plugins/BiomicPlots","plots.yml");
	public static FileConfiguration plotsCfg = YamlConfiguration.loadConfiguration(plotsFile);
	
	public static void save(String file) {
		try {
			switch (file) {
			case "plots":
				plotsCfg.save(plotsFile);
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
		case "plots":
			if (!plotsFile.exists()) {
				try {
					plotsFile.createNewFile();
					plotsCfg.load(plotsFile);
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
