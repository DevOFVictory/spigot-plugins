package de.devofvictory.wargame.utils;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class OtherUtils {
	
	public static HashMap<Player, Integer> getSortedHashMap(HashMap<Player, Integer> map) {
		HashMap<Player, Integer> sorted = new HashMap<>();
		
		sorted = map
		        .entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		        .collect(
		            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                LinkedHashMap::new));
		
		return sorted;
	}
	
	public static void spawnFireworks(Location location, int amount, Color color){
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
       
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(color).flicker(true).build());
       
        fw.setFireworkMeta(fwm);
        fw.detonate();
       
        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }
	
	public static boolean isSolid(Material type) {
		if (!type.isSolid()) {
			return false;
		}else {
			if (getNonSolidMaterials().contains(type)) {
				return false;
			}else {
				return true;
			}
		}
	}
	
	static ArrayList<Material> getNonSolidMaterials() {
		ArrayList<Material> nonSolidMaterials = new ArrayList<Material>();
		nonSolidMaterials.add(Material.LEAVES);
		nonSolidMaterials.add(Material.LEAVES_2);
		nonSolidMaterials.add(Material.GLASS);
		nonSolidMaterials.add(Material.WEB);
		nonSolidMaterials.add(Material.PISTON_BASE);
		nonSolidMaterials.add(Material.PISTON_EXTENSION);
		nonSolidMaterials.add(Material.PISTON_MOVING_PIECE);
		nonSolidMaterials.add(Material.PISTON_STICKY_BASE);
		nonSolidMaterials.add(Material.STONE_SLAB2);
		nonSolidMaterials.add(Material.STEP);
		nonSolidMaterials.add(Material.WOOD_STEP);
		nonSolidMaterials.add(Material.TNT);
		nonSolidMaterials.add(Material.STONE_PLATE);
		nonSolidMaterials.add(Material.WOOD_PLATE);
		nonSolidMaterials.add(Material.GOLD_PLATE);
		nonSolidMaterials.add(Material.IRON_PLATE);
		nonSolidMaterials.add(Material.LADDER);
		nonSolidMaterials.add(Material.CHEST);
		nonSolidMaterials.add(Material.ICE);
		nonSolidMaterials.add(Material.PACKED_ICE);
		nonSolidMaterials.add(Material.CACTUS);
		nonSolidMaterials.add(Material.FENCE);
		nonSolidMaterials.add(Material.FENCE_GATE);
		nonSolidMaterials.add(Material.STAINED_GLASS);
		nonSolidMaterials.add(Material.STAINED_GLASS_PANE);
		nonSolidMaterials.add(Material.ACACIA_FENCE);
		nonSolidMaterials.add(Material.ACACIA_FENCE_GATE);
		nonSolidMaterials.add(Material.BIRCH_FENCE);
		nonSolidMaterials.add(Material.BIRCH_FENCE_GATE);
		nonSolidMaterials.add(Material.IRON_FENCE);
		nonSolidMaterials.add(Material.JUNGLE_FENCE);
		nonSolidMaterials.add(Material.JUNGLE_FENCE_GATE);
		nonSolidMaterials.add(Material.DARK_OAK_FENCE);
		nonSolidMaterials.add(Material.DARK_OAK_FENCE_GATE);
		nonSolidMaterials.add(Material.SPRUCE_FENCE);
		nonSolidMaterials.add(Material.COBBLE_WALL);
		nonSolidMaterials.add(Material.SIGN_POST);
		nonSolidMaterials.add(Material.SIGN);
		nonSolidMaterials.add(Material.WALL_SIGN);
		nonSolidMaterials.add(Material.ACACIA_STAIRS);
		nonSolidMaterials.add(Material.BIRCH_WOOD_STAIRS);
		nonSolidMaterials.add(Material.JUNGLE_WOOD_STAIRS);
		nonSolidMaterials.add(Material.QUARTZ_STAIRS);
		nonSolidMaterials.add(Material.SPRUCE_WOOD_STAIRS);
		nonSolidMaterials.add(Material.SANDSTONE_STAIRS);
		nonSolidMaterials.add(Material.RED_SANDSTONE_STAIRS);
		nonSolidMaterials.add(Material.NETHER_BRICK_STAIRS);
		nonSolidMaterials.add(Material.SMOOTH_STAIRS);
		nonSolidMaterials.add(Material.BRICK_STAIRS);
		nonSolidMaterials.add(Material.COBBLESTONE_STAIRS);
		nonSolidMaterials.add(Material.SEA_LANTERN);
		nonSolidMaterials.add(Material.TRAP_DOOR);
		nonSolidMaterials.add(Material.IRON_TRAPDOOR);
		nonSolidMaterials.add(Material.WATER_LILY);
		nonSolidMaterials.add(Material.ENCHANTMENT_TABLE);
		nonSolidMaterials.add(Material.ENDER_PORTAL_FRAME);
		nonSolidMaterials.add(Material.ENDER_CHEST);
		nonSolidMaterials.add(Material.TRIPWIRE_HOOK);
		nonSolidMaterials.add(Material.STRING);
		nonSolidMaterials.add(Material.ANVIL);
		nonSolidMaterials.add(Material.DAYLIGHT_DETECTOR);
		nonSolidMaterials.add(Material.DAYLIGHT_DETECTOR_INVERTED);
		nonSolidMaterials.add(Material.HOPPER);
		nonSolidMaterials.add(Material.CARPET);
		nonSolidMaterials.add(Material.YELLOW_FLOWER);
		nonSolidMaterials.add(Material.FLOWER_POT);
		nonSolidMaterials.add(Material.WOOD_DOOR);
		nonSolidMaterials.add(Material.ACACIA_DOOR);
		nonSolidMaterials.add(Material.BIRCH_DOOR);
		nonSolidMaterials.add(Material.DARK_OAK_DOOR);
		nonSolidMaterials.add(Material.IRON_DOOR_BLOCK);
		nonSolidMaterials.add(Material.IRON_DOOR);
		nonSolidMaterials.add(Material.JUNGLE_DOOR);
		nonSolidMaterials.add(Material.SPRUCE_DOOR);
		nonSolidMaterials.add(Material.BED);
		nonSolidMaterials.add(Material.REDSTONE_COMPARATOR);
		nonSolidMaterials.add(Material.REDSTONE);
		nonSolidMaterials.add(Material.REDSTONE_WIRE);
		nonSolidMaterials.add(Material.REDSTONE_TORCH_ON);
		nonSolidMaterials.add(Material.SKULL);
		nonSolidMaterials.add(Material.BANNER);
		nonSolidMaterials.add(Material.BREWING_STAND);
		nonSolidMaterials.add(Material.STANDING_BANNER);
		nonSolidMaterials.add(Material.WALL_BANNER);
		nonSolidMaterials.add(Material.WOOD_DOOR);
		
		
		return nonSolidMaterials;
	}
	
	
	
}

	
	


