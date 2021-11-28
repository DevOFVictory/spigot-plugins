package de.devofvictory.wargame.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.devofvictory.wargame.items.C4;
import de.devofvictory.wargame.items.Granate;
import de.devofvictory.wargame.items.WurfMine;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.EndGame;
import de.devofvictory.wargame.utils.Holograms;
import de.devofvictory.wargame.utils.Leiche;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.StartDeathMatch;
import de.devofvictory.wargame.utils.StartGame;
import de.devofvictory.wargame.utils.Vote;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

@SuppressWarnings("deprecation")
public class Listener_OnDeath implements Listener{
	
	Location randomLoc;
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		if (e.getEntity() instanceof Player) {
			
		Player p = e.getEntity();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				p.spigot().respawn();
				
			}
		},5);
			
		
		
		StartGame.livesLeft.put(p, StartGame.livesLeft.get(p)-1);
		if (StartGame.livesLeft.get(p) > 0) {
			
			int worldborderRadius = (int)Bukkit.getWorld("map").getWorldBorder().getSize()/2;
			
			
			try {
				randomLoc = new Location(StartDeathMatch.currentCenter.getWorld(), ThreadLocalRandom.current().nextInt(StartDeathMatch.currentCenter.getBlockX()-worldborderRadius, StartDeathMatch.currentCenter.getBlockX()+worldborderRadius), 100, ThreadLocalRandom.current().nextInt(StartDeathMatch.currentCenter.getBlockZ()-worldborderRadius, StartDeathMatch.currentCenter.getBlockZ()+worldborderRadius));
			}catch (IllegalArgumentException ex) {
				randomLoc = Bukkit.getWorld("map").getWorldBorder().getCenter();
				randomLoc.setY(100);
			}
			e.setKeepInventory(true);
			
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					p.sendMessage(Main.Prefix+"§4§lLeben: "+StartGame.livesLeft.get(p)+"/"+Vote.votedLives);
					
					
					p.setMaxHealth(40);
					p.setHealth(20);
					p.setHealthScale(40);
						
					p.teleport(randomLoc);
					
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							Chicken c = (Chicken) p.getWorld().spawnCreature(randomLoc, CreatureType.CHICKEN);
							c.setPassenger((Entity)p);
							StartGame.ride.put(p.getName(), c);
							
						}
					},10);

				}
			},10);
			
			
			
		}else {
			StartGame.setDeath(p);
			e.setKeepInventory(false);
			
			
			Location loc = p.getLocation();
	        
	        
	        
	        String text[] = {"§a"+p.getName()};
	        
	        Holograms nameHolo = new Holograms(text, loc.add(0, 1, 0));
	        nameHolo.showAll();
	        
	        p.sendMessage(Main.Prefix+"§2§lKills: §a§l"+EndGame.getKills(p));
	        
	        Main.getMinigame().getStats(p).addDeath();
			
	        
			
			if (SpectatorClass.specCamera.containsValue(p.getName())) {
				for (String key : SpectatorClass.specCamera.keySet()) {
					if (SpectatorClass.specCamera.get(key).equalsIgnoreCase(p.getName())) {
						Player keyPlayer = Bukkit.getPlayer(key);
						
						PacketPlayOutCamera camera = new PacketPlayOutCamera();
						camera.a = keyPlayer.getEntityId();
						((CraftPlayer)keyPlayer).getHandle().playerConnection.sendPacket(camera);
						keyPlayer.setGameMode(GameMode.SURVIVAL);

						keyPlayer.sendMessage(Main.Prefix+"§cDu beobachtest §6"+p.getName()+" §cnun nicht mehr!");
					
					}
				}
				
			}
	        
	        try {
				for (ItemStack is : e.getDrops()) {
					if (is.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMultiTool") || is.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSchwert")) {
						e.getDrops().remove(is);
					}
				}
			}catch (Exception ex) {
				// TODO: handle exception
			}
	        
	        try {
		        Leiche npc = null;
				npc = new Leiche(p.getName(), loc.clone().subtract(0,-1,0));
		        npc.spawn(null);
		        npc.sleep(true);
	        }catch (NullPointerException ex) {
	        	
	        }
		}
		
		
		if (p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			
			EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)p.getLastDamageCause();
			
			if (edbee.getDamager() instanceof Projectile) {
	    		Projectile pr = (Projectile)edbee.getDamager();
	    			if (pr.getShooter() instanceof Player) {
	    				Player killer = (Player)pr.getShooter();
	    	
	    	
		if (killer != null) {
			
			if (pr.getCustomName().equalsIgnoreCase("akarrow")) {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwurde von §6"+killer.getName()+" §dmit einer AK eliminiert!");
			}else if (pr.getCustomName().equalsIgnoreCase("sniperarrow")) {
				e.setDeathMessage(Main.Prefix+"§6"+killer.getName()+" §dhat §6"+p.getName()+" §dmit einer Sniper aus "+(int)p.getLocation().distance(killer.getLocation())+" m den Pickel aus dem Gesicht gefetzt!");
			}else if (pr.getCustomName().equalsIgnoreCase("pistolarrow")) {
				e.setDeathMessage(Main.Prefix+"§6"+killer.getName()+" §dhat §6"+p.getName()+" §dden Tag mit einer einzigen Pistole versaut!");
			}else if (pr.getCustomName().equalsIgnoreCase("rocketlauncherfireball")) {
				e.setDeathMessage(Main.Prefix+"§dBei §6"+p.getName()+" §dhat es wegen §6"+killer.getName()+"§ds RocketLauncher ganz doll geknallt!");
			}else if (pr.getCustomName().equalsIgnoreCase("shotgunsnowball")) {
				e.setDeathMessage(Main.Prefix+"§6"+killer.getName()+" §dhat §6"+p.getName()+" §ddas Gesicht mit Schrot zugebomt!");
			}else if (pr.getCustomName().equalsIgnoreCase("whiterideenderpearl")) {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist nicht lebend aus dem WhiteRide herausgegangen!");
			}else if (pr.getCustomName().equalsIgnoreCase("machinegunarrow")) {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwurde von §6"+killer.getName()+" §dmit einem Maschinengewähr durchlöchert!");
			}else {
				
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist tragischerweise verreckt!");
			}
			
			String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
            Location location = e.getEntity().getLocation();
            Holograms holo = new Holograms(Text, location);
            holo.showPlayerTemp(killer, 20*3);
            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
            Main.getMinigame().getStats(killer).addKill();
            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
            
            
			
		}else {
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist tragischerweise verreckt!");
		}
	    			}else {
	    				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist tragischerweise verreckt!");
	    			}
		}else {
			Player killer = p.getKiller();
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwurde von §6"+killer.getName()+" §dzu hart verprügelt!");
			
			String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
            Location location = e.getEntity().getLocation();
            Holograms holo = new Holograms(Text, location);
            holo.showPlayerTemp(killer, 20*3);
            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
            Main.getMinigame().getStats(killer).addKill();
            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
		}
	    			
			}else {
		
		if (p.getLastDamageCause().getCause() == DamageCause.BLOCK_EXPLOSION) {
			
			if (Granate.lastShooter != null) {
				
				Player killer = Granate.lastShooter;
				
				String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
	            Location location = e.getEntity().getLocation();
	            Holograms holo = new Holograms(Text, location);
	            holo.showPlayerTemp(killer, 20*3);
	            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
	            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
	            Main.getMinigame().getStats(killer).addKill();
	            
	           e.setDeathMessage(Main.Prefix+"§6"+killer.getName()+" §dhat §6"+p.getName()+" §deine gekanllt!");
				Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
				Objective obj = sb.getObjective("Kills");
				obj.getScore(killer.getName()).setScore(obj.getScore(killer.getName()).getScore()+1);
				
				
			}else if (WurfMine.getMineKiller(p.getLocation()) != null) {
				
					Player killer = Bukkit.getPlayer(WurfMine.getMineKiller(p.getLocation()));
					
					e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dhat schlechte Bekanntschaft mit §6"+killer.getName()+"§ds Wurfmine gemacht!");
					
					String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
		            Location location = e.getEntity().getLocation();
		            Holograms holo = new Holograms(Text, location);
		            holo.showPlayerTemp(killer, 20*3);
		            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
		            Main.getMinigame().getStats(killer).addKill();
		            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
		            
					Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
					Objective obj = sb.getObjective("Kills");
					obj.getScore(killer.getName()).setScore(obj.getScore(killer.getName()).getScore()+1);
					
			}else if (!C4.primed.isEmpty()) {
				
				Bukkit.broadcastMessage(Main.Prefix+"§aDebug 1");
				
					ArrayList<Location> possibleLocations = C4.possibleC4Locations(p.getLocation());
					for (Location possibleLoc : possibleLocations) {
						Bukkit.broadcastMessage(Main.Prefix+"§aDebug 2");
						if (C4.primed.contains(C4.getPlacer(possibleLoc))) {
							Player killer = C4.getPlacer(possibleLoc);
							Bukkit.broadcastMessage(Main.Prefix+"§aDebug 3");
							
							e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dentdeckte §6"+killer.getName()+"§ds C4 nicht rechteitig!");
							
							String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
				            Location location = e.getEntity().getLocation();
				            Holograms holo = new Holograms(Text, location);
				            holo.showPlayerTemp(killer, 20*3);
				            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
				            Main.getMinigame().getStats(killer).addKill();
				            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
				            
							Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
							Objective obj = sb.getObjective("Kills");
							obj.getScore(killer.getName()).setScore(obj.getScore(killer.getName()).getScore()+1);
						}else {
							e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dentdeckte das C4 nicht rechtzeitig!");
						}
					}
					
			}else {
				Bukkit.broadcastMessage(Main.Prefix+"§aDebug 0");
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dsah aus wie ein Feuerwerk als es knallte!");
			}
			
			
		}else if (p.getLastDamageCause().getCause() == DamageCause.FALL) {
			
			
			if (Main.lastBlaeserHit.containsKey(p.getName())) {
				Player killer = Bukkit.getPlayer(Main.lastBlaeserHit.get(p.getName()));
				if (killer != null) {
					Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
					Objective obj = sb.getObjective("Kills");
					obj.getScore(killer.getName()).setScore(obj.getScore(killer.getName()).getScore()+1);
					e.setDeathMessage(Main.Prefix+"§6"+killer.getName()+" §dhat §6"+p.getName()+" §deinen mit dem Laubbläser geblasen! §7§o\"hehe :)\"§d!");
					
					String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
		            Location location = e.getEntity().getLocation();
		            Holograms holo = new Holograms(Text, location);
		            holo.showPlayerTemp(killer, 20*3);
		            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
		            Main.getMinigame().getStats(killer).addKill();
		            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
		            
				}
			}else {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dsollte lernen auf den Füßen zu landen!");
			}
		}else if (p.getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) {
			
			Bukkit.broadcastMessage(Main.Prefix+"§aDebug 0");
			Bukkit.broadcastMessage(Main.Prefix+C4.primed.size());
			if (C4.primed.size() > 0) {
			
			Bukkit.broadcastMessage(Main.Prefix+"§aDebug 1");
			
				ArrayList<Location> possibleLocations = C4.possibleC4Locations(p.getLocation());
				for (Location possibleLoc : possibleLocations) {
					Bukkit.broadcastMessage(Main.Prefix+"§aDebug 2");
					if (C4.primed.contains(C4.getPlacer(possibleLoc))) {
						Player killer = C4.getPlacer(possibleLoc);
						Bukkit.broadcastMessage(Main.Prefix+"§aDebug 3");
						
						e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dentdeckte §6"+killer.getName()+"§ds C4 nicht rechteitig!");
						
						String[] Text = {"§f»", "§4§l"+p.getName()+" §c§lgetötet!","§e+10 §6WarCoins", "§f»"};
			            Location location = e.getEntity().getLocation();
			            Holograms holo = new Holograms(Text, location);
			            holo.showPlayerTemp(killer, 20*3);
			            Main.getMinigame().setCoins(killer, Main.getMinigame().getCoins(killer)+10);
			            Main.getMinigame().getStats(killer).addKill();
			            p.sendMessage(Main.Prefix + "§eLeben von "+killer.getName()+": " + getHearts(killer));
			            
						Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
						Objective obj = sb.getObjective("Kills");
						obj.getScore(killer.getName()).setScore(obj.getScore(killer.getName()).getScore()+1);
					}else {
						e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dentdeckte das C4 nicht rechtzeitig!");
					}
				}
			}
			
		}else if (p.getLastDamageCause().getCause() == DamageCause.SUFFOCATION) {
			
			if (Listener_OnBuildOutsideBorder.isOutsideOfBorder(e.getEntity().getLocation().getBlock().getLocation())) {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwurde von der Worldboarder gefressen!");
			}else {
				e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist leider erstickt!");
			}
			
		}else if (p.getLastDamageCause().getCause() == DamageCause.FIRE) {
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist die Sache eindeutig zu heiß geworden!");
		}else if (p.getLastDamageCause().getCause() == DamageCause.SUICIDE) {
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwar leicht depresiv! \"§oAde du schöne Welt!\"");
		}else if (p.getLastDamageCause().getCause() == DamageCause.VOID) {
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dwar leicht depresiv! \"§oAde du schöne Welt!\"");
		}else {
			e.setDeathMessage(Main.Prefix+"§6"+p.getName()+" §dist tragischerweise vereckt!");
		}
			
		}
		
		
		
		
	}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Chicken) {
			e.getDrops().clear();
			e.setDroppedExp(0);
		}
	}
	
	public static String getHearts(Player victem) {
		
		char heart = '❤';
		char half = '❥';
		
		String result = "";
		
		for (int i = 0; i < victem.getHealth()/2; i++) {
			
			result += heart;

		}
		
		if (victem.getHealth() % 2 != 0) {
			result = result.substring(0, result.length() - 1);
			result += half;
		}
		
		
		
		return "§c"+result;
		
	}
	


}
