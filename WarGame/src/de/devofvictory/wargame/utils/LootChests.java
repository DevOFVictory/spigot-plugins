package de.devofvictory.wargame.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.devofvictory.wargame.main.Main;



@SuppressWarnings("deprecation")
public class LootChests implements Listener {
	
	
	static ArrayList<Entity> chickens = new ArrayList<>();
	static HashMap<Entity, Integer> run = new HashMap<>();
	
	public static void spawnLootChest(Location location, int radius, int amount) {
		
		for (int i = 0; i < amount; i++) {
			
		Location loc = new Location(location.getWorld(), ThreadLocalRandom.current().nextInt(location.getBlockX()-radius, location.getBlockX()+radius), 100, ThreadLocalRandom.current().nextInt(location.getBlockZ()-radius, location.getBlockZ()+radius));
		
		FallingSand pass = (FallingSand) loc.getWorld().spawnFallingBlock(loc, Material.EMERALD_BLOCK, (byte)0);
		pass.setDropItem(false);
		pass.setCustomName("lootdrop");
		LivingEntity c = loc.getWorld().spawnCreature(loc, CreatureType.CHICKEN);
		

		PotionEffect ef = new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 255, false, false);
		c.addPotionEffect(ef);
		c.setPassenger(pass);
		chickens.add(c);
		((CraftEntity)c).getHandle().b(true);
		
		run.put(c, Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (c.getLocation().subtract(new Vector(0, 3, 0)).getBlock().getType() != Material.AIR ||
					c.getLocation().subtract(new Vector(0, 2, 0)).getBlock().getType() != Material.AIR ||
					c.getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() != Material.AIR ||
					c.isOnGround()) {
					
					c.remove();
					chickens.remove(c);
					Bukkit.getScheduler().cancelTask(run.get(c));
				}

			}
		}, 10*20, 20));
		
		
	}
		
	}
	
	@EventHandler
	public void onChangeToBlock(EntityChangeBlockEvent e) {
		if (e.getEntityType() == EntityType.FALLING_BLOCK) {
			FallingBlock fall = (FallingBlock)e.getEntity();
			if (fall.getMaterial() == Material.EMERALD_BLOCK) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						fall.getLocation().getBlock().setType(fall.getMaterial());
						
					}
				}, 5);
			
			}
		}
}
}

