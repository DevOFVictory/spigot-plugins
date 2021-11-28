package de.devofvictory.skykitpvp.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class OtherUtils {
		
	
	public static Player getNearestPlayer(Entity entity, int maxDistance, Player blackListed) {
		Player nearest = null;
		for (Entity en : entity.getNearbyEntities(maxDistance, maxDistance, maxDistance)) {
			if (en instanceof Player) {
				Player p = (Player) en;
				
				if (p != blackListed) {
					if (nearest == null) {
						nearest = p;
					}else {
						if (entity.getLocation().distance(p.getLocation()) < entity.getLocation().distance(nearest.getLocation())) {
							nearest = p;
						}
					}
				}
			}
		}
		
		return nearest;
	}
	
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public static ItemStack getSkullFromUrl(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if(url.isEmpty())return head;
       
       
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
	
	public static ItemFrame getNearestFrame(Entity entity, int maxDistance) {
		
		
		ItemFrame itemframe = null;
		for (Entity en : entity.getNearbyEntities(maxDistance, maxDistance, maxDistance)) {
			if (en instanceof ItemFrame) {
				ItemFrame nearFrame = (ItemFrame) en;
				
				if (nearFrame.getItem().getType() == Material.GOLD_INGOT) {
					
					if (itemframe == null) {
						itemframe = nearFrame;
					}else {
						if (entity.getLocation().distance(itemframe.getLocation()) > entity.getLocation().distance(nearFrame.getLocation())) {
							itemframe = nearFrame;
						}
					}
				}
			}
		}
		
		return itemframe;
	}
	

	
	public static String[] getStringArray(String string) {
        String str = string.replace("[", "");
        str = str.replace("]", "");
        
        
        String[] array = str.split(", ");
        
        return array;
	}
	
	public static boolean isSpawn(Location location) {
		
		World world = Variables.getSpawnLocation().getWorld();
		
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		
		RegionManager regions = container.get(BukkitAdapter.adapt(world));
		
		if (regions != null) {
			ProtectedRegion region = regions.getRegion(Variables.worldguardSpawnRegionName);
			
			BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
			
			return region.contains(vector);
		}else {
			return false;
		}
		
		
	}
	
	
	public static Entity getTargetEntity(Player p) {
		List<Block> line = p.getLineOfSight((Set<Material>)null, 4);
		
		for (Entity entity : p.getWorld().getEntities()) {
			if (line.contains(entity.getLocation().getBlock()) || line.contains(entity.getLocation().getBlock().getLocation().clone().add(0,1,0).getBlock())) {
				if (entity != p) {
					return entity;
				}
			}
		}
		return null;
	}
	

	
	 public static List<Location> blocksFromTwoPoints(Location loc1, Location loc2)
	    {
	        List<Location> locations = new ArrayList<Location>();
	 
	        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
	        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
	 
	        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
	        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
	 
	        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
	        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
	 
	        for(int x = bottomBlockX; x <= topBlockX; x++)
	        {
	            for(int z = bottomBlockZ; z <= topBlockZ; z++)
	            {
	                for(int y = bottomBlockY; y <= topBlockY; y++)
	                {
	                    Block block = loc1.getWorld().getBlockAt(x, y, z);
	                   
	                    locations.add(block.getLocation());
	                }
	            }
	        }
	       
	        return locations;
	    }
	 
		public static void removeInventoryItems(Player p, Material type, String name, int amount) {
		    for (ItemStack is : p.getInventory().getContents()) {
		        if (is != null && is.getType() == type) {
		            int newamount = is.getAmount() - amount;
		            if (newamount > 0) {
		                is.setAmount(newamount);
		                break;
		            } else {
		                p.getInventory().remove(is);
		                amount = -newamount;
		                if (amount == 0) break;
		            }
		        }
		    }
		}
		
	 
	 public static Player getPlayer(UUID uuid) {
		 for (Player all : Bukkit.getOnlinePlayers()) {
			 if (all.getUniqueId().equals(uuid)) {
				 return all;
			 }
		 }
		 return null;
	 }
	 
	 public static ArrayList<Player> getNearbyPlayers(Entity ent, int range) {
		 ArrayList<Player> inRange = new ArrayList<Player>();
		 for (Entity en : ent.getNearbyEntities(range, range, range)) {
			if (en instanceof Player) {
				Player t = (Player) en;
				if (t != ent) {
					inRange.add(t);
				}
			}
		 }
		 return inRange;
	 }

}
