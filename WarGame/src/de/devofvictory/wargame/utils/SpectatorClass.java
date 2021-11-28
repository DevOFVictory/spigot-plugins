package de.devofvictory.wargame.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.devofvictory.wargame.main.Main;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class SpectatorClass implements Listener{
	
	public static HashMap<String, String> specCamera = new HashMap<>();
	public static HashMap<String, Long> specDelay = new HashMap<>();
	
	public static void setSpectator(Player p, Player killer) {
		
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				StartGame.spectators.add(p);
				p.setGameMode(GameMode.ADVENTURE);
				
				
				p.setFoodLevel(20);
				p.setMaxHealth(20);
				p.setHealth(20);
				p.setHealthScale(20);
				
				
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (StartGame.spectators.contains(all)) {
						p.showPlayer(all);
						all.showPlayer(p);
					}else {
						all.hidePlayer(p);
						p.showPlayer(all);
					}
				}
				
				ItemStack teleporter = new ItemStack(Material.COMPASS);
				ItemMeta teleporterMeta = teleporter.getItemMeta();
				teleporterMeta.setDisplayName("§8•§7● §2§lTeleporter §7●§8•");
				teleporter.setItemMeta(teleporterMeta);
				
				ItemStack inventory = new ItemStack(Material.CHEST);
				ItemMeta inventoryMeta = inventory.getItemMeta();
				inventoryMeta.setDisplayName("§8•§7● §2§lInventar §7●§8•");
				List<String> lore = new ArrayList<>();
				lore.add("§f(Refresh) §bKlicke um das Inventar vom Spieler zu öffnen!");
				inventoryMeta.setLore(lore);
				inventory.setItemMeta(inventoryMeta);
				
				p.getInventory().setItem(22, inventory);
				p.getInventory().setItem(4, teleporter);
				
				if (killer != null && killer != p) {
					p.teleport(killer);
				}else {
					p.teleport(StartGame.middle);
				}
				p.setAllowFlight(true);
				p.setFlying(true);
				p.spigot().setCollidesWithEntities(false);
				
			}
		}, 20);
		
		
		
	}
	
	
	@EventHandler
	public void onCompassOpen(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		try {
		if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().getType() == Material.COMPASS && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §2§lTeleporter §7●§8•")) {
			if (StartGame.spectators.contains(p)) {
				
						Inventory inv = Bukkit.createInventory(null, 18, "§8•§7● §2§lTeleporter §7●§8•");
						
						for (Player all : StartGame.living) {
							ItemStack skull = new ItemStack(Material.SKULL_ITEM,1 ,(byte)3);
							SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
							skullMeta.setOwner(all.getName());
							skullMeta.setDisplayName("§c"+all.getName());
							skull.setItemMeta(skullMeta);
							
							inv.addItem(skull);
							
						}
						
						p.openInventory(inv);
			}
			
		
			}else {
				if (StartGame.spectators.contains(p))
				e.setCancelled(true);
			}
		
			
		}catch (NullPointerException ex) {
			
		}
		
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		try {
		if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §2§lInventar §7●§8•")) {
		if (StartGame.spectators.contains(p)) {

			if (specCamera.containsKey(p.getName())) {
				Inventory inv = Bukkit.createInventory(null, 5*9, "§8•§7● §2§lInventar §7●§8•");
				Player t = Bukkit.getPlayer(specCamera.get(p.getName()));
				
				
				for (int i = 0; i<9; i++) {
					ItemStack is = t.getInventory().getItem(i);
					if (is != null) {
					inv.setItem(i+36, t.getInventory().getItem(i));
					}else {
						inv.setItem(i+36, new ItemStack(Material.AIR));
					}
				}
				
				ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
				ItemMeta placeholderMeta = placeholder.getItemMeta();
				placeholderMeta.setDisplayName("§r");
				placeholder.setItemMeta(placeholderMeta);
				
				for (int i = 27; i<36; i++) {
					inv.setItem(i, placeholder);
				}
				
				for (int i = 0; i<27; i++) {
					ItemStack is = t.getInventory().getItem(i+9);
					
					if (is != null) {
						inv.setItem(i, is);
					}else {
						inv.setItem(i, new ItemStack(Material.AIR));
					}
				}
				p.openInventory(inv);
				
			}else {
				
				p.sendMessage(Main.Prefix+"§cDu beobachtest zurzeit keinen Spieler! Mache Rechtsklick auf einen Spieler, um ihn zu beobachten!");
				return;
			}
		}
		
	}
		}catch (NullPointerException ex) {
		}
	
	}
	
	
	@EventHandler
	public void onCompassClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (StartGame.spectators.contains(p)) {
			e.setCancelled(true);
			try {
			if (e.getInventory().getName().equalsIgnoreCase("§8•§7● §2§lTeleporter §7●§8•")) {
				String playername = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§c", "");
				
				if (Bukkit.getPlayer(playername) != null) {
					p.teleport(Bukkit.getPlayer(playername));
					p.playSound(p.getLocation(), Sound.CHICKEN_HURT, 10, 10);
					p.setAllowFlight(true);
					p.setFlying(true);
					p.spigot().setCollidesWithEntities(false);
				}
			}
			}catch (NullPointerException ex) {
				
			}
			
		}
		
		
	}
	
	@EventHandler
	public void onCompassDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (StartGame.spectators.contains(p) || !Main.isGameRunning) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (StartGame.spectators.contains(p)) {
			e.setCancelled(true);
		p.setAllowFlight(true);
		p.setFlying(true);
		p.spigot().setCollidesWithEntities(false);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (StartGame.spectators.contains(p)) {
			e.setCancelled(true);
			p.setAllowFlight(true);
			p.setFlying(true);
			p.spigot().setCollidesWithEntities(false);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (StartGame.spectators.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (StartGame.spectators.contains(e.getDamager())) {
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof Player) {
			if (StartGame.spectators.contains(e.getEntity()))
				e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if (StartGame.spectators.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (StartGame.spectators.contains(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
			e.getPlayer().spigot().setCollidesWithEntities(false);
		}
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		if (StartGame.spectators.contains(p)) {
			for (Player specs : StartGame.spectators) {
				specs.sendMessage(Main.Prefix+"§8•§7●§8Zuschauer §8× §7"+p.getName()+" §8» §7"+e.getMessage());
			}
		}else {
				Bukkit.broadcastMessage("§3"+p.getName()+" §8» §b"+e.getMessage());		
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			
			if (StartGame.spectators.contains(p)) {
				e.setCancelled(true);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.spigot().setCollidesWithEntities(false);
			}
		}
	}
	
	
	@EventHandler
	public void onSpectate(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if (StartGame.spectators.contains(p)) {
			
			e.setCancelled(true);
			
			if (e.getRightClicked() instanceof Player) {
				
				Player target = (Player) e.getRightClicked();
				if (!specCamera.containsKey(p.getName()) && !StartGame.spectators.contains(target)) {
					
					if (specDelay.get(p.getName()).longValue() + 15*1000 <= System.currentTimeMillis()) {
						specDelay.put(p.getName(), System.currentTimeMillis());
				
						PacketPlayOutCamera camera = new PacketPlayOutCamera();
						camera.a = target.getEntityId();
						((CraftPlayer)p).getHandle().playerConnection.sendPacket(camera);
					
					
						p.setGameMode(GameMode.SPECTATOR);
					
						specCamera.put(p.getName(), target.getName());
						p.sendMessage(Main.Prefix+"§aDu beobachtest nun §2"+target.getName()+"§a!");
						target.sendMessage(Main.Prefix+"§2"+p.getName()+" §abeobachtet dich nun!");
						p.setAllowFlight(true);
						p.setFlying(true);
						p.spigot().setCollidesWithEntities(false);
					}else {
						long time = System.currentTimeMillis();
						long last = specDelay.get(p.getName());
						long remainingSec = ((time-last-15000)/1000)/-1;
						long remainingMili = ((time-last-15000)%1000)/-1/10;
						
						p.sendMessage(Main.Prefix+"§cBitte warte noch "+remainingSec+"."+remainingMili+" Sekunden!");
					}
			}
			}
		}
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (!p.isSneaking() && StartGame.spectators.contains(p)) {
			
			if (specCamera.containsKey(p.getName())) {
			
			PacketPlayOutCamera camera = new PacketPlayOutCamera();
			camera.a = p.getEntityId();
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(camera);
			p.setGameMode(GameMode.ADVENTURE);

			p.sendMessage(Main.Prefix+"§cDu beobachtest §6"+specCamera.get(p.getName())+" §cnun nicht mehr!");
			Bukkit.getPlayer(specCamera.get(p.getName())).sendMessage(Main.Prefix+"§6"+p.getName()+" §cbeobachtet dich nun nicht mehr!");
			p.teleport(Bukkit.getPlayer(specCamera.get(p.getName())));
			specCamera.remove(p.getName());
			
			p.setAllowFlight(true);
			p.setFlying(true);
			p.spigot().setCollidesWithEntities(false);
			
			}
		}
	}


	public static void updateInventorsLoop() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {

				
				if (!specCamera.isEmpty()) {
					for (String specName : specCamera.keySet()) {
						Player spectator = Bukkit.getPlayer(specName);
						Player player = Bukkit.getPlayer(specCamera.get(specName));
						
						if (spectator != null && player != null && spectator.getOpenInventory().getTitle().equalsIgnoreCase("§8•§7● §2§lInventar §7●§8•")) {
							
							Inventory inv = Bukkit.createInventory(null, 5*9, "§8•§7● §2§lInventar §7●§8•");
							
							for (int i = 0; i<9; i++) {
								ItemStack is = player.getInventory().getItem(i);
								if (is != null) {
								inv.setItem(i+36, player.getInventory().getItem(i));
								}else {
									inv.setItem(i+36, new ItemStack(Material.AIR));
								}
							}
							
							ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
							ItemMeta placeholderMeta = placeholder.getItemMeta();
							placeholderMeta.setDisplayName("§r");
							placeholder.setItemMeta(placeholderMeta);
							
							for (int i = 27; i<36; i++) {
								inv.setItem(i, placeholder);
							}
							
							for (int i = 0; i<27; i++) {
								ItemStack is = player.getInventory().getItem(i+9);
								
								if (is != null) {
									inv.setItem(i, is);
								}else {
									inv.setItem(i, new ItemStack(Material.AIR));
								}
							}
							spectator.openInventory(inv);

						}
					}
				}
				
			
				
			}
		}, 20, 5);
	}
	
	
	
	
	

}
