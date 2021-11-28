package de.devofvictory.ezentials.commands;


import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.ezentials.main.Main;

public class Command_Rename implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("rename")) {
			String perm = "ezentials.cmd.rename";
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				if (p.hasPermission(perm)) {
					if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
					if (args.length >= 1) {
						
						StringBuilder stringbuilder = new StringBuilder();
						for (int i = 0; i < args.length; i++) {
							if (i != 0) stringbuilder.append(" ");
							stringbuilder.append(args[i]);
						}
						String output = stringbuilder.toString();
					
					ItemStack item = p.getItemInHand();
					formatItem(output, item);
					p.getInventory().remove(p.getItemInHand());
					p.getInventory().addItem(item);
					
					p.sendMessage(Main.Prefix+"§aDer Name vom Item wurde zu §6"+output.replace('&', '§')+" §ageändert!");
					
					}else {
						sender.sendMessage(Main.Prefix+"§cBenutze §6/rename <Name>§c!");
						}
					}else {
						p.sendMessage(Main.Prefix+"§cDu hast kein Item in der Hand!");
					}
				}else {sender.sendMessage(Main.noPerms(perm));
				}
				
			}else {sender.sendMessage(Main.Prefix+"§cFür diesen Command musst du ein Spieler sein!");
			}
		}
		return false;
	}
	
	static ItemStack formatItem(String newname, ItemStack item) {
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(newname.replace('&', '§'));
		item.setItemMeta(meta);
		
		return item;
	}

}
