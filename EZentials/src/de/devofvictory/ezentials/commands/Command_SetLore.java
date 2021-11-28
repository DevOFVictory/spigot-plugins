package de.devofvictory.ezentials.commands;



import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.ezentials.main.Main;

public class Command_SetLore implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("setlore")) {
			String perm = "ezentials.cmd.setlore";
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission(perm)) {
					
					if(p.getItemInHand().getType() == Material.AIR || p.getItemInHand() == null) {
						p.sendMessage(Main.Prefix+"§cDu hast kein Item in der Hand!");
						return true;
					}
					if(args.length == 0) {
						p.sendMessage(Main.Prefix+"§cBenutze §6/setlore <Lore>§c!");
						return true;
					}
					
					String message = "";
					ArrayList<String> lore = new ArrayList<>();
					for (int i = 0; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					
					String[] splitted = message.split(".,");
					
					for (int i = 0; i<splitted.length; i++) {
						lore.add(this.formatAll(splitted[i]));
					}
					
					
					ItemStack item = this.setLore(p.getItemInHand(), lore);
					p.getInventory().remove(p.getItemInHand());
					p.getInventory().addItem(item);
					p.sendMessage(Main.Prefix+"§aDie Item-Beschreibung wurde zu §5"+message.replace('&', '§')+" §ageändert!");
					
				}else {
					p.sendMessage(Main.noPerms(perm));
				}
			}else {sender.sendMessage(Main.Prefix+"§cFür diesen Command musst du ein Spieler sein!");}
		}
		
		return false;
	}
	public String formatAll(String format) {
		format = format.replace('&', '§');
		return format;
	}
	
	public ItemStack setLore(ItemStack item, ArrayList<String> message) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(message);
		item.setItemMeta(meta);
		return item;
	}

}
