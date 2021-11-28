package de.devofvictory.biomicploz.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import de.chilipro.eco.utils.MoneyManager;
import de.devofvictory.biomicploz.listeners.Listener_OnMark;
import de.devofvictory.biomicploz.main.Main;

public class PlotManager {
	
	public static ArrayList<Plot> allPlots = new ArrayList<>();
	public static HashMap<Player, ArrayList<Laser>> inspect = new HashMap<>();
	
	
	public static void claimPlot(Plot plot, Player owner) {
		plot.setOwner(owner.getUniqueId());
		plot.setTrusted(new ArrayList<UUID>());
		plot.setFlags(new HashMap<String, String>());
		plot.setHome(owner.getLocation());
		plot.setFlagValue("use", "false");
		allPlots.add(plot);
	}
	
	
	
	public static boolean isBetween(Location loc, Point2D corner1, Point2D corner2) {
		int x1 = Math.min(corner1.getX(), corner2.getX());
        int z1 = Math.min(corner1.getY(), corner2.getY());
        int x2 = Math.max(corner1.getX(), corner2.getX());
        int z2 = Math.max(corner1.getY(), corner2.getY());
 
        return loc.getBlockX() >= x1 && loc.getBlockX() <= x2 && loc.getBlockZ() >= z1 && loc.getBlockZ() <= z2;
    }
	
	public static int getHighestPlotId() {
		
		int higest = 0;
		
		for (Plot plot : allPlots) {
			if (plot.getID() > higest) {
				higest = plot.getID();
			}
		}
		
		return higest;
	}
	
	public static Plot getPlot(Location loc) {
				
		for (Plot plot : allPlots) {
			if (isBetween(loc, plot.getCorner1(), plot.getCorner2())) {
				return plot;
			}
		}
		return null;
	}
	
	
	
	public static String getBoolColorCode(int id, String key) {		
		if (getPlot(id) != null) {

			Plot plot = getPlot(id);
			
			if (plot.getFlagValue(key) != null) {
				if (plot.getFlagValue(key).contains("true")) {
					return "§a";
				}else if (plot.getFlagValue(key).contains("false")) {
					return "§c";
				}
				
			}else {
				return "§f";
			}
			
		}
			return "§f";
		
	}
	
	public static Plot getPlot(int id) {
		for (Plot plot : allPlots) {
			if (plot.getID() == id) {
				return plot;
			}
		}
		return null;
	}
	
	// Get all Plot-Objects from a specific player
	
	public static ArrayList<Plot> getPlayerPlots(UUID uuid) {
		ArrayList<Plot> plots = new ArrayList<>();
		
		for (Plot plot : allPlots) {
			if (plot.getOwner().equals(uuid)) {
				plots.add(plot);
			}
		}
		return plots;
	}
	
	// check if the player is allowed to build on the given location
	
	public static boolean isEntitledToBuild(UUID uuid, Location loc) {
		
		if (!loc.getWorld().getName().equals("plotworld"))
			return true;
				
		if (getPlot(loc) != null) {
		if (getPlot(loc).getOwner().equals(uuid) || getPlot(loc).getTrusted().contains(uuid)) {

			return true;
		}else {

			return false;
			
		}
		}else {

			return false;
		}
	}
	
	public static boolean isEntitledToInteract(Player p, Block block, boolean physical) {
		
		if (block != null) {
			
		
		try {
		if (interactableTypes.contains(block.getType())) {
			
			if (!physical && p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals("§e•§f●§e§lZauberaxt")) {
				
			
			Plot plot = getPlot(block.getLocation());
			
			if (plot == null)
				return false;
			if (plot.getOwner().equals(p.getUniqueId()) || plot.getTrusted().contains(p.getUniqueId()))
				return true;
			if (plot.getFlagValue("use") == null)
				return false;
			if (plot.getFlagValue("use").equals("false"))
				return false;
			
			}else {
				Plot plot = getPlot(block.getLocation());
				
				if (plot == null) {
					Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");
					return false;
				}
				if (plot.getOwner().equals(p.getUniqueId()) || plot.getTrusted().contains(p.getUniqueId()))
					return true;
				if (plot.getFlagValue("use") == null) {
					Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");

					return false;
				}
				if (plot.getFlagValue("use").equals("false")) {
					Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");
					return false;
				}
				if (plot.getFlagValue("use").equals("true")) {
					return true;
				}
			}
		}else {
			return false;
		}
	}catch (NullPointerException ex) {
		Plot plot = getPlot(block.getLocation());
		
		if (plot == null) {
			Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");
			return false;
		}
		if (plot.getOwner().equals(p.getUniqueId()) || plot.getTrusted().contains(p.getUniqueId()))
			return true;
		if (plot.getFlagValue("use") == null) {
			Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");
			return false;
		}
		if (plot.getFlagValue("use").equals("false")) {
			Utils.sendNoSpamMsg(p, Main.Prefix+"§cDu darfst hier nicht mit §6"+block.getType() + " §cinteragieren!");
			return false;
		}
	}
		return false;
	}
	
		return false;
	}

	
	public static void confirmPlot(Plot plot, Player p) {
		
		int price = plot.getPrice();
			
			if (MoneyManager.getMoney(p) >= price) {
				MoneyManager.setMoney(p, MoneyManager.getMoney(p)-price);
				
				PlotManager.claimPlot(plot, p);
				Utils.sendNoSpamMsg(p, Main.Prefix+"§aGrundstück wurde erfolgreich für §2"+price+"$ §agekauft!!");
				
				Listener_OnMark.loc1.get(p).getBlock().getState().update();
				Listener_OnMark.loc2.get(p).getBlock().getState().update();

				Listener_OnMark.loc1.remove(p);
				Listener_OnMark.loc2.remove(p);
				
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
				
				
			}else {
				Utils.sendNoSpamMsg(p, "Du hast nicht genug Geld!");
			}
		
		
		
		
		
		
		
	}
	
