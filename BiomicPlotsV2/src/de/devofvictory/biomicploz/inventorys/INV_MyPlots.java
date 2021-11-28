package de.devofvictory.biomicploz.inventorys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Utils;

public class INV_MyPlots implements Listener{
	
	public static void callInv(Player p, UUID target) {
		
		Inventory inv;
		
		if (p.getUniqueId().equals(target)) {
			inv = Bukkit.createInventory(null, 1*9, "§e§lMeine Grundstücke");
		}else {
			
			String name = Bukkit.getOfflinePlayer(target).getName();
			
			inv = Bukkit.createInventory(null, 1*9, "§e§l"+name+"s Grundstücke");
		}
		
		
		
		int count = 1;
		
		for (Plot plot : PlotManager.getPlayerPlots(target)) {
			ItemStack item = new ItemStack(Material.GRASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6§lGrundstück "+count);
			List<String> lore = new ArrayList<>();
			lore.add("§fID: "+plot.getID());
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			inv.addItem(item);
			
			count++;
		}
		p.closeInventory();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				p.openInventory(inv);
			}
		},1);
		

	}
	
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		try {
			if (e.getClickedInventory().getTitle().equalsIgnoreCase("§e§lMeine Grundstücke") || e.getClickedInventory().getTitle().contains("s Grundstücke")) {
				
				if (e.getInventory().getSize() == 1*9) {
				
				e.setCancelled(true);
				int id = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(0).split("§fID: ")[1]);
				String number = e.getCurrentItem().getItemMeta().getDisplayName().split("§6§lGrundstück ")[1];
				
				Plot plot = PlotManager.getPlot(id);
				
				Player p = (Player) e.getWhoClicked();
				
				if (p.getUniqueId().equals(plot.getOwner())) {
					INV_Plot.callInv(p, id, number);
				}else {
					String name = Bukkit.getOfflinePlayer(plot.getOwner()).getName();
					p.teleport(plot.getHome());
					Utils.sendNoSpamMsg(p, Main.Prefix+"§aDu wurdest zu "+name+"s "+number+". Grundstück teleportiert!");
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);


				}
				
			}
			}
		}catch (NullPointerException ex) {
			
		}
	}

}
