package de.devofvictory.skykitpvp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.devofvictory.skykitpvp.main.Main;

public class LevelBarUtil {
	
	private static HashMap<Player, LevelBarUtil> activeBars = new HashMap<Player, LevelBarUtil>();
	
	
	private int maxTime;

	private ArrayList<Player> players;

	private boolean useLevel;

	private BukkitTask scheduler;

	private boolean isPasued;

	private Runnable whenFinished;

	private long pausedValue;

	public LevelBarUtil(int time, boolean useLevel, Runnable whenFinished) {
		this.players = new ArrayList<>();
		this.useLevel = useLevel;
		this.maxTime = time;
		this.whenFinished = whenFinished;
	}

	public static void abortLevelBar(Player p) {
		if (activeBars.containsKey(p)) {
			LevelBarUtil levelbar = activeBars.get(p);
			
			levelbar.stop();
			p.setExp(0f);
		}
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public Runnable getWhenFinished() {
		return this.whenFinished;
	}

	public void setUseLevel(boolean useLevel) {
		this.useLevel = useLevel;
	}

	public boolean isUseLevel() {
		return this.useLevel;
	}

	public boolean isPasued() {
		return this.isPasued;
	}

	public int getTime() {
		return this.maxTime;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player p) {
		if (!this.players.contains(p))
			this.players.add(p);
	}

	public void removePlayer(Player p) {
		if (this.players.contains(p))
			this.players.remove(p);
	}

	public BukkitTask getScheduler() {
		return this.scheduler;
	}

	public void setPausedValue(long pausedValue) {
		this.pausedValue = pausedValue;
	}

	public void start() {
		
		for (Player player : players) {
			activeBars.put(player, this);
		}
		
		final long duration = TimeUnit.SECONDS.toNanos(this.maxTime);
		final long start = System.nanoTime();
		this.scheduler = (new BukkitRunnable() {
			public void run() {
				long diff = System.nanoTime() - start;
				if (diff > duration) {
					cancel();
					if (whenFinished != null)
						whenFinished.run();
				}
				for (Player player : players) {
					try {
						player.setExp(1.0F - (float) diff / (float) duration);
					} catch (IllegalArgumentException ex) {
					}

				}
			}
		}).runTaskTimer((Plugin) Main.getInstance(), 0L, 1L);
	}

	public void startToUp() {
		final long duration = TimeUnit.SECONDS.toNanos(this.maxTime);
		final long start = System.nanoTime();
		this.scheduler = (new BukkitRunnable() {
			public void run() {
				long diff = System.nanoTime() - start;
				if (diff > duration) {
					cancel();
					whenFinished.run();
				}
				for (Player player : players) {
					try {
						player.setExp(1.0F - (float) diff / (float) duration);
					} catch (IllegalArgumentException ex) {
					}
				}
			}
		}).runTaskTimer((Plugin) Main.getInstance(), 0L, 1L);
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(this.scheduler.getTaskId());
	}

	public void pause() {
		if (!this.isPasued) {
			this.isPasued = true;
			Bukkit.getScheduler().cancelTask(this.scheduler.getTaskId());
		}
	}

	public void resume() {
		if (this.isPasued) {
			final long duration = TimeUnit.SECONDS.toNanos(this.maxTime);
			final long start = this.pausedValue;
			this.scheduler = (new BukkitRunnable() {
				public void run() {
					long diff = System.nanoTime() - start;
					if (diff > duration) {
						cancel();
						whenFinished.run();
					}
					for (Player player : players) {
						try {
							player.setExp(1.0F - (float) diff / (float) duration);
						} catch (IllegalArgumentException ex) {
						}
					}
				}
			}).runTaskTimer((Plugin) Main.getInstance(), 0L, 1L);
		}
	}
}