package de.devofvictory.survivalproject.custom;

import java.util.ArrayList;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.devofvictory.survivalproject.main.Main;

public class EpicPortal implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.NETHER_PORTAL) {
			Player p = e.getPlayer();
			if (p.getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.GLOWSTONE) {
				e.setCancelled(true);
				if (!p.getLocation().getWorld().getName().equalsIgnoreCase("epic")) {
					p.teleport(new Location(Bukkit.getWorld("epic"), -2358.5, 64, -778, -90, 0));
				} else {
					try {
						p.teleport(p.getBedSpawnLocation());
					} catch (IllegalArgumentException ex) {
						p.teleport(Bukkit.getWorld("world").getSpawnLocation());
					}

				}

			}
		}
	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent e) {
		if (e.getBlock().getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.GLOWSTONE) {
			create(e.getBlock().getLocation());
		}

	}

	public static void create(Location loc) {

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {

				Location glowstoneLoc = loc.clone().subtract(0, 1, 0);

				if (glowstoneLoc.clone().add(1, 0, 0).getBlock().getType() == Material.GLOWSTONE
						|| glowstoneLoc.clone().subtract(1, 0, 0).getBlock().getType() == Material.GLOWSTONE) {

					ArrayList<Block> blocks = new ArrayList<Block>();

					blocks.add(loc.getBlock());
					blocks.add(loc.clone().add(0, 1, 0).getBlock());
					blocks.add(loc.clone().add(0, 2, 0).getBlock());

					if (loc.clone().add(1, 0, 0).getBlock().getType() == Material.GLOWSTONE) {

						blocks.add(loc.clone().subtract(1, 0, 0).getBlock());
						blocks.add(loc.clone().subtract(1, 0, 0).clone().add(0, 1, 0).getBlock());
						blocks.add(loc.clone().subtract(1, 0, 0).clone().add(0, 2, 0).getBlock());

						ArrayList<Location> rahmenLocs = new ArrayList<Location>();

						Location loc1 = loc.clone().add(0, -1, 0);
						Location loc2 = loc.clone().add(1, 0, 0);
						Location loc3 = loc.clone().add(1, 1, 0);
						Location loc4 = loc.clone().add(1, 2, 0);
						Location loc5 = loc.clone().add(0, 3, 0);
						Location loc6 = loc.clone().add(-1, -1, 0);
						Location loc7 = loc.clone().add(-2, 0, 0);
						Location loc8 = loc.clone().add(-2, 1, 0);
						Location loc9 = loc.clone().add(-2, 2, 0);
						Location loc10 = loc.clone().add(-1, 3, 0);

						rahmenLocs.add(loc1);
						rahmenLocs.add(loc2);
						rahmenLocs.add(loc3);
						rahmenLocs.add(loc4);
						rahmenLocs.add(loc5);
						rahmenLocs.add(loc6);
						rahmenLocs.add(loc7);
						rahmenLocs.add(loc8);
						rahmenLocs.add(loc9);
						rahmenLocs.add(loc10);

						if (loc1.getBlock().getType() == Material.GLOWSTONE
								&& loc2.getBlock().getType() == Material.GLOWSTONE
								&& loc3.getBlock().getType() == Material.GLOWSTONE
								&& loc4.getBlock().getType() == Material.GLOWSTONE
								&& loc5.getBlock().getType() == Material.GLOWSTONE
								&& loc6.getBlock().getType() == Material.GLOWSTONE
								&& loc7.getBlock().getType() == Material.GLOWSTONE
								&& loc8.getBlock().getType() == Material.GLOWSTONE
								&& loc9.getBlock().getType() == Material.GLOWSTONE
								&& loc10.getBlock().getType() == Material.GLOWSTONE) {

						} else {

//		    				for (Location i : rahmenLocs) {
//		    					i.getBlock().setType(Material.DIAMOND_BLOCK);
//		    				}

							return;
						}

					} else if (loc.clone().subtract(1, 0, 0).getBlock().getType() == Material.GLOWSTONE) {
						blocks.add(loc.clone().add(1, 0, 0).getBlock());
						blocks.add(loc.clone().add(1, 0, 0).clone().add(0, 1, 0).getBlock());
						blocks.add(loc.clone().add(1, 0, 0).clone().add(0, 2, 0).getBlock());

						ArrayList<Location> rahmenLocs = new ArrayList<Location>();

						Location loc1 = loc.clone().add(1, -1, 0);
						Location loc2 = loc.clone().add(2, 0, 0);
						Location loc3 = loc.clone().add(2, 1, 0);
						Location loc4 = loc.clone().add(2, 2, 0);
						Location loc5 = loc.clone().add(1, 3, 0);
						Location loc6 = loc.clone().add(0, -1, 0);
						Location loc7 = loc.clone().add(-1, 0, 0);
						Location loc8 = loc.clone().add(-1, 1, 0);
						Location loc9 = loc.clone().add(-1, 2, 0);
						Location loc10 = loc.clone().add(0, 3, 0);

						rahmenLocs.add(loc1);
						rahmenLocs.add(loc2);
						rahmenLocs.add(loc3);
						rahmenLocs.add(loc4);
						rahmenLocs.add(loc5);
						rahmenLocs.add(loc6);
						rahmenLocs.add(loc7);
						rahmenLocs.add(loc8);
						rahmenLocs.add(loc9);
						rahmenLocs.add(loc10);

						if (loc1.getBlock().getType() == Material.GLOWSTONE
								&& loc2.getBlock().getType() == Material.GLOWSTONE
								&& loc3.getBlock().getType() == Material.GLOWSTONE
								&& loc4.getBlock().getType() == Material.GLOWSTONE
								&& loc5.getBlock().getType() == Material.GLOWSTONE
								&& loc6.getBlock().getType() == Material.GLOWSTONE
								&& loc7.getBlock().getType() == Material.GLOWSTONE
								&& loc8.getBlock().getType() == Material.GLOWSTONE
								&& loc9.getBlock().getType() == Material.GLOWSTONE
								&& loc10.getBlock().getType() == Material.GLOWSTONE) {

						} else {

//		    				for (Location i : rahmenLocs) {
//		    					i.getBlock().setType(Material.DIAMOND_BLOCK);
//		    				}

							return;
						}
					}

					if (blocks.size() == 6) {

						loc.getBlock().setType(Material.NETHER_PORTAL);

						for (Block block : blocks) {
							block.setType(Material.NETHER_PORTAL, false);
						}
					}

				} else if (glowstoneLoc.clone().add(0, 0, 1).getBlock().getType() == Material.GLOWSTONE
						|| glowstoneLoc.clone().subtract(0, 0, 1).getBlock().getType() == Material.GLOWSTONE) {

					ArrayList<Block> blocks = new ArrayList<Block>();

					blocks.add(loc.getBlock());
					blocks.add(loc.clone().add(0, 1, 0).getBlock());
					blocks.add(loc.clone().add(0, 2, 0).getBlock());

					if (loc.clone().add(0, 0, 1).getBlock().getType() == Material.GLOWSTONE) {
						blocks.add(loc.clone().subtract(0, 0, 1).getBlock());
						blocks.add(loc.clone().subtract(0, 0, 1).clone().add(0, 1, 0).getBlock());
						blocks.add(loc.clone().subtract(0, 0, 1).clone().add(0, 2, 0).getBlock());

						ArrayList<Location> rahmenLocs = new ArrayList<Location>();

						Location loc1 = loc.clone().add(0, -1, 0);
						Location loc2 = loc.clone().add(0, 0, 1);
						Location loc3 = loc.clone().add(0, 1, 1);
						Location loc4 = loc.clone().add(0, 2, 1);
						Location loc5 = loc.clone().add(0, 3, 0);
						Location loc6 = loc.clone().add(0, -1, -1);
						Location loc7 = loc.clone().add(0, 0, -2);
						Location loc8 = loc.clone().add(0, 1, -2);
						Location loc9 = loc.clone().add(0, 2, -2);
						Location loc10 = loc.clone().add(0, 3, -1);

						rahmenLocs.add(loc1);
						rahmenLocs.add(loc2);
						rahmenLocs.add(loc3);
						rahmenLocs.add(loc4);
						rahmenLocs.add(loc5);
						rahmenLocs.add(loc6);
						rahmenLocs.add(loc7);
						rahmenLocs.add(loc8);
						rahmenLocs.add(loc9);
						rahmenLocs.add(loc10);

						if (loc1.getBlock().getType() == Material.GLOWSTONE
								&& loc2.getBlock().getType() == Material.GLOWSTONE
								&& loc3.getBlock().getType() == Material.GLOWSTONE
								&& loc4.getBlock().getType() == Material.GLOWSTONE
								&& loc5.getBlock().getType() == Material.GLOWSTONE
								&& loc6.getBlock().getType() == Material.GLOWSTONE
								&& loc7.getBlock().getType() == Material.GLOWSTONE
								&& loc8.getBlock().getType() == Material.GLOWSTONE
								&& loc9.getBlock().getType() == Material.GLOWSTONE
								&& loc10.getBlock().getType() == Material.GLOWSTONE) {

						} else {
							return;
						}

					} else if (loc.clone().subtract(0, 0, 1).getBlock().getType() == Material.GLOWSTONE) {
						blocks.add(loc.clone().add(0, 0, 1).getBlock());
						blocks.add(loc.clone().add(0, 0, 1).clone().add(0, 1, 0).getBlock());
						blocks.add(loc.clone().add(0, 0, 1).clone().add(0, 2, 0).getBlock());

						ArrayList<Location> rahmenLocs = new ArrayList<Location>();

						Location loc1 = loc.clone().add(0, -1, 1);
						Location loc2 = loc.clone().add(0, 0, 2);
						Location loc3 = loc.clone().add(0, 1, 2);
						Location loc4 = loc.clone().add(0, 2, 2);
						Location loc5 = loc.clone().add(0, 3, 1);
						Location loc6 = loc.clone().add(0, -1, 0);
						Location loc7 = loc.clone().add(0, 0, -1);
						Location loc8 = loc.clone().add(0, 1, -1);
						Location loc9 = loc.clone().add(0, 2, -1);
						Location loc10 = loc.clone().add(0, 3, 0);

						rahmenLocs.add(loc1);
						rahmenLocs.add(loc2);
						rahmenLocs.add(loc3);
						rahmenLocs.add(loc4);
						rahmenLocs.add(loc5);
						rahmenLocs.add(loc6);
						rahmenLocs.add(loc7);
						rahmenLocs.add(loc8);
						rahmenLocs.add(loc9);
						rahmenLocs.add(loc10);

						if (loc1.getBlock().getType() == Material.GLOWSTONE
								&& loc2.getBlock().getType() == Material.GLOWSTONE
								&& loc3.getBlock().getType() == Material.GLOWSTONE
								&& loc4.getBlock().getType() == Material.GLOWSTONE
								&& loc5.getBlock().getType() == Material.GLOWSTONE
								&& loc6.getBlock().getType() == Material.GLOWSTONE
								&& loc7.getBlock().getType() == Material.GLOWSTONE
								&& loc8.getBlock().getType() == Material.GLOWSTONE
								&& loc9.getBlock().getType() == Material.GLOWSTONE
								&& loc10.getBlock().getType() == Material.GLOWSTONE) {

						} else {
							return;
						}

					}

					if (blocks.size() == 6) {
						for (Block block : blocks) {
							setBlock(block, Material.NETHER_PORTAL, BlockFace.SOUTH);
						}
					} else {
					}

				}

			}
		}, 1);

	}

	public static void setBlock(Block block, Material material, BlockFace blockFace) {
		block.setType(material);
		BlockData blockData = block.getBlockData();
		if (blockData instanceof Directional) {
			((Directional) blockData).setFacing(blockFace);
			block.setBlockData(blockData);
		}
		if (blockData instanceof Orientable) {
			((Orientable) blockData).setAxis(convertBlockFaceToAxis(blockFace));
			block.setBlockData(blockData);
		}
		if (blockData instanceof Rotatable) {
			((Rotatable) blockData).setRotation(blockFace);
			block.setBlockData(blockData);
		}
	}

	private static Axis convertBlockFaceToAxis(BlockFace face) {
		switch (face) {
		case NORTH:
		case SOUTH:
			return Axis.Z;
		case EAST:
		case WEST:
			return Axis.X;
		case UP:
		case DOWN:
			return Axis.Y;
		default:
			return Axis.X;
		}
	}

}
