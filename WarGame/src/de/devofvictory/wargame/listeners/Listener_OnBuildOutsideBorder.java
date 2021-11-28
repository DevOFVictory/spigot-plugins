package de.devofvictory.wargame.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;


public class Listener_OnBuildOutsideBorder implements Listener{
	
	public static HashMap<Player, Integer> placedCounter = new HashMap<>();
	public static HashMap<Player, Long> lastBreaked = new HashMap<>(); 
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			
			
			
			try {
			
			if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMultiTool") && e.getItem().getType() == Material.BONE) {
				
				if (isOutsideOfBorder(e.getClickedBlock().getLocation())) {
					
					
					if (isBetween(System.currentTimeMillis()-lastBreaked.get(e.getPlayer()), 198, 201)) {
						placedCounter.put(e.getPlayer(), placedCounter.get(e.getPlayer())+1);
						
						PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY(), e.getClickedBlock().getLocation().getZ()), Math.round(placedCounter.get(e.getPlayer())/2));
						int dimension = ((CraftWorld) e.getPlayer().getWorld()).getHandle().dimension;
						((CraftServer) e.getPlayer().getServer()).getHandle().sendPacketNearby(e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY()-1, e.getClickedBlock().getLocation().getZ(), 120, dimension, packet);
						
					}else {
						placedCounter.put(e.getPlayer(), 1);
						
						PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY(), e.getClickedBlock().getLocation().getZ()), 1);
						int dimension = ((CraftWorld) e.getPlayer().getWorld()).getHandle().dimension;
						((CraftServer) e.getPlayer().getServer()).getHandle().sendPacketNearby(e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY()-1, e.getClickedBlock().getLocation().getZ(), 120, dimension, packet);
					}
					
					
					if (e.getClickedBlock().getType() == Material.STONE || e.getClickedBlock().getType() == Material.BRICK || e.getClickedBlock().getType() == Material.SANDSTONE || e.getClickedBlock().getType() == Material.CLAY || e.getClickedBlock().getType() == Material.BRICK || e.getClickedBlock().getType() == Material.QUARTZ) {
						
						if (placedCounter.get(e.getPlayer()) == 5) {
							e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType().getId());
							e.getClickedBlock().breakNaturally();
							placedCounter.put(e.getPlayer(), 0);
						}
						
					}else if (e.getClickedBlock().getType() == Material.OBSIDIAN) {
						if (placedCounter.get(e.getPlayer()) == 20) {
							e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType().getId());
							e.getClickedBlock().breakNaturally();
							placedCounter.put(e.getPlayer(), 0);
						}
					}else if (e.getClickedBlock().getType() == Material.DIRT || e.getClickedBlock().getType() == Material.WOOL || e.getClickedBlock().getType() == Material.GRASS) {
						if (placedCounter.get(e.getPlayer()) == 3) {
							e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType().getId());
							e.getClickedBlock().breakNaturally();
							placedCounter.put(e.getPlayer(), 0);
						}
					}else {
						if (placedCounter.get(e.getPlayer()) == 10) {
							e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType().getId());
							e.getClickedBlock().breakNaturally();
							placedCounter.put(e.getPlayer(), 0);
						}
					}
					
					lastBreaked.put(e.getPlayer(), System.currentTimeMillis());
					
					
					e.setCancelled(true);
					return;
				}else {
					e.getPlayer().sendMessage(Main.Prefix+"§cDieser Block ist nicht außerhalb der WorldBorder!");
				}
				
				
			}
			
			}catch (NullPointerException ex) {
				// TODO: handle exception
			}
			
		if (isOutsideOfBorder(e.getClickedBlock().getLocation())) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				
				
				if (all.getLocation().getBlock().getLocation().equals(e.getClickedBlock().getRelative(e.getBlockFace()).getLocation())) {
					return;
					
				}
			}
		
			if (e.getItem().getType().isBlock()) {
				e.getClickedBlock().getRelative(e.getBlockFace()).setType(e.getItem().getType());
				
				playPlaceSound(e.getClickedBlock().getRelative(e.getBlockFace()));
				
			}
		}
		
	}
		
		
	}
	
	
	boolean isBetween(long a, long b, long c) {
		return a >= b ? a <= c : false;
		}
	
	static void playPlaceSound(Block b) {
		
		try {
		
		for(Sound sound : Sound.values()) {
            Field f = CraftSound.class.getDeclaredField("sounds");
            f.setAccessible(true);
            
            String[] sounds = (String[]) f.get(null);
            Method getBlock = CraftBlock.class.getDeclaredMethod("getNMSBlock");
            getBlock.setAccessible(true);
            Object nmsBlock = getBlock.invoke(b);
            net.minecraft.server.v1_8_R3.Block block = (net.minecraft.server.v1_8_R3.Block) nmsBlock;
            
 
            if(block.stepSound.getPlaceSound()
                    .equals(sounds[sound.ordinal()])) {
                b.getWorld().playSound(b.getLocation(), sound,10, (float) 1);
                
            }
        }
		
		}catch (Exception ex) {
			// TODO: handle exception
		}
    
	}
	
	@SuppressWarnings("deprecation")
	static void breakBlock(Block b) {
		
		try {
			b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());
        for(Sound sound : Sound.values()) {
            Field f = CraftSound.class.getDeclaredField("sounds");
            f.setAccessible(true);
            
            String[] sounds = (String[]) f.get(null);
            Method getBlock = CraftBlock.class.getDeclaredMethod("getNMSBlock");
            getBlock.setAccessible(true);
            Object nmsBlock = getBlock.invoke(b);
            net.minecraft.server.v1_8_R3.Block block = (net.minecraft.server.v1_8_R3.Block) nmsBlock;
            
			b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());

            
            if(block.stepSound.getBreakSound()
                    .equals(sounds[sound.ordinal()])) {
                b.getWorld().playSound(b.getLocation(), sound,10, 1);
            }
        }
        b.breakNaturally();
        
        
		}catch (Exception ex) {
			// TODO: handle exception
		}
    }
	
	public static boolean isOutsideOfBorder(Location loc) {
        WorldBorder border = loc.getWorld().getWorldBorder();
        double size = (border.getSize()-2)/2;
        Location center = border.getCenter();
        double x = loc.getX() - center.getX(), z = loc.getZ() - center.getZ();
        return ((x > size || (-x) > size) || (z > size || (-z) > size));
    }
	
	

}
