package de.devofvictory.wargame.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

public class Leiche extends Reflections {

	
	private int entityID;
	private Location location;
	private GameProfile gameprofile;
	
	
	public Leiche(String name,Location location){
		entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
		
		CraftPlayer cp = (CraftPlayer)Bukkit.getPlayer(name);
		
		if (!Skin.skins.containsKey(Bukkit.getPlayer(name))) {
				gameprofile = cp.getHandle().getProfile();
		}else {
				gameprofile = Skin.choosenProfiles.get(cp);
		}
		this.location = location.clone();
	}
	
	public void changeSkin(){
		String value = "eyJ0aW1lc3RhbXAiOjE0NDI4MzY1MTU1NzksInByb2ZpbGVJZCI6IjkwZWQ3YWY0NmU4YzRkNTQ4MjRkZTc0YzI1MTljNjU1IiwicHJvZmlsZU5hbWUiOiJDb25DcmFmdGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMWNlZDMzMjNmYjczMmFjMTc3MTc5Yjg5NWQ5YzJmNjFjNzczZWYxNTVlYmQ1Y2M4YzM5NTZiZjlhMDlkMTIifX19";
		String signature = "tFGNBQNpxNGvD27SN7fqh3LqNinjJJFidcdF8LTRHOdoMNXcE5ezN172BnDlRsExspE9X4z7FPglqh/b9jrLFDfQrdqX3dGm1cKjYbvOXL9BO2WIOEJLTDCgUQJC4/n/3PZHEG2mVADc4v125MFYMfjzkznkA6zbs7w6z8f7pny9eCWNXPOQklstcdc1h/LvflnR+E4TUuxCf0jVsdT5AZsUYIsJa6fvr0+vItUXUdQ3pps0zthObPEnBdLYMtNY3G6ZLGVKcSGa/KRK2D/k69fmu/uTKbjAWtniFB/sdO0VNhLuvyr/PcZVXB78l1SfBR88ZMiW6XSaVqNnSP+MEfRkxgkJWUG+aiRRLE8G5083EQ8vhIle5GxzK68ZR48IrEX/JwFjALslCLXAGR05KrtuTD3xyq2Nut12GCaooBEhb46sipWLq4AXI9IpJORLOW8+GvY+FcDwMqXYN94juDQtbJGCQo8PX670YjbmVx7+IeFjLJJTZotemXu1wiQmDmtAAmug4U5jgMYIJryXMitD7r5pEop/cw42JbCO2u0b5NB7sI/mr4OhBKEesyC5usiARzuk6e/4aJUvwQ9nsiXfeYxZz8L/mh6e8YPJMyhVkFtblbt/4jPe0bs3xSUXO9XrDyhy9INC0jlLT22QjNzrDkD8aiGAopVvfnTTAug=";
		gameprofile.getProperties().put("textures", new Property("textures", value, signature));
	}
	
	
	public void animation(int animation){
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)animation);
		sendPacket(packet);
	}
	
	public void status(int status){
		PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)status);
		sendPacket(packet);
	}
	
	public void equip(int slot,ItemStack itemstack){
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
		setValue(packet, "a", entityID);
		setValue(packet, "b", slot);
		setValue(packet, "c", itemstack);
		sendPacket(packet);
	}
	
	@SuppressWarnings("deprecation")
	public void sleep(boolean state){
		if(state){
			Location bedLocation = new Location(location.getWorld(), 1, 1, 1);
			PacketPlayOutBed packet = new PacketPlayOutBed();
			setValue(packet, "a", entityID);
			setValue(packet, "b", new BlockPosition(bedLocation.getX(),bedLocation.getY(),bedLocation.getZ()));
			
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
			}

			sendPacket(packet);
			teleport(location.clone().add(0,0.3,0));
		}else{
			animation(2);
			teleport(location.clone().subtract(0,0.3,0));
		}
	}
	
	public void spawn(Player p){
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		
		setValue(packet, "a", entityID);
		setValue(packet, "b", gameprofile.getId());
		setValue(packet, "c", getFixLocation(location.getX()));
		setValue(packet, "d", getFixLocation(location.getY()));
		setValue(packet, "e", getFixLocation(location.getZ()));
		setValue(packet, "f", getFixRotation(location.getYaw()));
		setValue(packet, "g", getFixRotation(location.getPitch()));
		setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.a(6,(float)20);
		w.a(10,(byte)127);
		setValue(packet, "i", w);
		addToTablist();
		if (p == null) {
			sendPacket(packet);
		}else {
			sendPacket(packet, p);
		}
		headRotation(location.getYaw(), location.getPitch());
	}
	
	public void teleport(Location location){
		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
		setValue(packet, "a", entityID);
		setValue(packet, "b", getFixLocation(location.getX()));
		setValue(packet, "c", getFixLocation(location.getY()));
		setValue(packet, "d", getFixLocation(location.getZ()));
		setValue(packet, "e", getFixRotation(location.getYaw()));
		setValue(packet, "f", getFixRotation(location.getPitch()));

		sendPacket(packet);
		headRotation(location.getYaw(), location.getPitch());
		this.location = location.clone();
	}
	
	public void headRotation(float yaw,float pitch){
		PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
		PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
		setValue(packetHead, "a", entityID);
		setValue(packetHead, "b", getFixRotation(yaw));
		

		sendPacket(packet);
		sendPacket(packetHead);
	}
	
	public void destroy(){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
		rmvFromTablist();
		sendPacket(packet);
	}
	
	public void addToTablist(){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public void rmvFromTablist(){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public int getFixLocation(double pos){
		return (int)MathHelper.floor(pos * 32.0D);
	}
	
	public byte getFixRotation(float yawpitch){
		return (byte) ((int) (yawpitch * 256.0F / 360.0F));
	}
	
}