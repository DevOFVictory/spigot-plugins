package de.devofvictory.survivalproject.listeners;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.devofvictory.survivalproject.custom.Teleporter;
import de.devofvictory.survivalproject.inventorys.INV_ChooseColor;
import de.devofvictory.survivalproject.main.Main;
import de.devofvictory.survivalproject.utils.TeleporterUtils;

public class TeleporterLogic implements Listener {

	public static HashMap<Player, Location> changeColorMap = new HashMap<>();

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		try {

			if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(TeleporterUtils.teleporterName)) {

				Teleporter teleporter = new Teleporter(e.getBlock().getLocation(), TeleporterUtils.getColorString(Material.WHITE_WOOL));
				teleporter.placeTeleporter();
				teleporter.setArrivalLocation(e.getBlock().getLocation().subtract(0,0,1));
				TeleporterUtils.allTeleporters.put(teleporter, "WHITE");
				TeleporterUtils.allLocations.add(teleporter.getLocation());
				
				p.sendMessage(Main.Prefix+"§aTeleporter erfolgreich plaziert!");
				
			}

		} catch (NullPointerException ex) {
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (TeleporterUtils.isTeleporter(e.getBlock().getLocation())) {
			e.setDropItems(false);
			Teleporter t = TeleporterUtils.getTeleporter(e.getBlock().getLocation());
			t.breakTeleporter();
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();

			if (!e.getPlayer().isSneaking()) {
				if (TeleporterUtils.isTeleporter(b.getLocation())) {
					e.setCancelled(true);
					INV_ChooseColor.callInv(e.getPlayer());
					changeColorMap.put(e.getPlayer(), b.getLocation());
	
				}
			}
		}
	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getView().getTitle().equalsIgnoreCase("§6Farbe auswählen")) {
			if (changeColorMap.containsKey(e.getPlayer())) {
				changeColorMap.remove(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e) {
		for (Block b : e.getBlocks()) {

			if (TeleporterUtils.allLocations.contains(b.getLocation())) {

				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e) {
		for (Block b : e.getBlocks()) {

			if (TeleporterUtils.allLocations.contains(b.getLocation())) {

				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent e) {
		
		try {
		
		List<Block> blocks = e.blockList();
		
		for (Block b : blocks) {

			if (TeleporterUtils.allLocations.contains(b.getLocation())) {

				e.blockList().remove(b);
			}
		}
		
		}catch (ConcurrentModificationException ex){
			
		}
	}
	
	@EventHandler
	public void onFire(BlockBurnEvent e) {

		if (TeleporterUtils.allLocations.contains(e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation().getBlock().getLocation().subtract(0, 1, 0);
		
		

		if (TeleporterUtils.isTeleporter(loc)) {

			Teleporter teleporter = TeleporterUtils.getTeleporter(loc);
			

			if (!teleporter.getColor().equalsIgnoreCase("WHITE")) {
				

				if (teleporter.hasCounterpart()) {
					
					Teleporter counterpart = teleporter.getCounterpart();

					Location counterpartLoc = counterpart.getArrivalLocation();
					
					double x = counterpartLoc.getX()+0.5;
					double y = counterpartLoc.getY()+1;
					double z = counterpartLoc.getZ()+0.5;
					
					float yaw = counterpartLoc.getYaw();

					Location toTP = new Location(counterpartLoc.getWorld(), x, y, z, yaw, p.getLocation().getPitch());

					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
					p.teleport(toTP);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
					
					
					
				} else {
					p.sendMessage(Main.Prefix + "§cTeleporter hat noch kein Gegenstück!");
				}
			} else {
				p.sendMessage(Main.Prefix + "§cTeleporter wurde noch nicht kunfiguriert!");
			}
		}
	}

}
