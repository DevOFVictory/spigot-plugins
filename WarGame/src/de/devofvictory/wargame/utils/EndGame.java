package de.devofvictory.wargame.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class EndGame {
	
	public EndGame(Player winner) {
		
		
			Main.allowDamage = false;
			Main.isMatchRunning = false;
			Bukkit.getScheduler().cancelTask(StartGame.sched);
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					for (Player all : Bukkit.getOnlinePlayers()) {
						PacketPlayOutCamera camera = new PacketPlayOutCamera();
						camera.a = all.getEntityId();
						((CraftPlayer)all).getHandle().playerConnection.sendPacket(camera);
						
						all.setGameMode(GameMode.SURVIVAL);
						all.teleport(new Location(Bukkit.getWorld("world"), 17.77017129922218, 21, 51.41904290788815, (float)90.8998031616211, (float)0.1499878466129303));
						all.setHealth(20);
						all.setSaturation(20);
						all.getInventory().clear();
						new ClearUtil(all);
						all.getWorld().playSound(winner.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						Firework firework = (Firework) all.getWorld().spawnEntity(winner.getLocation(), EntityType.FIREWORK);
						FireworkMeta fireworkmeta = firework.getFireworkMeta();
						fireworkmeta.addEffects(FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL_LARGE).build());
						fireworkmeta.setPower(1);
						firework.setFireworkMeta(fireworkmeta);
						all.setFireTicks(0);
						all.setAllowFlight(false);
						all.setFlying(false);
						
						all.setMaxHealth(20);
						all.setHealth(20);
						all.setHealthScale(20);
						
						for (Player all2 : Bukkit.getOnlinePlayers()) {
							all.showPlayer(all2);
						}
						
						
						
						all.sendMessage(Main.Prefix+"§2§lKills: §a§l"+getKills(all));
						
						
						NameTagHider.show(all);
						
						
						try {
							StartGame.spectators.remove(all);
						}catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}, 25);
			
		Main.getMinigame().getStats(winner).addWin();
		
		
		Bukkit.broadcastMessage(Main.Prefix+"§eDer Server startet in 20 Sekunden neu!");
		
		
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(Main.Prefix+"§aDu wirst nun in die Lobby teleportiert!");
				for (Player all : Bukkit.getOnlinePlayers()) {
					Main.connectToServer(all, "Lobby-1");
				}
			}
		}, 20*20);
				
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						Bukkit.getServer().shutdown();
					}
				}, 25*20);
				
				
			Main.getMinigame().exportAll();	
	}
	
	public static int getKills(Player p) {
		try {
		Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective obj = sb.getObjective("Kills");
		Score score  = obj.getScore(p.getName());
		int kills = score.getScore();
		
		return kills;
	}catch (Exception ex) {
		return -1;
	}
	}

}
