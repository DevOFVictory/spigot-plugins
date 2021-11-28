package de.devofvictory.ezentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.ezentials.main.Main;


public class Command_GiveAll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("giveall")) {
			String perm = "ezentials.cmd.giveall";
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				
				if (p.hasPermission(perm)) {
					
					ItemStack hand = p.getItemInHand();
					if ((hand != null) && (p.getItemInHand().getType() != Material.AIR)){
					
					
					int amount = hand.getAmount();
					String name = hand.getItemMeta() == null ? hand.getType().name() : hand.getItemMeta().getDisplayName() ==null ? hand.getType().name() : hand.getItemMeta().getDisplayName();
					
					p.sendMessage(Main.Prefix+"§aDu hast jedem Spieler das Item §2"+name+" §6"+amount+" §amal gegeben!");
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (all != p) {
						all.sendMessage("§aJeder Spieler hat das Item §2"+name+" §6"+amount+" §amal erhalten!");
						if(all.getInventory().firstEmpty() == -1) {
							all.getWorld().dropItemNaturally(all.getLocation(), hand);
						}else {all.getInventory().addItem(new ItemStack[] {hand});
							
						}
						}
					}
					}else {
						p.sendMessage(Main.Prefix+"§cDu hast kein Item in der Hand!");
					return true;
					}
					
				}else {
					p.sendMessage(Main.noPerms(perm));
				return true;
				}
				
			}else {
				sender.sendMessage(Main.Prefix+"§cFür diesen Command musst du ein Spieler sein!");
			return true;
			}
		}
		
		return false;
	}

}
