package de.devofvictory.spigotlobbysystem.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.spigotlobbysystem.main.Main;
import de.devofvictory.spigotlobbysystem.utils.Utils;

public class Command_Lobby implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lobby")) {
			if (args.length == 0) {
				
				if (sender instanceof Player) {
					Player p = (Player)sender;
					
					if (p.hasPermission("lobby.cmd.lobby")) {

							if (Utils.isLobbySet()) {
								p.teleport(Utils.spawnLocation);
								p.sendMessage(Main.Prefix+"§aWelcome in the Lobby!");
							}else {
								p.sendMessage(Main.Prefix+"§cThe LobbySpawn is not set! Please contact an admin!");
							}
				}
				}
				
			}else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("build")) {
					if (sender.hasPermission("lobby.cmd.build")) {
					if (sender instanceof Player) {
						Player p = (Player)sender;
						if (Utils.isLobbySet()) {
						if (Utils.isLobby(p)) {
						if (Utils.allowBuild.contains(p)) {
							Utils.allowBuild.remove(p);
							p.sendMessage(Main.Prefix+"§cYou can no longer build!");
							p.setGameMode(GameMode.SURVIVAL);
							p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, (float)1, 5);
							Utils.gmItems.put(p, p.getInventory().getContents());
							Utils.setInv(p);
							p.getInventory().setHelmet(null);
							
						}else {
							Utils.allowBuild.add(p);
							p.sendMessage(Main.Prefix+"§aNow you can build!");
							p.setGameMode(GameMode.CREATIVE);
							p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, (float)1, 5);
							if (Utils.gmItems.containsKey(p)) {
								p.getInventory().setContents(Utils.gmItems.get(p));
								p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
							}else {
								p.getInventory().clear();
							}
						}
					}else {
						p.sendMessage(Main.Prefix+"§cYou are not in the Lobby!");
					}
					}else {
						p.sendMessage(Main.Prefix+"§cThe lobbyspawn isn't set! Please contact an admin!");
					}
					}
				}else {
					sender.sendMessage(Main.Prefix+"§cYou are not permitted to do that!");
				}
					
					
				}else if(args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("lobby.reload")) {
						sender.sendMessage(Main.Prefix+"§cYou are not permitted to do that!");
						return true;
					}
					Main.reloadCfg();
					sender.sendMessage(Main.Prefix+"§aReloaded Configuration-File!");
					
					
				}else if (args[0].equalsIgnoreCase("setlobby")) {
					if (sender.hasPermission("lobby.setlobby")) {
						if (sender instanceof Player) {
							Player p = (Player)sender;
							Location loc = p.getLocation();
							Utils.setLobby(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
							p.sendMessage(Main.Prefix+"§aThe LobbySpawn was set to §6"+p.getLocation().getBlockX()+" "+p.getLocation().getBlockY()+" "+p.getLocation().getBlockZ());
							
						}
					}
				}
				
			}else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("build")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						
					}else {
						sender.sendMessage(Main.Prefix+"§cPlayer §6"+args[1]+" §cis not online!");
					}
				}
				
			}
			
			
		}
		return false;
	}

}
