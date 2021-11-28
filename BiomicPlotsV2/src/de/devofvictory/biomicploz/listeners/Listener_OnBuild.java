package de.devofvictory.biomicploz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devofvictory.biomicploz.commands.Command_Build;
import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Utils;

public class Listener_OnBuild implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		if (Command_Build.powermode.contains(e.getPlayer()))
			return;

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getBlock().getLocation())) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier nicht abbauen!");

		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		if (Command_Build.powermode.contains(e.getPlayer()))
			return;

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getBlock().getLocation())) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier nicht bauen!");
		}
	}

	@EventHandler
	public void onEntityInteractAt(PlayerInteractAtEntityEvent e) {
		if (Command_Build.powermode.contains(e.getPlayer()))
			return;
		if (e.getRightClicked().getType() == EntityType.PLAYER)
			return;

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getRightClicked().getLocation())
				&& e.getRightClicked().getType() != EntityType.ITEM_FRAME) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier nicht mit Entitäten interagieren!");
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		if (Command_Build.powermode.contains(e.getPlayer()))
			return;
		if (e.getRightClicked().getType() == EntityType.PLAYER)
			return;

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getRightClicked().getLocation())) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier nicht mit Entitäten interagieren!");
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR) {

			Player p = e.getPlayer();
			Plot plot = PlotManager.getPlot(e.getClickedBlock().getLocation());

			if (Command_Build.powermode.contains(e.getPlayer()))
				return;

			switch (e.getAction()) {
			case RIGHT_CLICK_BLOCK:

				try {
					if (PlotManager.interactableTypes.contains(e.getClickedBlock().getType())) {
						if (!e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e•§f●§e§lZauberaxt")) {
							if (plot != null) {
								if (!plot.getOwner().equals(p.getUniqueId())
										&& !plot.getTrusted().contains(p.getUniqueId())
										&& !plot.getFlagValue("use").equals("true"))
									e.setCancelled(true);
								Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht mit §6"
										+ e.getClickedBlock().getType().name() + " §cinteragieren!");
							} else {

								if (e.getClickedBlock().getLocation().getWorld().getName()
										.equalsIgnoreCase("plotworld")) {

									Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht mit §6"
											+ e.getClickedBlock().getType().name() + " §cinteragieren!");
									e.setCancelled(true);
								}
							}
						}
					}else {
						if (!e.getItem().getType().isBlock()) {
							if (!PlotManager.isEntitledToBuild(p.getUniqueId(), e.getClickedBlock().getLocation())) {
								e.setCancelled(true);
								Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht interagieren!");
							}
						}
					}
				} catch (NullPointerException ex) {
					if (plot != null) {
						if (plot.getFlagValue("use") != null) {
							if (!plot.getOwner().equals(p.getUniqueId()) && !plot.getTrusted().contains(p.getUniqueId())
									&& plot.getFlagValue("use").equals("false"))
								if (e.getClickedBlock().getLocation().getWorld().getName()
										.equalsIgnoreCase("plotworld")) {
									e.setCancelled(true);
									Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht mit §6"
											+ e.getClickedBlock().getType().name() + " §cinteragieren!");
								}
						} else {
							e.setCancelled(true);
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht mit §6"
									+ e.getClickedBlock().getType().name() + " §cinteragieren!");
						}
					} else {
						if (e.getClickedBlock().getLocation().getWorld().getName().equalsIgnoreCase("plotworld"))
							e.setCancelled(true);
					}

				}

				break;
			case LEFT_CLICK_BLOCK:

				break;
			case RIGHT_CLICK_AIR:

				break;
			case LEFT_CLICK_AIR:

				break;
			case PHYSICAL:

				if (plot != null) {
					if (plot.getFlagValue("use") != null) {
						if (!plot.getOwner().equals(p.getUniqueId()) && !plot.getTrusted().contains(p.getUniqueId())
								&& !plot.getFlagValue("use").equals("true"))
							e.setCancelled(true);
					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht §cinteragieren!");
						e.setCancelled(true);
					}
				} else {
					Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu darfst hier nicht §cinteragieren!");
					e.setCancelled(true);
				}

				break;

			default:
				break;

			}
		}
	}

	@EventHandler
	public void onBukkitFill(PlayerBucketFillEvent e) {
		if (Command_Build.powermode.contains(e.getPlayer()))
			return;

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getBlockClicked().getLocation())) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier keine Eimer füllen!");
		}
	}

	@EventHandler
	public void onBukkitEmpty(PlayerBucketEmptyEvent e) {
		if (Command_Build.powermode.contains(e.getPlayer()))
			return;

		if (PlotManager.getPlot(e.getBlockClicked().getLocation()) != null) {
			Plot plot = PlotManager.getPlot(e.getBlockClicked().getLocation());

			if (plot.isWallLocation(e.getBlockClicked().getLocation())) {
				Utils.sendNoSpamMsg(e.getPlayer(), "§cAuf dem Rand deines Plotes darfst du keinen Eimer leeren!");
				e.setCancelled(true);
				return;
			}
		}

		if (!PlotManager.isEntitledToBuild(e.getPlayer().getUniqueId(), e.getBlockClicked().getLocation())) {
			e.setCancelled(true);
			Utils.sendNoSpamMsg(e.getPlayer(), Main.Prefix + "§cDu darfst hier keine Eimer leeren!");
		}
	}

}
