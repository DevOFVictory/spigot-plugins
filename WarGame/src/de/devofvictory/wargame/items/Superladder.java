package de.devofvictory.wargame.items;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Directional;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ClearUtil;
import de.devofvictory.wargame.utils.OtherUtils;

public class Superladder implements Listener{

	private static String dispName = "§e§lSuperLeiter";
	
	private static HashMap<Integer, Integer> currentHight = new HashMap<Integer, Integer>();

	public static String getDispName() {
		return dispName;
	}
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		try {

			if (p.getItemInHand().getType() == Material.LADDER
					&& p.getItemInHand().getItemMeta().getDisplayName().equals(dispName)) {
				


				int count = p.getItemInHand().getAmount();

				if (count > 1) {
					
					if (p.isSneaking()) {
						
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
								if (ClearUtil.hasEnough(p, Material.LADDER, dispName, 1)) {

									Directional dir = new DirectionalContainer(e.getBlock().getType(), e.getBlock().getData());
									BlockFace face = dir.getFacing().getOppositeFace();
									
									Block placedOnBlock = e.getBlock().getRelative(face);
									Block placedLadder = e.getBlock();
									
									if (!currentHight.containsKey(this.getTaskId()))
										currentHight.put(this.getTaskId(), placedLadder.getLocation().getBlockY()-1);
									
									Location blockLoc = placedOnBlock.getLocation();
									Location ladderLoc = placedLadder.getLocation();
									
									blockLoc.setY(currentHight.get(this.getTaskId()));
									ladderLoc.setY(currentHight.get(this.getTaskId()));
									
									
									
									if (OtherUtils.isSolid(blockLoc.getBlock().getType()) && ladderLoc.getBlock().getType() == Material.AIR) {

										ladderLoc.getBlock().setType(Material.LADDER);
										ladderLoc.getBlock().setData(e.getBlock().getData());
										
										p.playSound(p.getLocation(), Sound.STEP_LADDER, 1, 1);
										
										ClearUtil.removeInventoryItems(p, Material.LADDER, dispName, 1);
										
										currentHight.put(this.getTaskId(), currentHight.get(this.getTaskId())-1);
										
									}else {
										currentHight.remove(this.getTaskId());
										this.cancel();
									}
									
								}else {
									currentHight.remove(this.getTaskId());
									this.cancel();
								}
							}
						}.runTaskTimer(Main.getInstance(), 10, 10);
						
					} else {
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
								if (ClearUtil.hasEnough(p, Material.LADDER, dispName, 1)) {

									Directional dir = new DirectionalContainer(e.getBlock().getType(), e.getBlock().getData());
									BlockFace face = dir.getFacing().getOppositeFace();
									
									Block placedOnBlock = e.getBlock().getRelative(face);
									Block placedLadder = e.getBlock();
									
									if (!currentHight.containsKey(this.getTaskId()))
										currentHight.put(this.getTaskId(), placedLadder.getLocation().getBlockY()+1);
									
									Location blockLoc = placedOnBlock.getLocation();
									Location ladderLoc = placedLadder.getLocation();
									
									blockLoc.setY(currentHight.get(this.getTaskId()));
									ladderLoc.setY(currentHight.get(this.getTaskId()));
									
									
									if (OtherUtils.isSolid(blockLoc.getBlock().getType()) && ladderLoc.getBlock().getType() == Material.AIR) {
										

										ladderLoc.getBlock().setType(Material.LADDER);
										ladderLoc.getBlock().setData(e.getBlock().getData());
										
										p.playSound(p.getLocation(), Sound.STEP_LADDER, 1, 1);
										
										ClearUtil.removeInventoryItems(p, Material.LADDER, dispName, 1);
										
										currentHight.put(this.getTaskId(), currentHight.get(this.getTaskId())+1);
										
									}else {
										currentHight.remove(this.getTaskId());
										this.cancel();
									}
									
								}else {
									currentHight.remove(this.getTaskId());
									this.cancel();
								}
							}
						}.runTaskTimer(Main.getInstance(), 10, 10);
					}
				}
			}

		} catch (NullPointerException ex) {
			// TODO: handle exception
		}

	}

}
