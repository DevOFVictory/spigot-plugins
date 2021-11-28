package de.devofvictory.wargame.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;

public class MultiTool implements Listener{
	
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if (p.isSneaking()) {
		try {
			
			if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMultiTool")) {
				ItemStack shovel = new ItemStack(Material.DIAMOND_SPADE);
				ItemMeta shovelMeta = shovel.getItemMeta();
				shovelMeta.setDisplayName("§6§lMultiTool");
				shovelMeta.spigot().setUnbreakable(true);
				shovel.setItemMeta(shovelMeta);
				
				ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
				ItemMeta pickaxeMeta = shovel.getItemMeta();
				pickaxeMeta.setDisplayName("§6§lMultiTool");
				pickaxeMeta.spigot().setUnbreakable(true);
				pickaxe.setItemMeta(pickaxeMeta);
				
				ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
				ItemMeta axeMeta = shovel.getItemMeta();
				axeMeta.setDisplayName("§6§lMultiTool");
				axeMeta.spigot().setUnbreakable(true);
				axe.setItemMeta(axeMeta);
				
				ItemStack bone = new ItemStack(Material.BONE);
				ItemMeta boneMeta = shovel.getItemMeta();
				boneMeta.setDisplayName("§6§lMultiTool");
				boneMeta.spigot().setUnbreakable(true);
				bone.setItemMeta(boneMeta);
				
				if (p.getItemInHand().getType() == Material.DIAMOND_SPADE) {
					p.setItemInHand(pickaxe);
					p.playSound(p.getLocation(), Sound.BURP, 1, 1);
					
				}else if(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
					p.setItemInHand(axe);
					p.playSound(p.getLocation(), Sound.BURP, 1, 1);
					
				}else if(p.getItemInHand().getType() == Material.DIAMOND_AXE) {
					p.setItemInHand(bone);
					p.playSound(p.getLocation(), Sound.BURP, 1, 1);
					
				}else if(p.getItemInHand().getType() == Material.BONE) {
					p.setItemInHand(shovel);
					p.playSound(p.getLocation(), Sound.BURP, 1, 1);
				}
				
				
				
			}
			
		}catch(NullPointerException ex) {
			
		}
		
		}	
		}
					
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (Main.isMatchRunning) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				try {
					String name = p.getItemInHand().getItemMeta().getDisplayName();
					if (name.equalsIgnoreCase("§6§lMultiTool")) {
						e.setCancelled(true);
						p.sendMessage(Main.Prefix+"§cDu kannst mit dem MultiTool keinen Schaden verteilen!");
					}
				}catch (NullPointerException ex) {
				
				}
					
			}
		}
	}
	
	

}
