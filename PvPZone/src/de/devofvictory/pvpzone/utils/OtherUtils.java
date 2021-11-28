package de.devofvictory.pvpzone.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class OtherUtils {
	
	public static boolean isInRegion(Location loc, Location minLocation, Location maxLocation) {
		return (minLocation.getBlockX() <= loc.getBlockX()
		        && minLocation.getBlockY() <= loc.getBlockY()
		        && minLocation.getBlockZ() <= loc.getBlockZ()
		        && maxLocation.getBlockX() >= loc.getBlockX()
		        && maxLocation.getBlockY() >= loc.getBlockY()
		        && maxLocation.getBlockZ() >= loc.getBlockZ());
	}
	
   public static void sendActionbar(Player p, String message) {
	     PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), (byte) 2);
	     ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	   }
   public static double roundAvoid(double value, int places) {
	    double scale = Math.pow(10, places);
	    return Math.round(value * scale) / scale;
	}

}
