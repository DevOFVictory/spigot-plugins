package de.devofvictory.wargame.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Locations;

public class SchildTrank implements Listener{
	
	@EventHandler
	public void onRegain(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if (e.getRegainReason() == RegainReason.SATIATED) {
					if (p.getHealth() >= 20) {
						e.setCancelled(true);
						
			}
		}else {
			if (!Main.isGameRunning) {
				e.setCancelled(true);
			}
		}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e) {
		try {
		if (e.getItem().getType() == Material.POTION && e.getItem().getData().getData() == 0 && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchildTrank")) {
			Double heart = e.getPlayer().getHealth()+10;
			e.getPlayer().sendMessage(Main.Prefix+"§eProst!");
			try {
				e.getPlayer().setHealth(heart);
			}catch (Exception ex) {
				e.getPlayer().setHealth(40);
			}
			
		}
		}catch (NullPointerException ex) {
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCauldronRefill(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				try {
					if (e.getItem().getType() == Material.GLASS_BOTTLE && e.getItem().getItemMeta().getDisplayName() == null && e.getClickedBlock().getType() == Material.CAULDRON) {
						Bukkit.getWorld("map").spawnFallingBlock(Locations.getRandomLocation(Locations.middle, 113), Material.CAULDRON, (byte)1);
						
						ItemStack SchildTrank = new ItemStack(Material.POTION,1,(byte)0);
						ItemMeta SchildTrankMeta = SchildTrank.getItemMeta();
						SchildTrankMeta.setDisplayName("§6§lSchildTrank");
						SchildTrank.setItemMeta(SchildTrankMeta);
						
						if (hasSpace(p.getInventory())) {
							
							if (e.getItem().getAmount() > 1) {
								e.getItem().setAmount(e.getItem().getAmount()-1);
							}else {
								p.getInventory().remove(e.getItem());
							}
							
							p.getInventory().addItem(SchildTrank);
							
						}else {
							if (e.getItem().getAmount() > 1) {
								e.getItem().setAmount(e.getItem().getAmount()-1);
							}else {
								p.getInventory().remove(e.getItem());
							}
							
							p.getWorld().dropItemNaturally(p.getLocation(), SchildTrank);
						}
						
						
						
						p.sendMessage(Main.Prefix+"§aSchildtrank erfolgreich nachgefüllt!");
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								e.getClickedBlock().setType(Material.AIR);
							}
						},2);
					}
				}catch (NullPointerException ex) {
				}
			}
	}
	
	boolean hasSpace(Inventory inv) {
		for(ItemStack item : inv.getContents())
		{
		    if(item == null)
		      return true;
		}
		return false;
	}

}
