package de.devofvictory.wargame.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;

public class StartDeathMatch {
	
	static Location middle = new Location(Bukkit.getWorld("map"), 430, 100, -409);
	
	public static Location currentCenter = middle;
	
	static int timeUntilBorder;
	
	static int time;
	
	/*
	
	boolean istEmmaCool = true;
	
	DevOFVictorys Schwester war HIER!!!!
	
	*/
	
	public StartDeathMatch() {
		
		if (Main.isMatchRunning) {
		
		
		Bukkit.getScheduler().cancelTask(StartGame.sched);
		Bukkit.broadcastMessage(Main.Prefix+"§4Deathmatch hat begonnen! Auf in den Kampf!");
		
		
		Location loc = RandomPos.getLoc(middle, 50);
		
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
					WorldBorder border = Bukkit.getWorld("map").getWorldBorder();
					border.setCenter(loc.getX(), loc.getZ());
					currentCenter = loc;
					
					double size = border.getSize();
					
					if (size > 20) {
						
						Bukkit.broadcastMessage(Main.Prefix+"§eAchtung: Die Weltborder wird kleiner!");
						border.setSize(size-40D, 30L);
						
						oneMinuteLeftToBorder();
						
						
					}else {
						
						Bukkit.broadcastMessage(Main.Prefix+"§cIn 30 Sekunden wird der Gewinner zufällig entschieden!");

						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								
								Random r = new Random();
								Player randomWinner = StartGame.living.get(r.nextInt(StartGame.living.size()));
								
								Bukkit.broadcastMessage(Main.Prefix+"§4Es wurde "+randomWinner.getName()+" zufällig aufgewählt!");
								
								for (Player living : StartGame.living) {
									if (living != randomWinner)
										living.setHealth(0);
								}
								
							}
						},30*20);

						
					}
					
			}
					
		}, 0, 90*20);
		
		
		
		}
	}
	
	static void oneMinuteLeftToBorder() {
		

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				time = 60;
				
				timeUntilBorder = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						time--;
						
						ScoreBoardManager.upDateScoreboard(time, 0, StartGame.living.size());
						
						if (time == 0) {
							Bukkit.getScheduler().cancelTask(timeUntilBorder);

						}
						
					}
				}, 0*20, 1*20);
				
			}
		}, 30*20);
		


		
	}
		
	}
	
	
		