	public static void showCorners(Player p, Plot plot) {
		int y1 = p.getLocation().getBlockY()-10;
		int y2 = p.getLocation().getBlockY()+50;
		
		Location targetLoc1 = new Location(Main.plotworld, plot.getCorner1().getX(), y2, plot.getCorner1().getY());
		Location guardianLoc1 = new Location(Main.plotworld, plot.getCorner1().getX(), y1, plot.getCorner1().getY());
		
		Location targetLoc2 = new Location(Main.plotworld, plot.getCorner2().getX(), y2, plot.getCorner2().getY());
		Location guardianLoc2 = new Location(Main.plotworld, plot.getCorner2().getX(), y1, plot.getCorner2().getY());
		
		Location targetLoc3 = new Location(Main.plotworld, plot.getCorner3().getX(), y2, plot.getCorner3().getY());
		Location guardianLoc3 = new Location(Main.plotworld, plot.getCorner3().getX(), y1, plot.getCorner3().getY());
		
		Location targetLoc4 = new Location(Main.plotworld, plot.getCorner4().getX(), y2, plot.getCorner4().getY());
		Location guardianLoc4 = new Location(Main.plotworld, plot.getCorner4().getX(), y1, plot.getCorner4().getY());
		
		ArmorStand armorstand1 = targetLoc1.getWorld().spawn(targetLoc1, ArmorStand.class);
		ArmorStand armorstand2 = targetLoc2.getWorld().spawn(targetLoc2, ArmorStand.class);
		ArmorStand armorstand3 = targetLoc3.getWorld().spawn(targetLoc3, ArmorStand.class);
		ArmorStand armorstand4 = targetLoc4.getWorld().spawn(targetLoc4, ArmorStand.class);
		
		armorstand1.setGravity(false);
		armorstand1.setVisible(false);
		armorstand2.setGravity(false);
		armorstand2.setVisible(false);
		armorstand3.setGravity(false);
		armorstand3.setVisible(false);
		armorstand4.setGravity(false);
		armorstand4.setVisible(false);
		
		Laser laser1 = new Laser(guardianLoc1);
		laser1.setTarget(p, armorstand1);

		Laser laser2 = new Laser(guardianLoc2);
		laser2.setTarget(p, armorstand2);
		
		Laser laser3 = new Laser(guardianLoc3);
		laser3.setTarget(p, armorstand3);
		
		Laser laser4 = new Laser(guardianLoc4);
		laser4.setTarget(p, armorstand4);
		

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				armorstand1.remove();
				armorstand2.remove();
				armorstand3.remove();
				armorstand4.remove();
				
			}
		},2*20);
			
		ArrayList<Laser> list = new ArrayList<>();
		list.add(laser1);
		list.add(laser2);
		list.add(laser3);
		list.add(laser4);
		
		inspect.put(p, list);
	}
	
	public static void hideCorners(Player p) {
		for (Laser laser : inspect.get(p)) {
			laser.despawn(p);
		}
		
		inspect.remove(p);
	}
	
	public static List<Material> interactableTypes = Arrays.asList(
			Material.DISPENSER,
			Material.NOTE_BLOCK,
			Material.BED_BLOCK,
			Material.WORKBENCH,
			Material.FURNACE,
			Material.BURNING_FURNACE,
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.SPRUCE_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.WOODEN_DOOR,
			Material.LEVER,
			Material.IRON_DOOR_BLOCK,
			Material.STONE_BUTTON,
			Material.CAKE_BLOCK,
			Material.DIODE_BLOCK_OFF,
			Material.DIODE_BLOCK_ON,
			Material.TRAP_DOOR,
			Material.FENCE_GATE,
			Material.ENCHANTMENT_TABLE,
			Material.BREWING_STAND,
			Material.DRAGON_EGG,
			Material.ENDER_CHEST,
			Material.COMMAND,
			Material.BEACON,
			Material.WOOD_BUTTON,
			Material.ANVIL,
			Material.CHEST,
			Material.TRAPPED_CHEST,
			Material.DIODE,
			Material.REDSTONE_COMPARATOR_OFF,
			Material.REDSTONE_COMPARATOR_ON,
			Material.HOPPER,
			Material.DROPPER,
			Material.FLOWER_POT,
			Material.REDSTONE_ORE,
			Material.STONE_PLATE,
			Material.WOOD_PLATE,
			Material.IRON_PLATE,
			Material.GOLD_PLATE,
			Material.JUKEBOX);
}
