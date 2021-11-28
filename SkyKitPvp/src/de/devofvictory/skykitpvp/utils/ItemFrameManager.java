package de.devofvictory.skykitpvp.utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFrameManager {
	
	private static Random random = new Random();
	
	public static ItemFrame getFreeItemFrame(ItemFrame lastFrame) {
		
		ItemFrame frame =Variables.itemFrames.get(random.nextInt(Variables.itemFrames.size()));
		
		while (frame.getItem().getType() != Material.AIR && !frame.getLocation().equals(lastFrame.getLocation())) {
			frame = Variables.itemFrames.get(random.nextInt(Variables.itemFrames.size()));
		}
		return frame;
	}
	
	public static ItemFrame getFreeItemFrame() {
		
		ItemFrame frame =Variables.itemFrames.get(random.nextInt(Variables.itemFrames.size()));
		
		while (frame.getItem().getType() != Material.AIR) {
			frame = Variables.itemFrames.get(random.nextInt(Variables.itemFrames.size()));
		}
		return frame;
	}
	
	public static void registerItemFrames() {
		Variables.itemFrames.clear();
		for (Entity entity : Variables.getSpawnLocation().getWorld().getEntities()) {
			if (entity.getType() == EntityType.ITEM_FRAME) {
				ItemFrame frame = (ItemFrame) entity;
				if (frame.getItem() != null && frame.getItem().getType() != Material.FILLED_MAP) {
					Variables.itemFrames.add(frame);
				}
			}
		}

	}
	
	
	public static void setGold(ItemFrame frame) {
		ItemStack is = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = is.getItemMeta();
		
		meta.setDisplayName("§6§lKick mich!");
		is.setItemMeta(meta);
		frame.setItem(is);
	}
	
	public static void spawnFirstGold() {
		clearGolds();
		for (int i = 0; i < Variables.itemFrameAmount; i++) {
			setGold(getFreeItemFrame());
		}
	}
	
	public static void clearGolds() {
		for (ItemFrame frame : Variables.itemFrames) {
			frame.setItem(new ItemStack(Material.AIR));
		}
	}

}
