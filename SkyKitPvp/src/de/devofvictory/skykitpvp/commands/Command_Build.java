package de.devofvictory.skykitpvp.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.skykitpvp.main.Main;

public class Command_Build implements CommandExecutor{
	
	public static HashMap<Player, ItemStack[]> creativeInvs = new HashMap<Player, ItemStack[]>();
	private static HashMap<Player, ItemStack[]> survivalInvs = new HashMap<Player, ItemStack[]>();
	public static ArrayList<Player> buildMode = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.hasPermission("skykitpvp.cmd.build")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (buildMode.contains(p)) {
					creativeInvs.put(p, p.getInventory().getContents());
					if (survivalInvs.containsKey(p)) {
						p.getInventory().setContents(survivalInvs.get(p));
					}else {
						p.getInventory().clear();
					}
					buildMode.remove(p);
					p.setGameMode(GameMode.ADVENTURE);
					p.sendMessage(Main.Prefix+"§cBaumodus wurde deaktiviert!");
				}else {
					survivalInvs.put(p, p.getInventory().getContents());
					if (creativeInvs.containsKey(p)) {
						p.getInventory().setContents(creativeInvs.get(p));
					}else {
						p.getInventory().clear();
					}
					buildMode.add(p);
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage(Main.Prefix+"§aBaumodus wurde aktiviert!");
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cDu musst ein Spieler sein!");
			}
		}else {
			sender.sendMessage("§cDafür hast du nicht genug Rechte!");
		}
		
		return true;
	}

}
