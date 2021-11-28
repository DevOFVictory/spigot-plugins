package de.devofvictory.ezentials.commands;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.ezentials.main.Main;



public class Command_EZItems implements CommandExecutor, Listener{
	
	public static Inventory inv;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("EZItems")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				String perm = "ezentials.cmd.ezitems";
				if (p.hasPermission(perm)) {
					
					inv = p.getServer().createInventory(null, 9, "§3§lEZ§b§lItems §4§l<3");
					
					ItemStack protshild = new ItemStack(Material.EYE_OF_ENDER);
					ItemMeta protshildMeta = protshild.getItemMeta();
					ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
					ItemMeta placeholderMeta = placeholder.getItemMeta();
					placeholderMeta.setDisplayName("§r§r§r");
					placeholder.setItemMeta(placeholderMeta);
					protshildMeta.setDisplayName("§5§lSchutzschild");
					protshild.setItemMeta(protshildMeta);
					
					inv.setItem(0, placeholder);
					inv.setItem(1, placeholder);
					inv.setItem(2, placeholder);
					inv.setItem(3, placeholder);
					inv.setItem(4, protshild);
					inv.setItem(5, placeholder);
					inv.setItem(6, placeholder);
					inv.setItem(7, placeholder);
					inv.setItem(8, placeholder);
					p.openInventory(inv);
					
				}else {
					p.sendMessage(Main.noPerms(perm));
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getClickedInventory() != null) {
		if (e.getClickedInventory().getName().equalsIgnoreCase("§3§lEZ§b§lItems §4§l<3")) {
			if (e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta() != null) {
			if (p.hasPermission("ezentials.cmd.ezitems")) {
				e.setCancelled(true);
				if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§r§r§r")) {
						p.sendMessage(Main.Prefix+"§cComming Soon...");
					}
				}else {
					String ItemName = e.getCurrentItem().getItemMeta().getDisplayName();
					if (ItemName.equalsIgnoreCase("§5§lSchutzschild")) {
						p.playSound(p.getLocation(), Sound.LEVEL_UP, (float) 10, (float) 5);
						ItemStack protshild = new ItemStack(Material.EYE_OF_ENDER);
						ItemMeta protshildMeta = protshild.getItemMeta();
						ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
						ItemMeta placeholderMeta = placeholder.getItemMeta();
						placeholderMeta.setDisplayName("§r§r§r");
						placeholder.setItemMeta(placeholderMeta);
						protshildMeta.setDisplayName("§5§lSchutzschild");
						protshild.setItemMeta(protshildMeta);
						p.getInventory().setItem(p.getInventory().getHeldItemSlot(), protshild);
						p.sendMessage(Main.Prefix+"§aViel Spaß mit diesem Item!");
						p.closeInventory();
					}
				}
			}
		}
			}
		}
		
	}

}
