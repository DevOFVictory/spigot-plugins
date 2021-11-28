package de.devofvictory.survivalproject.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.devofvictory.survivalproject.main.Main;

public class Command_PublicChest implements CommandExecutor{
	
	static Inventory publicInv = null;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			if (publicInv == null) {
				publicInv = Bukkit.createInventory(null, 9, "§6§lPublicChest");
			}
			
			Player p = (Player)sender;
			p.openInventory(publicInv);
			p.sendMessage(Main.Prefix+"§cAchtung: Die PublicChest wird nach Reload oder Neustart geleert!");
			
		}
		return true;
	}
	
	

}
