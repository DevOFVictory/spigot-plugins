package de.devofvictory.soulboundenchantment.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.soulboundenchantment.main.SoulboundEnchantment;
import de.devofvictory.soulboundenchantment.utils.OtherUtils;
import de.devofvictory.soulboundenchantment.utils.RomanNumber;

public class Command_Soulbound implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// /soulbound 5							Soulbound 5 book
		// /soulbound 5 DevOFVictory			Enchant item in hand with soulbound 5 on DevOFVictory
		
		if (sender instanceof Player) {
			
			Player p = (Player)sender;
			
			if (args.length == 1) {
				
				int lvl = 0;
				
				try {
					lvl = Integer.parseInt(args[0]);
				}catch (NumberFormatException ex) {
					sender.sendMessage("§cThe given level is invalid!");
					return true;
				}
				
				if (lvl >= 1 && lvl <= 6) {
					ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
					EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
					bookMeta.addStoredEnchant(SoulboundEnchantment.ench, lvl, true);
					bookMeta.setLore(Arrays.asList(new String[] {"§7Soulbound "+RomanNumber.toRoman(lvl)}));
					book.setItemMeta(bookMeta);
					p.getInventory().addItem(book);
					
					sender.sendMessage("§aYou got a Soulbound "+lvl+" enchantment book!");
				}else {
					sender.sendMessage("§cThe level have to be a number in range of 1-6");
				}
				
			}else if (args.length == 2) {
					
				if (p.getItemInHand().getType() != Material.AIR) {
					
					ItemStack hand = p.getItemInHand();
					if (OtherUtils.isAllowedMaterial(hand.getType())) {
						
						int lvl = 0;
						
						try {
							lvl = Integer.parseInt(args[0]);
						}catch (NumberFormatException ex) {
							sender.sendMessage("§cThe given level is invalid!");
							return true;
						}
						
						if (lvl >= 1 && lvl <= 6) {
							
							ItemMeta handMeta = hand.getItemMeta();
							
							if (!handMeta.hasEnchant(SoulboundEnchantment.ench)) {
							
								handMeta.addEnchant(SoulboundEnchantment.ench, lvl, true);
	
								List<String> lore = handMeta.hasLore() ? handMeta.getLore() : new ArrayList<String>();
								
								lore.add("§7Soulbound "+RomanNumber.toRoman(lvl) + " ("+args[1]+")");
								
								handMeta.setLore(lore);
								
								hand.setItemMeta(handMeta);
								
								p.updateInventory();
								
								sender.sendMessage("§aYou have soulbound this item with level "+lvl+" to "+args[1]+"!");
							
							}else {
								p.sendMessage("§cThis item is already soulbound!");
							}
						}else {
							sender.sendMessage("§cThe level have to be a number in range of 1-6");
						}
						
												
					}else {
						p.sendMessage("§cYou can only enchant armor, weapons and tools!");
					}
					
				}else {
					p.sendMessage("§cYou are not holding an item in you hand!");
				}
					
					
				
			}else {
				sender.sendMessage("§cUsage: §6/soulbound <Level>");
				sender.sendMessage("§cUsage: §6/soulbound <Level> <Player>");
			}
		}
		
		return true;
	}

}
