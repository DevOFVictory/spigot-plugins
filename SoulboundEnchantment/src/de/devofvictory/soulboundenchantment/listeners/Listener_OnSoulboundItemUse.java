package de.devofvictory.soulboundenchantment.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.devofvictory.soulboundenchantment.utils.OtherUtils;
import de.devofvictory.soulboundenchantment.utils.armorequipevent.ArmorEquipEvent;

public class Listener_OnSoulboundItemUse implements Listener{
	
	@EventHandler
	public void onArmorEpuip(ArmorEquipEvent e) {
		Player p = e.getPlayer();
		if (!OtherUtils.canUse(p, e.getNewArmorPiece())) {
			e.setCancelled(true);
			punishPlayer(p);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		Player p = e.getPlayer();
		if (!OtherUtils.canUse(p, e.getItem())) {
			e.setCancelled(true);
			punishPlayer(p);
		}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (!OtherUtils.canUse(p, p.getItemInHand())) {
			e.setCancelled(true);
			punishPlayer(p);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if (!OtherUtils.canUse(p, p.getItemInHand())) {
				e.setCancelled(true);
				punishPlayer(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void punishPlayer(Player p) {
		p.playSound(p.getLocation(), Sound.ENCHANT_THORNS_HIT, 1, 1);
		p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 1*20, 3, false, false, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1*20, 2, false, false, false));
		p.sendTitle("§4§lOutch!", "§cThis item is soulbound!");
	}

}
