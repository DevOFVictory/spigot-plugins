package de.devofvictory.survivalproject.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class Listener_OnBlockBreak implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SPAWNER) {
			try {
				
				Player p = e.getPlayer();
				if (p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE && p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
					
					EntityType type = ((CreatureSpawner)e.getBlock().getState()).getSpawnedType();
					
					ItemStack spawner = new ItemStack(Material.SPAWNER);
					BlockStateMeta bsm = (BlockStateMeta)spawner.getItemMeta();
					BlockState state = bsm.getBlockState();
					CreatureSpawner myspawner = (CreatureSpawner)state;
					myspawner.setSpawnedType(type);
					bsm.setBlockState(myspawner);
					spawner.setItemMeta(bsm);
					
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), spawner);
					
				}
				
			}catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}

}
