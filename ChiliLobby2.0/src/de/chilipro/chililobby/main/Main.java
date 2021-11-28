package de.chilipro.chililobby.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.chilipro.chililobby.listeners.Listener_OnBuild;
import de.chilipro.chililobby.listeners.Listener_OnDamage;
import de.chilipro.chililobby.listeners.Listener_OnDrop;
import de.chilipro.chililobby.listeners.Listener_OnHotBarChange;
import de.chilipro.chililobby.listeners.Listener_OnInteract;
import de.chilipro.chililobby.listeners.Listener_OnInvClick;
import de.chilipro.chililobby.listeners.Listener_OnJoin;
import de.chilipro.chililobby.listeners.Listener_OnKnockEnter;
import de.chilipro.chililobby.listeners.Listener_OnSignChange;
import de.chilipro.chililobby.commands.Command_Build;
import de.chilipro.chililobby.inventorys.Inv_Menu;
import de.chilipro.chililobby.utils.SpawnsFile;

public class Main extends JavaPlugin{
	
	public static String Prefix = "§f[§4§lChili§c§lLobby§f] ";
	private static Main plugin;
	
	public static HashMap<String, ItemStack[]> builditems = new HashMap<>();
	public static ArrayList<String> allowBuild = new ArrayList<>();
	
	public static Location spawn;
	public static Location wargame;
	public static Location crystal;
	public static Location pvp;
	public static Location citybuild;
	public static Location knock;
	
	public static void noPerms(Player p) {
		p.sendMessage(Main.Prefix+"§cDazu hast du keine Rechte!");
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		SpawnsFile.createCfg();
		SpawnsFile.readData();
		
		

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getPluginManager().registerEvents(new Listener_OnInteract(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnSignChange(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnHotBarChange(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDrop(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnDamage(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnBuild(), this);
		Bukkit.getPluginManager().registerEvents(new Inv_Menu(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnInvClick(), this);
		Bukkit.getPluginManager().registerEvents(new Listener_OnKnockEnter(), this);
		
		getCommand("build").setExecutor(new Command_Build());

		
		
		Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§2Plugin wurde aktiviert!");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Main.Prefix+"§4Plugin wurde deaktiviert!");
	}
	
	public static void connectToServer(Player p, String server) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		}catch (IOException error){
			error.printStackTrace();
		}
		p.sendPluginMessage(Main.getInstance(), "BungeeCord", baos.toByteArray());
	}
	
	public static Main getInstance() {
		return plugin;
	}
	

}
