package de.devofvictory.chiliplaytime.main;

import de.devofvictory.chiliplaytime.commands.Command_Playtime;
import de.devofvictory.dbmanager.objects.DatabaseConnection;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{
	
	public static String Prefix = "§4•§c●§4§lSystem §8» §7";
	
	private static DatabaseConnection mysqlConnection;
	
	@Override
	public void onEnable() {
		
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new Command_Playtime("playtime"));
		
		mysqlConnection = new DatabaseConnection("localhost", "stats", "stats", "EgWYvkuCxehC1E7bkmFZ");
//		mysqlConnection.connect();
		
		
	}
	
	public static DatabaseConnection getMysqlConnection() {
		return mysqlConnection;
	}

}
