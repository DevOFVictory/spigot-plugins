package de.devofvictory.wargame.utils;

import org.bukkit.scheduler.BukkitRunnable;

public class BossBarUpdater extends BukkitRunnable{
	
	BossBar bossbar;
	
	public BossBarUpdater(BossBar bossbar) {
		this.bossbar = bossbar;
	}

	@Override
	public void run() {
		for (Bar b : BossBar.bars.values()) {
			b.update();
		}
		
	}

}
