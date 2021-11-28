package de.devofvictory.skykitpvp.superpowers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;
import de.devofvictory.skykitpvp.objects.SuperPowerExecutor;
import de.devofvictory.skykitpvp.utils.KitManager;
import de.devofvictory.skykitpvp.utils.LevelBarUtil;

public class BAT_DISGUISE implements SuperPowerExecutor, Listener{

	static ArrayList<Player> timeout = new ArrayList<Player>();
	
	public static HashMap<Player, Bat> bats = new HashMap<Player, Bat>();
	
	static HashMap<Player, ItemStack[]> inventories = new HashMap<Player, ItemStack[]>();
	
	static HashMap<Player, Double> lives = new HashMap<Player, Double>();
	@Override
	public void runSuperPower(Player p) {
		
		if (!timeout.contains(p)) {
			
		Kit kit = KitManager.getSelectedKit(p);
		
		int kitLevelInt = KitManager.getKitLevel(p, kit);
		KitLevel kitLevel = kit.getKitLevelForLevel(kitLevelInt);
		
		int cooldown = Integer.parseInt(kitLevel.getVariableValue("cooldown"));
		int duration = Integer.parseInt(kitLevel.getVariableValue("duration"));
		int flyspeed = Integer.parseInt(kitLevel.getVariableValue("flyspeed"));
		
		
		if (!bats.containsKey(p) && p.isOnGround()) {
			
			disguise(p, flyspeed);
			
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					unDisguise(p);
					
				}
			}, duration*20);
			
			
			LevelBarUtil levelBar = new LevelBarUtil(cooldown, false, null);
			
			levelBar.addPlayer(p);
			levelBar.start();
			
			p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
			
			timeout.add(p);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					timeout.remove(p);
					
				}
			}, 20*cooldown);
			
			}else {
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
			}
		}else {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
		}
		

	}
	
	@SuppressWarnings("deprecation")
	public static void disguise(Player p, int flyspeed) {
		inventories.put(p, p.getInventory().getContents());
		PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 1000000*20, 255, false, false, false);
		p.addPotionEffect(invisibility);
		p.getInventory().clear();
		
		Bat bat = p.getWorld().spawn(p.getLocation(), Bat.class);
		bat.setAI(false);
		bat.setCustomName("§0"+p.getName());
		bat.setCustomNameVisible(true);
		bats.put(p, bat);
		
		p.setAllowFlight(true);
		p.setFlying(true);
		p.setFlySpeed((float)flyspeed/10);
		
		bat.setInvulnerable(true);
		bat.setAwake(true);
		
		
		ItemStack fangzahn = new ItemStack(Material.GHAST_TEAR);
		ItemMeta fangzahnMeta = fangzahn.getItemMeta();
		fangzahnMeta.setDisplayName("§f§lFangzahn");
		fangzahn.setItemMeta(fangzahnMeta);
		fangzahn.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
		
		lives.put(p, p.getHealth());
		
		p.getInventory().setItem(0, fangzahn);
		
		p.setMaxHealth(2);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void unDisguise(Player p) {
		
		Bat bat = bats.get(p);
		
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		bat.remove();
		p.getInventory().clear();
		p.getInventory().setContents(inventories.get(p));
		p.setFlying(false);
		p.setAllowFlight(false);
		p.setFlySpeed(0.1F);
		
		p.setMaxHealth(20);
		p.setHealth(lives.get(p));
		
		bats.remove(p);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (bats.containsKey(p)) {
			Bat bat = bats.get(p);
			
			bat.teleport(p);
		}
	}
	
	public void onDeath(PlayerDeathEvent e) {
		if (bats.containsKey(e.getEntity())) {
			unDisguise(e.getEntity());
		}
	}
	
}
