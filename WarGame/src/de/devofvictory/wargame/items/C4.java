package de.devofvictory.wargame.items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ClearUtil;

public class C4 implements Listener {

	static HashMap<Player, ArrayList<Location>> c4Locations = new HashMap<>();
	public static ArrayList<Player> primed = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		try {

			if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {

				if (e.getItem().getType() == Material.STONE_BUTTON
						&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lC4")) {

					if (c4Locations.containsKey(e.getPlayer())) {

						if (c4Locations.get(e.getPlayer()).size() >= 1) {
							
							if (e.getPlayer().isSneaking()) {
								
								primed.add(e.getPlayer());
								
							for (Location loc : c4Locations.get(e.getPlayer())) {
								Bukkit.getWorld("map").createExplosion(loc, 5, true);
							}

							e.getPlayer().sendMessage(Main.Prefix + "§6§lC4 §awurde an §2"
									+ c4Locations.get(e.getPlayer()).size() + " §aPositionen gezündet!");
							

							c4Locations.get(e.getPlayer()).clear();
							
							}else {
								e.getPlayer().sendMessage(Main.Prefix+"§cBitte sneake zur Sicherheit beim Zünden!");
							
							}
						} else {
							e.getPlayer().sendMessage(Main.Prefix + "§cDu hast noch kein §6§lC4 §cgesetzt!");
						}

					} else {
						e.getPlayer().sendMessage(Main.Prefix + "§cDu hast noch kein §6§lC4 §cgesetzt!");
					}

				}

			}

		} catch (NullPointerException ex) {
		}

	}
	
	public static Player getPlacer(Location loc) {
		for (Player player : c4Locations.keySet()) {
			if (c4Locations.get(player).contains(loc))
				return player;
		}
		return null;
	}
	
	public static ArrayList<Location> possibleC4Locations(Location loc) {
		ArrayList<Location> possibleLocs = new ArrayList<>();
		for (ArrayList<Location> allLocs : c4Locations.values()) {
			for (Location current : allLocs) {
				if (current.distance(loc) <= 6) {
					possibleLocs.add(current);
				}
			}
		}
		return possibleLocs;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		try {

			if (e.canBuild() && e.getItemInHand().getType() == Material.STONE_BUTTON
					&& e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lC4")) {

				if (ClearUtil.hasEnough(e.getPlayer(), Material.STONE_BUTTON, "§6§lC4", 2)) {
					
					
					
					e.getPlayer().sendMessage(Main.Prefix + "§6§lC4 §agesetzt!");
					if (c4Locations.containsKey(e.getPlayer())) {
						c4Locations.get(e.getPlayer()).add(e.getBlock().getLocation());
					} else {
						c4Locations.put(e.getPlayer(), new ArrayList<Location>());
						c4Locations.get(e.getPlayer()).add(e.getBlock().getLocation());
					}

					
					
				} else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Main.Prefix + "§cDu brauchst noch ein §6§lC4 §czum Anzünden!");
				}
					
				

			}

		} catch (NullPointerException ex) {

		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.STONE_BUTTON && Main.isMatchRunning && isRealC4(e.getBlock().getLocation())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.Prefix + "§cDu kannst kein §6§lC4 §cabbauen!");
		}
	}
	
	boolean isRealC4(Location loc) {
		for (ArrayList<Location> locs : c4Locations.values()) {
			if (locs.contains(loc)) {
				return true;
			}
		}
		return false;
	}

}
