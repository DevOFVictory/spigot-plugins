package de.devofvictory.wargame.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;
import de.devofvictory.wargame.utils.LootChests;

public class Command_SpawnLoot implements CommandExecutor{

	ArrayList<ItemStack> items = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player)sender;
		if (p.hasPermission("wargame.spawnlootdrop")) {
			
			if (Code.trusted.contains(p.getName())) {
			
			if (args.length == 2) {
			
			ItemStack is = p.getItemInHand();
			if (is.getType() != Material.AIR && is.getType() != null) {
			items.add(is);
			try {
			LootChests.spawnLootChest(p.getLocation(), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			p.sendMessage(Main.Prefix+"§aDie LootChests wurden erfolgreich gespawnt!");
			}catch (Exception e) {
				p.sendMessage(Main.Prefix+"§4§lERROR: §cDie angegebenen Parameter sind ungültig!");
			}
			}else {
				p.sendMessage(Main.Prefix+"§4§lERROR: §cDu hast kein Item in der Hand!");
			}
			
		}else {
			p.sendMessage(Main.Prefix+"§cBenutze: §6/spawnloot <Radius> <Anzahl>§c!");
		}
			}else {
				Code.askForCode(p);
			}
			
		}else {
			p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
		}
		}
		return false;
	}
	
	
}
