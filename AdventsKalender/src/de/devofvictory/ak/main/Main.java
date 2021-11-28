package de.devofvictory.ak.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.ak.commands.Command_Adventskalender;
import de.devofvictory.ak.listeners.OnInteract;
import de.devofvictory.ak.listeners.OnInventoryClick;




public class Main extends JavaPlugin{
	
	//Items
	public static ItemStack advent1 = new ItemStack(Material.CHEST);
	public static ItemMeta meta = advent1.getItemMeta().setDisplayName();
	
	public static void setMeta(ItemMeta meta) {
		
	}
	
	
	
	
	
	
	private static Main plugin;
	
	public static Inventory inv = null;
	
	public static String Prefix = "[§6Advents§eKalender] §r";
	
	public void onEnable() {
		
		
		 //ListenerRegister
		 PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new OnInventoryClick(), this);
		 pm.registerEvents(new OnInteract(), this);
	 	 
		 
		 //CmdRegister
		 getCommand("adventskalender").setExecutor(new Command_Adventskalender());
		 		
		 plugin=this;
		
		
		
	}
	
	public void onDisable() {
		
		
	}
	
	public void regCmd(String cmd) {
		
	}
	public static Main getInstance() {
		 return plugin;
	 }
}
