package de.devofvictory.survivalproject.custom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.survivalproject.utils.TeleporterUtils;

public class Teleporter {

	Location location;
	String color;
	Location arrivalLocation;

	public Teleporter(Location loc) {
		this.location = loc;
		this.color = "WHITE";
	}

	public Teleporter(Location loc, String color) {
		
		this.location = loc;
		this.color = color;
		
	}

	public void setColor(String color) {
		this.color = color;

		TeleporterUtils.allTeleporters.put(this, color);

	}

	public String getColor() {
//		Bukkit.broadcastMessage("3 "+TeleporterUtils.getTeleporter(new Location(Bukkit.getWorld("flat"), -72, 4, 224)).getCounterpart().getLocation());

		return color;
	}

	public Location getLocation() {
		return location;
	}
	
	public void setArrivalLocation(Location arrivalLoc) {
		float yaw = 0;
		float pitch = 0;
		
		int x = this.location.getBlockX();
		int y = this.location.getBlockY();
		int z = this.location.getBlockZ();
		
		if (arrivalLoc.getBlockX() > this.location.getBlockX()) {
			yaw = -90;
			x++;
		}else if (arrivalLoc.getBlockX() < this.location.getBlockX()){
			yaw = 90;
			x--;
		}
		
		if (arrivalLoc.getBlockZ() > this.location.getBlockZ()) {
			yaw = 0;
			z++;
		}else if (arrivalLoc.getBlockZ() < this.location.getBlockZ()){
			yaw = -180;
			z--;
		}
		

		
		this.arrivalLocation = new Location(arrivalLoc.getWorld(), x, y, z, yaw, pitch);
	}
	
	public Location getArrivalLocation() {
		return arrivalLocation;
	}

	public void breakTeleporter() {

		ItemStack item = new ItemStack(Material.WHITE_WOOL);

		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(TeleporterUtils.teleporterName);

		item.setItemMeta(meta);

		location.getWorld().dropItemNaturally(location, item);

		TeleporterUtils.allLocations.remove(location);
		TeleporterUtils.allTeleporters.remove(this);

	}

	public void placeTeleporter() {
		location.getBlock().setType(Material.WHITE_WOOL);
	}

	public Teleporter getCounterpart() {

		for (Teleporter colored : TeleporterUtils.getTeleportersInColor(this.getColor())) {
			if (!colored.equals(this)) {
//				Bukkit.broadcastMessage("5 "+TeleporterUtils.getTeleporter(new Location(Bukkit.getWorld("flat"), -72, 4, 224)).getCounterpart().getLocation());
				return colored;
			}
		}
		return null;
	}

	public boolean hasCounterpart() {
		if (this.getCounterpart() != null) {
			return true;
		} else {
			return false;
		}
	}

}
