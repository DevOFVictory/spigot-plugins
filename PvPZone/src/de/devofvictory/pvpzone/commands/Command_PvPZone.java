package de.devofvictory.pvpzone.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.Region;

import de.devofvictory.pvpzone.main.PvPZone;
import de.devofvictory.pvpzone.utils.Variables;

public class Command_PvPZone implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// pvpzone help
		// pvpzone setup saveKit
		// pvpzone setup loadKit
		// pvpzone setup setArea
		
		if (sender.hasPermission("pvpzone.command")) {
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				
				sender.sendMessage("->");
				sender.sendMessage("§6/pvpzone help §8- §7Get this help");
				sender.sendMessage("§6/pvpzone setup saveKit §8- §7Save your current inventory as kit");
				sender.sendMessage("§6/pvpzone setup loadKit §8- §7Manuelly load the saved kit");
				sender.sendMessage("§6/pvpzone setup setArea §8- §7Save your marked region as pvparea");
				sender.sendMessage("§6/pvpzone setup setSpawn §8- §7Set the spawn point");
				sender.sendMessage("->");
				
			}else {
				sender.sendMessage(PvPZone.Prefix+"§cUnknown subcommand. Use §6/pvpzone help §cfor syntax.");
			}
		}else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("setup")) {
				if (args[1].equalsIgnoreCase("saveKit")) {
					
					if (sender instanceof Player) {
						Player p = (Player)sender;
						
						Variables.kit = p.getInventory().getContents();
						p.sendMessage(PvPZone.Prefix+"§aSuccessfully saved kit!");
						
					}else {
						sender.sendMessage(PvPZone.Prefix+"§cOnly a player can perform this command.");
					}
					
					
				}else if (args[1].equalsIgnoreCase("loadkit")) {
					
					if (sender instanceof Player) {
						Player p = (Player)sender;
						
						p.getInventory().setContents(Variables.kit);
						p.sendMessage(PvPZone.Prefix+"§aSuccessfully loaded kit!");
						
					}else {
						sender.sendMessage(PvPZone.Prefix+"§cOnly a player can perform this command.");
					}
					
					
				}else if (args[1].equalsIgnoreCase("setArea")) {
					
					if (sender instanceof Player) {
						
						Player p = (Player)sender;
					
						if (PvPZone.getWorldeditApi() != null) {
							try {
								Region rg = PvPZone.getWorldeditApi().getSession(p).getSelection(PvPZone.getWorldeditApi().getSession(p).getSelectionWorld());
								
								if (rg != null) {
									int x1 = rg.getMinimumPoint().getBlockX();
									int y1 = rg.getMinimumPoint().getBlockY();
									int z1 = rg.getMinimumPoint().getBlockZ();
									
									int x2 = rg.getMaximumPoint().getBlockX();
									int y2 = rg.getMaximumPoint().getBlockY();
									int z2 = rg.getMaximumPoint().getBlockZ();
									
									Location loc1 = new Location(Bukkit.getWorld(rg.getWorld().getName()), x1, y1, z1);
									Location loc2 = new Location(Bukkit.getWorld(rg.getWorld().getName()), x2, y2, z2);
									
									Variables.areaLoc1 = loc1;
									Variables.areaLoc2 = loc2;
									
									p.sendMessage(PvPZone.Prefix+"§aSuccessfully set pvp area!");

								}else {
									p.sendMessage("§cYou dont have marked a complete WorldEdit selection.");
								}
							
							} catch (Exception ex) {
								p.sendMessage("§cYou dont have marked a complete WorldEdit selection.");
							}
						}else {
							p.sendMessage(PvPZone.Prefix+"§cWorldedit seems to be no installed.");
						}
					}else {
						sender.sendMessage(PvPZone.Prefix+"§cOnly a player can perform this command.");
					}
					
				}else if (args[1].equalsIgnoreCase("setSpawn")) {
					
					if (sender instanceof Player) {
						Player p = (Player)sender;
						
						Variables.spawn = p.getLocation();
						
						p.sendMessage(PvPZone.Prefix+"§aThe spawn was set to your location.");
					}
					
					
				}else {
					sender.sendMessage(PvPZone.Prefix+"§cUnknown subcommand. Use §6/pvpzone help §cfor syntax.");
				}
			}else {
				sender.sendMessage(PvPZone.Prefix+"§cUnknown subcommand. Use §6/pvpzone help §cfor syntax.");
			}
		}else {
			sender.sendMessage(PvPZone.Prefix+"§cUnknown subcommand. Use §6/pvpzone help §cfor syntax.");
		}
		}else {
			sender.sendMessage(PvPZone.Prefix+"§cYou dont have enough permissions to perform this command!");
		}
		return true;
	}
	
	

}
