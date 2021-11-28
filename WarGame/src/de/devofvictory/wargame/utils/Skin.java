package de.devofvictory.wargame.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Skin implements Listener{
	
	public static HashMap<Player, String> skins = new HashMap<>();
	
	
	public static void changeSkin(CraftPlayer cp, String targetName) {
		
		GameProfile skingp = cp.getProfile();
		
		try {
			skingp = GameProfileBuilder.fetch(UUIDFetcher.getUUID(targetName));
		} catch (IOException e) {
			cp.sendMessage(Main.Prefix+"§cDer Skin konnte nicht geladen werden.");
			e.printStackTrace();
			return;
		}
		
		Collection<Property> props = skingp.getProperties().get("textures");
		cp.getProfile().getProperties().removeAll("textures");
		cp.getProfile().getProperties().putAll("textures",props);
		cp.sendMessage(Main.Prefix+"§aSkin geändert zu §2"+targetName);
		
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(cp.getEntityId());
		sendPacket(destroy);
		
		PacketPlayOutPlayerInfo tabremove = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
		sendPacket(tabremove);
		
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				PacketPlayOutPlayerInfo tabadd = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
				sendPacket(tabadd);
				
				PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
				
				for (Player all : Bukkit.getOnlinePlayers()) {
					if ((CraftPlayer)all != cp)
						((CraftPlayer)all).getHandle().playerConnection.sendPacket(spawn);
				}
				
			}
		}.runTaskLater(Main.getInstance(), 4);
		
		
	}
	
	public static HashMap<Player, GameProfile> choosenProfiles = new HashMap<>();
	
	
	public static void changeAllSkins() {
		
		for (Player p : skins.keySet()) {
			PermissionsEx.getUser(p).addPermission("Betternick.Skin");
			p.performCommand("skin "+skins.get(p));
			PermissionsEx.getUser(p).removePermission("Betternick.Skin");
		}
//		
//		GameProfile skingp = cp.getProfile();
//		
//		try {
//			skingp = GameProfileBuilder.fetch(UUIDFetcher.getUUID(skins.get(cp)));
//			
//			choosenProfiles.put(cp, skingp);
//			
//		} catch (IOException e) {
//			cp.sendMessage(Main.Prefix+"§cDer Skin konnte nicht geladen werden. Bitte versuche es in der nachsten Runde erneut!");
//			return;
//		}
//		
//		Collection<Property> props = skingp.getProperties().get("textures");
//		cp.getProfile().getProperties().removeAll("textures");
//		cp.getProfile().getProperties().putAll("textures",props);
//		
//		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(cp.getEntityId());
//		sendPacket(destroy);
//		
//		PacketPlayOutPlayerInfo tabremove = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
//		sendPacket(tabremove);
//		
//		
//		
//		new BukkitRunnable() {
//			
//			@Override
//			public void run() {
//				PacketPlayOutPlayerInfo tabadd = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
//				sendPacket(tabadd);
//				
//				PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
//				
//				for (Player all : Bukkit.getOnlinePlayers()) {
//					if ((CraftPlayer)all != cp)
//						((CraftPlayer)all).getHandle().playerConnection.sendPacket(spawn);
//				}
//				
//			}
//		}.runTaskLater(Main.getInstance(), 4);
//		
//		
//		}
		
		
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void sendPacket(Packet packet) {
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
		}
		
	}
	
	public static void callInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§cSkins");
		
		
		
		for (String name : SpindManager.getSkins(p.getUniqueId())) {
			ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setDisplayName("§e"+name);
			skull.setItemMeta(skullMeta);
			inv.addItem(skull);
		}
		
		p.openInventory(inv);
	}
	
	
	@EventHandler
	public void onSkinChange(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		try {
			if (e.getClickedInventory().getTitle().equalsIgnoreCase("§cSkins")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§e")) {
					String name = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§e", "");
					
					if (SpindManager.playerSkins.get(p.getUniqueId()).contains(name)) {
						
//						p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10, 10);
//						p.sendMessage(Main.Prefix+"§cDiese Funktion ist vorübergehend gesperrt!");
//						p.closeInventory();
						
						skins.put(p, name);
						
						p.sendMessage(Main.Prefix+"§aDu wirst mit dem Skin von "+name+" spielen!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
						p.closeInventory();
						
					}else {
						p.sendMessage(Main.Prefix+"§cDiesen Skin besitzt du nicht!");
					}
					
				}
			}
		}catch (NullPointerException ex) {
			// TODO: handle exception
		}
	}

}
