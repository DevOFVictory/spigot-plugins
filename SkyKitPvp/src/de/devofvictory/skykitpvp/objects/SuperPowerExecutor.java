package de.devofvictory.skykitpvp.objects;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public abstract interface SuperPowerExecutor {
	
	static ArrayList<Player> timeout = new ArrayList<Player>();
	
	abstract void runSuperPower(Player p);
	

}
