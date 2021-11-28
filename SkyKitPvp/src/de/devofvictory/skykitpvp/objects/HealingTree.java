package de.devofvictory.skykitpvp.objects;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.TreeType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.skykitpvp.main.Main;


public class HealingTree {
	
	private Location location;
	private ArrayList<Location> locations;
	private Player owner;
	private boolean isSpawned;
	private ArmorStand armorstand;
	private int effectLevel;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public HealingTree(Location location, ArrayList<Location> locations, Player owner, int effectLevel) {
		this.location = location;
		this.owner = owner;
		this.locations = locations;
		this.effectLevel = effectLevel;
	}
	
	public ArmorStand getArmorstand() {
		return armorstand;
	}
	
	public boolean spawn() {
		if (location.getWorld().generateTree(location, TreeType.TREE)) {
			isSpawned = true;
			armorstand = location.getWorld().spawn(location, ArmorStand.class);
			armorstand.setVisible(false);
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					if (isSpawned) {
						
						for (Entity nearby : armorstand.getNearbyEntities(7, 7, 7)) {
							if (nearby instanceof Player) {
								Player nearPlayer = (Player)nearby;
								PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 5*20, effectLevel, true, true, true);
								nearPlayer.addPotionEffect(regen);
							}
							
							location.getWorld().spawnParticle(Particle.HEART, armorstand.getLocation().getX(), armorstand.getLocation().getY(), armorstand.getLocation().getZ(), 
									5, 2, 5, 0.1, 200);
						}
						
					}else {
						cancel();
					}
					
				}
			}.runTaskTimer(Main.getInstance(), 0, 20);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isSpawned() {
		return isSpawned;
	}
	
	public void despawn() {
		for (Location loc : locations) {
			loc.getBlock().setType(Material.AIR);
		}
		isSpawned = false;
		armorstand.remove();
	}
	
	
	
	

}
