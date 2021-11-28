package de.devofvictory.wargame.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;

public class PacketReader {

	Player player;
	Channel channel;
	
	public PacketReader(Player player) {
		this.player = player;
	}
	
	public void inject(){
		CraftPlayer cPlayer = (CraftPlayer)this.player;
		channel = cPlayer.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
	}
	
	public void uninject(){
		if(channel.pipeline().get("PacketInjector") != null){
			channel.pipeline().remove("PacketInjector");
		}
	}
	

	public void readPacket(Packet<?> packet){
		if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
			int id = (Integer)getValue(packet, "a");
			
			
			
			if(Main.npc.getEntityID() == id){
				if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
					if (StartWartePhase.count == 14) {
						Main.npc.animation(1);
						player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 1, 1);
						player.sendMessage(Main.Prefix+ "§e§lHändler §f» §7Aua! Ich lasse mich nicht erpressen, "+player.getName()+"! §8§o[EasterEgg]");
					}
				}else if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
					Inventory inv = Bukkit.createInventory(null, 9, "§e§lHändler");
					
					player.openInventory(inv);
					ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte)7);
					ItemMeta placeholderMeta = placeholder.getItemMeta();
					placeholderMeta.setDisplayName("§r");
					placeholder.setItemMeta(placeholderMeta);
					
					for (int i = 0; i<9; i++) {
						inv.setItem(i, placeholder);
					}
					
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
				}
			}
			
		}
	}
	

	public void setValue(Object obj,String name,Object value){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(obj, value);
		}catch(Exception e){}
	}
	
	public Object getValue(Object obj,String name){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(obj);
		}catch(Exception e){}
		return null;
	}
	
}