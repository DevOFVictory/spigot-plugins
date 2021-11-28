package de.devofvictory.wargame.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

public  class BossBar {
	
	public static Map<String, Bar> bars = new HashMap<>();
	
	public static void set(Player p, String msg, float health) {
		if (!bars.containsKey(p.getName())) {
			bars.put(p.getName(), new Bar(p, msg, health));
		}
	}
	
	public static void remove(Player p) {
		if (bars.containsKey(p.getName())) {
			bars.remove(p.getName());
		}
	}
	
	public static EntityWither getWither(Player p) {
		return bars.get(p.getName()).e;
	}
	
	public static void setMessage(Player p, String msg) {
		set(p, msg, 300);
	}
	
	public static void setMessage(Player p, String msg, int seconds) {
		set(p, msg, 300);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				try {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(BossBar.getWither(p).getId());
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				BossBar.remove(p);
				}catch (NullPointerException ex) {
					// TODO: handle exception
				}
				
			}
		}, seconds*20);
	}
	
	public static void setMessage(Player p, String msg, int seconds, double lives) {
		set(p, msg, (float)lives);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				try {
					PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(BossBar.getWither(p).getId());
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
					BossBar.remove(p);
				}catch (NullPointerException ex) {
					// TODO: handle exception
				}
			}
		}, seconds*20);
	}
	
	public static void setMessage(Player p, String msg, double lives) {
		set(p, msg, (float)lives);
	}
	public static void setMessage(Player p, String msg, int seconds, float percent) {
		set(p, msg, percent*2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				try {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(BossBar.getWither(p).getId());
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				BossBar.remove(p);
				}catch (NullPointerException ex) {
					// TODO: handle exception
				}
				
			}
		}, seconds*20);
	}
	
	public static void setMessage(Player p, String msg, float percent) {
		set(p, msg, percent*2);
	}
	
	

}
