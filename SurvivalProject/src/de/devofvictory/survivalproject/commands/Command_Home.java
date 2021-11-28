package de.devofvictory.survivalproject.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.devofvictory.survivalproject.main.Main;

public class Command_Home implements CommandExecutor, Listener{
	
	static HashMap<Player, Integer> teleporting = new HashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("home")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				
				p.sendMessage(Main.Prefix+"§eTeleportierung läuft... Bewege dich 5 Sekunden nicht!");
				
				p.spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation().add(0,1,0), 1);
				
				p.sendTitle("§5§lTeleportierung läuft...", "§cNicht bewegen!");
				
				int sched = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						teleporting.remove(p);
						
						try {
						
							p.teleport(p.getBedSpawnLocation());
							p.sendMessage(Main.Prefix+"§aErfolgreich teleportiert!");
						
						
						}catch (IllegalArgumentException ex){
							
							p.sendMessage(Main.Prefix+"§cDein Bett existiert nicht mehr!");
							
						}
						
					}
				}, 20*5);
				
				teleporting.put(p, sched);
				
				
			}
		}
		
		
		return true;
	}
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (teleporting.containsKey(e.getPlayer())) {
			
				e.getPlayer().sendMessage(Main.Prefix+"§4Du hast dich bewegt!");
				Bukkit.getScheduler().cancelTask(teleporting.get(e.getPlayer()));
				teleporting.remove(e.getPlayer());
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 10, 10);
			
			
		}
	}
	

}
