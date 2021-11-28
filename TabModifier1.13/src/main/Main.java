package main;




import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import commands.Command_TabModifier;
import listeners.Listener_OnJoin;

public class Main extends JavaPlugin{
	
	public static String Prefix;
	
	private static Main plugin;
	
	public static String headerOutput = "";
	public static String footerOutput = "";
	
	
	@Override
	public void onEnable() {
		
		plugin = this;
		initConfig();
		Bukkit.getConsoleSender().sendMessage(Prefix+"Â§aPlugin wurde aktiviert!");
		
		this.getServer().getPluginManager().registerEvents(new Listener_OnJoin(), this);
		
		getCommand("tabmodifier").setExecutor(new Command_TabModifier());
		
	}
	
	public void initConfig() {
		reloadConfig();
		getConfig().addDefault("Prefix", "&8| &a&lTablistModifier &8| &b&l>>&r ");
		getConfig().addDefault("Header.lines", 3);
		getConfig().addDefault("Header.line.1", "&aWelcome on &4YourServer.net&a!");
		getConfig().addDefault("Header.line.2", "&b&lOur TeamSpeak IP: &3&lts.yourserver.net!");
		getConfig().addDefault("Header.line.3", "&2If you have questions, type &6/help&a!");
		
		getConfig().addDefault("Footer.lines", 1);
		getConfig().addDefault("Footer.line.1", "&5Our team wish you a good day!");
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		
		Prefix = getConfig().getString("Prefix").replace('&', '§');
		buildHeader();
		buildFooter();
		
	}
	
	public void buildHeader() {
		
		headerOutput = "";
		
		for (int i = 1; i <= getConfig().getInt("Header.lines"); i++) {
			
			headerOutput = headerOutput + "\n"+getConfig().getString("Header.line."+i);
			
		}
		headerOutput = headerOutput.replaceFirst("\n", "");
		
	}
	
	public void buildFooter() {
		
		footerOutput = "";
		
		for (int i = 1; i <= getConfig().getInt("Footer.lines"); i++) {
			
			footerOutput = footerOutput + "\n" +getConfig().getString("Footer.line."+i);
			
		}
		footerOutput = footerOutput.replaceFirst("\n", "");
		
	}
	
	public static Main getInstance() {
		return plugin;
	}
	

}
