package de.devofvictory.wargame.utils;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

public class NPCManager extends Reflections {

	
	int entityID;
	Location location;
	GameProfile gameprofile;
	
	
	public NPCManager(String name,Location location){
		entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
		gameprofile = new GameProfile(UUID.randomUUID(), name);
		changeSkin();
		this.location = location;
	}
	
	public void spawn(Player p){
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		
		
		setValue(packet, "a", entityID);
		setValue(packet, "b", gameprofile.getId());
		setValue(packet, "c", (int)MathHelper.floor(location.getX() * 32.0D));
		setValue(packet, "d", (int)MathHelper.floor(location.getY() * 32.0D));
		setValue(packet, "e", (int)MathHelper.floor(location.getZ() * 32.0D));
		setValue(packet, "f", (byte) ((int) (location.getYaw())));
		setValue(packet, "g", (byte) ((int) (location.getPitch())));
		setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.a(6,(float)20);
		w.a(10,(byte)127);
		setValue(packet, "i", w);
		addToTablist();
		sendPacket(packet, p);
		
		
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					rmvFromTablist();
					
				}
			},60);
		
			
		
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public void animation(int animation){
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)animation);
        sendPacket(packet);
}
	
	public void changeSkin() {
		String value = "eyJ0aW1lc3RhbXAiOjE1NTk4MjQwNzE5MDcsInByb2ZpbGVJZCI6ImEwNWRlZWMwN2EwZTQwNzY4N2NiZGM1ZGNhY2E4NTg2IiwicHJvZmlsZU5hbWUiOiJWaWxsYWdlciIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FmN2MwN2QxZGVkNjFiMWQzMzEyNjg1YjMyZTQ1NjhmZmRkYTc2MmVjOGQ4MDg4OTVjYzMyOWE5M2Q2MDZlMCJ9fX0=";
		String signature = "Lv0USGBBix2wAHiF+Zj+n07mNZm4RYGgRzXq1qAd3xA2T1QFqJVvbLf1Bp9QlRjzf22un14rP42o4U4aN3en9ydPcVn1p5S2qgGGCJBZqJhrB3Scd54lxbiYZIGNuEYcHb8qEQLMQ9ipjAT1FljB0q0ejJ5U0g13KvmSpjR9a7Deg40M21iflmcKxqGRQ32bRear+i4vYFBUR5ztM48BzI5D8+nd7UwnfC70dyjC3txtPxkh+jpD5gvjRgfywIxsunpyi+1sUUyYevH60gfANNw3s+t9SqAik+fw+7TZhb8IzFVZ/asqkFq19fIL/qTwDYBXXwKW7Uxardb4bMBRvhQqa5FCPkM2PD9Fd9dr9zngOyd+/99jypQ6dhDiO7VZPRS6A9v/XVUf+KJVYJFXLeML4m62W0h03UvhvGtkrSPHfd240EVagRu9jLBlI7VRXJXz0fiapgrCn3LZqWw2UAIj9h3y/3wHv5Cpmgtl3rB/fWbTFZcQsvjC/VzQAzc22LpLYLLUOwL8knqNdZl8Dib5gr70fuTLIVO9SwrgHsdz0u8APh41hrpviinPifBYK5U7ea2H+qYBfOKX2fW3ken6D7OM+mU8tdYZ3DtSBe3LFR+Mc+6cxiwIlcAFPRH2yF+EF0n7my6WXVPTOyO0307GsqczBH9HXyyMOZ/Krv0=";
		
		gameprofile.getProperties().put("textures", new Property("textures", value, signature));
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
	
}