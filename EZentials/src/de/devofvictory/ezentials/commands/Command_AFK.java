package de.devofvictory.ezentials.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import de.devofvictory.ezentials.main.Main;

public class Command_AFK implements CommandExecutor{
	
	public static ArrayList<Player> afk = new ArrayList<>();
	
	public static HashMap<Player, String> afkMessages = new HashMap<>();
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("afk")) {
			String perm = "ezentials.cmd.afk";
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission(perm)) {
					
					if (afk.contains(p)) {
						removeAfk(p);
					}else {
						if (args.length == 0) {
						setAfk(p, "null");
					}else {
						String message = "";
						
						for (int i = 0; i < args.length; i++) {
							message = message + args[i] + " ";
						}
						setAfk(p, message);
					}
					}
					
				}else {
					p.sendMessage(Main.noPerms(perm));
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cFür diesen Befehl musst du ein Spieler sein!");
			}
		}
		return true;
	}
	
	public static void autoAfk(Player p) {
		if (p.hasPermission("ezentials.cmd.afk") || p.hasPermission("ezentials.afk.kick.bypass")) {
			setAfk(p, "null");
			
		}else {
			p.kickPlayer("§f[§4ChiliPro.de§f] §cDu warst 5 Minuten lang AFK!");
		}
	}
	
	
	
	
	
	public static void setAfk(Player p, String string) {
		if (string.equalsIgnoreCase("null")) {
				
				afk.add(p);
				Location loc = p.getLocation().add(0.0D, 0.2D, 0.0D);
				ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
				
				as.setCustomName("§f§l[§4§lAFK§f§l]");
				as.setCustomNameVisible(true);
				as.setVisible(false);
				as.setGravity(false);
				as.setMetadata(p.getName(), new FixedMetadataValue(Main.instance(), true));
				
		
				p.sendMessage(Main.Prefix+"§aDu bist nun AFK!");
				Bukkit.broadcastMessage(Main.afkPrefix+" §6"+p.getName()+ " §cist nun abwesend!");
		}else {
			afk.add(p);
			afkMessages.put(p, string);
			Location loc = p.getLocation().add(0.0D, 0.2D, 0.0D);
			ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
			
			as.setCustomName("§f§l[§4§lAFK§f§l] §8>>§c "+string);
			as.setCustomNameVisible(true);
			as.setVisible(false);
			as.setGravity(false);
			as.setMetadata(p.getName(), new FixedMetadataValue(Main.instance(), true));
			
			
			p.sendMessage(Main.Prefix+"§aDu bist nun AFK!");
			Bukkit.broadcastMessage(Main.afkPrefix+" §6"+p.getName()+ " §cist nun abwesend! §8>> §c"+string);

		}
		
	}
	
	public static void removeAfk(Player p) {
		afkMessages.remove(p);
		Main.afkTime.put(p, 0);
		afk.remove(p);
		p.sendMessage(Main.Prefix+"§cDu bist nun nicht mehr AFK!");
		Bukkit.broadcastMessage(Main.afkPrefix+" §6"+p.getName()+ " §aist nun wieder da!");
		
		for (World world : Bukkit.getWorlds()) {
		for (Entity entities : world.getEntities()) {
			if (entities.hasMetadata(p.getName())) {
				entities.remove();
			}
		}
		}
	}

}
