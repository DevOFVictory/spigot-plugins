package de.devofvictory.skykitpvp.eco.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.skykitpvp.eco.commands.Command_Coins;
import de.devofvictory.skykitpvp.eco.commands.Command_Eco;
import de.devofvictory.skykitpvp.eco.utils.EcoManager;
import de.devofvictory.skykitpvp.eco.utils.EcoVariables;
import de.devofvictory.skykitpvp.eco.utils.databasemanager.enums.EcoOutputMessage;
import de.devofvictory.skykitpvp.eco.utils.databasemanager.objects.EcoDatabaseConnection;

public class EcoMain extends JavaPlugin {
	
	
	private static EcoMain plugin;
	
	public static String getConsolePrefix() {
		return EcoVariables.consolePrefix;
	}
	
	@Override
	public void onEnable() {
		
		reloadConfig();
		saveConfig();
		plugin = this;
		
		loadDefaults();
		
		setupMysql();
		
		registerCommands();
		
		Bukkit.getConsoleSender().sendMessage(EcoVariables.prefix+"Das SkyKitPvp-Coins Plugin wurde aktiviert!");
		Bukkit.getConsoleSender().sendMessage(EcoVariables.prefix+"Die SkyKitPvp-Coins Schnittstelle wurde aktiviert!");
	}
	

	private void setupMysql() {
		
		EcoVariables.connection = new EcoDatabaseConnection(EcoVariables.host, EcoVariables.database, EcoVariables.username, EcoVariables.password);
		EcoVariables.connection.connect();
		EcoVariables.connection.setOutputMessage(EcoOutputMessage.EVERYTHING);
		
		ArrayList<String> createValues = new ArrayList<String>();
		createValues.add("uuid VARCHAR(50)");
		createValues.add("lastname VARCHAR(16)");
		createValues.add("coins INTEGER(10)");
		
		EcoVariables.connection.createTableIfNotExist("playerCoins", createValues);
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			EcoManager.loadFromMySql(online, true);
		}
		
	}

	@Override
	public void onDisable() {
		for (Player online : Bukkit.getOnlinePlayers()) {
			EcoManager.saveToMySql(online, false);
		}
	}
	
	public static EcoMain getInstance() {
		return plugin;
	}
	
	private void loadDefaults() {
		if (!EcoMain.getInstance().getConfig().isSet("Prefix")) {
			EcoMain.getInstance().getConfig().set("Prefix", EcoVariables.prefix);
			EcoMain.getInstance().saveConfig();
		}else {
			EcoVariables.prefix = EcoMain.getInstance().getConfig().getString("Prefix");
		}
		
		if (!EcoMain.getInstance().getConfig().isSet("MYSQL.Host")) {
			EcoMain.getInstance().getConfig().set("MYSQL.Host", EcoVariables.host);
			EcoMain.getInstance().saveConfig();
		}else {
			EcoVariables.host = EcoMain.getInstance().getConfig().getString("MYSQL.Host");
		}
		
		if (!EcoMain.getInstance().getConfig().isSet("MYSQL.Database")) {
			EcoMain.getInstance().getConfig().set("MYSQL.Database", EcoVariables.database);
			EcoMain.getInstance().saveConfig();
		}else {
			EcoVariables.database = EcoMain.getInstance().getConfig().getString("MYSQL.Database");
		}
		
		if (!EcoMain.getInstance().getConfig().isSet("MYSQL.User")) {
			EcoMain.getInstance().getConfig().set("MYSQL.User", EcoVariables.username);
			EcoMain.getInstance().saveConfig();
		}else {
			EcoVariables.username = EcoMain.getInstance().getConfig().getString("MYSQL.User");
		}
		
		if (!EcoMain.getInstance().getConfig().isSet("MYSQL.Password")) {
			EcoMain.getInstance().getConfig().set("MYSQL.Password", EcoVariables.password);
			EcoMain.getInstance().saveConfig();
		}else {
			EcoVariables.password = EcoMain.getInstance().getConfig().getString("MYSQL.Password");
		}
	}
	
	private void registerCommands() {
		getInstance().getCommand("coins").setExecutor(new Command_Coins());
		getInstance().getCommand("eco").setExecutor(new Command_Eco());
	}

}
