package de.devofvictory.skykitpvp.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.Variables;

public class Command_Spawn implements CommandExecutor, Listener{
	
	private static ArrayList<Player> queue = new ArrayList<Player>();
	
	private static HashMap<Player, BukkitTask> tasks = new HashMap<Player, BukkitTask>();
	
	

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				if (p.hasPermission("skykitpvp.cmd.spawn")) {
					
					if (!queue.contains(p)) {
						
						queue.add(p);
						p.sendTitle("§aTeleportvorgang startet...", "§eBewege dich 5 Sekunden lang nicht!");
						p.playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 1, 1);
						
						BukkitTask task = new BukkitRunnable() {
							
							@Override
							public void run() {
								if (queue.contains(p)) {
									p.teleport(Variables.getSpawnLocation());
									p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
									p.sendTitle("§2Teleportvorgang abgeschlossen!", "§aDu bist jetzt am Spawn!");
									abortTeleport(p);
								}else {
									abortTeleport(p);
								}
								
							}
						}.runTaskLater(Main.getInstance(), 5*20);
						
						tasks.put(p, task);
						
						
					}else {
						p.sendMessage(Main.Prefix+"§cDu wirst bereits teleportiert!");
					}
					
				}else {
					p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
				}
			}
		}
		
		return true;
	}
	
	private static void abortTeleport(Player p) {
		if (queue.contains(p)) {
			if (tasks.containsKey(p)) {
				BukkitTask task = tasks.get(p);
				task.cancel();
				tasks.remove(p);
				queue.remove(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
			
			if (queue.contains(p)) {
				abortTeleport(p);
				p.sendTitle("§4Teleportvorgang abgebrochen!", "§cDu hast dich bewegt!");
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
			}
			
			
		}
	}
}
