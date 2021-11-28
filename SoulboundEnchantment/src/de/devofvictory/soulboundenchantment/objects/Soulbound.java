package de.devofvictory.soulboundenchantment.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.soulboundenchantment.listeners.Listener_OnRespawn;
import de.devofvictory.soulboundenchantment.utils.OtherUtils;
import de.devofvictory.soulboundenchantment.utils.RomanNumber;

public class Soulbound extends Enchantment implements Listener {

	public Soulbound(NamespacedKey id) {
		super(id);
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			List<ItemStack> list = new ArrayList<ItemStack>();

			for (int c = 0; c < e.getDrops().size(); c++) {
				ItemStack is = e.getDrops().get(c);
				if (is.hasItemMeta() && is.getItemMeta().hasEnchant(this)) {
					e.getDrops().remove(is);
					int lvl = is.getItemMeta().getEnchantLevel(this);

					if (lvl > 1) {
						ItemStack copy = new ItemStack(is);
						ItemMeta copyMeta = copy.getItemMeta();
						copyMeta.removeEnchant(this);
						copyMeta.addEnchant(this, lvl - 1, true);

						List<String> lore = new ArrayList<String>();
						lore.addAll(is.getItemMeta().getLore());

						for (int i = 0; i < lore.size(); i++) {
							if (lore.get(i).startsWith("§7Soulbound ")) {
								String owner = OtherUtils.getNameFromLore(lore.get(i));
								lore.remove(i);
								lore.add(i, "§7" + this.getName() + " " + RomanNumber.toRoman(lvl - 1) + " ("+owner+")");
							}
						}
						copyMeta.setLore(lore);
						copy.setItemMeta(copyMeta);

						list.add(copy);
					}
				}
			}
			Listener_OnRespawn.playerItems.put(p, list);
		}
	}
	
	@Override
	public NamespacedKey getKey() {
		// TODO Auto-generated method stub
		return super.getKey();
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Soulbound";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isCursed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isTreasure() {
		// TODO Auto-generated method stub
		return false;
	}

}
