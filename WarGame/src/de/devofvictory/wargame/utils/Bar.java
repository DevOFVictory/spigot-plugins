package de.devofvictory.wargame.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Bar {
	
	Player p;
	String msg;
	public EntityWither e;
	float health;
	
	public Bar(Player p, String msg, float health) {
		this.p = p;
		this.msg = msg;
		this.health = health;
		update();
		
	}
	
	public void update() {
			Vector d = p.getLocation().getDirection();
			Location loc = p.getLocation().add(p.getLocation().add(d.multiply(40)));
			removeEnderdragon();
			WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
			e = new EntityWither(world);
			e.setLocation(loc.getX(), p.getLocation().getY(), loc.getY(), loc.getPitch(), loc.getYaw());
			e.setCustomName(msg);
			e.setInvisible(true);
			e.getBukkitEntity().getHandle().b(true);
			e.setHealth(health);
			
			PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 255);
			((Wither)e.getBukkitEntity()).addPotionEffect(effect, false);
			
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(e);
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			
		}
	
	public void removeEnderdragon() {
		if (e != null) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(e.getId());
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	

}
