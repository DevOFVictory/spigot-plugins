package de.devofvictory.levelbarapi.main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LevelBar {
	
	private int maxTime;
	private ArrayList<Player> players;
	private boolean useLevel;
	private BukkitTask scheduler;
	private boolean isPasued;
	private Runnable whenFinished;
	private long pausedValue;
	
	public LevelBar(int time, boolean useLevel, Runnable whenFinished) {
		this.players = new ArrayList<Player>();
		this.useLevel = useLevel;
		this.maxTime = time;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Runnable getWhenFinished() {
		return whenFinished;
	}
	
	public void setUseLevel(boolean useLevel) {
		this.useLevel = useLevel;
	}
	
	public boolean isUseLevel() {
		return useLevel;
	}
	
	public boolean isPasued() {
		return isPasued;
	}
	
	
	public int getTime() {
		return maxTime;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player p) {
		if (!players.contains(p))
		players.add(p);
	}
	
	public void removePlayer(Player p) {
		if (players.contains(p))
			players.remove(p);
	}
	
	public BukkitTask getScheduler() {
		return scheduler;
	}
	
	public void setPausedValue(long pausedValue) {
		this.pausedValue = pausedValue;
	}
	
	public void start() {
		long duration = TimeUnit.SECONDS.toNanos(maxTime);
		long start = System.nanoTime();
		scheduler = new BukkitRunnable() {
		    public void run() {
		        long diff = System.nanoTime() - start;
		        if (diff > duration) {
		            this.cancel();
		            
		            whenFinished.run();
		            
		        }
		        for (Player player : players) {
		        	player.setExp(1F - ((float) diff / (float) duration));
		        }
		        
		    }
		    
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(scheduler.getTaskId());
	}
	
	public void pause() {
		if (!isPasued) {
			isPasued = true;
			Bukkit.getScheduler().cancelTask(scheduler.getTaskId());
		}
	}
	
	public void resume() {
		if (isPasued) {
			long duration = TimeUnit.SECONDS.toNanos(maxTime);
			long start = pausedValue;
			scheduler = new BukkitRunnable() {
			    public void run() {
			        long diff = System.nanoTime() - start;
			        if (diff > duration) {
			            this.cancel();
			            
			            whenFinished.run();
			            
			        }
			        for (Player player : players) {
			        	player.setExp(1F - ((float) diff / (float) duration));
			        }
			        
			    }
			    
			}.runTaskTimer(Main.getInstance(), 0, 1);
		}
	}

}

