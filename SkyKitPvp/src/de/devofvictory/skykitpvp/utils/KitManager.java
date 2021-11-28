package de.devofvictory.skykitpvp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.skykitpvp.objects.Kit;
import de.devofvictory.skykitpvp.objects.KitLevel;

public class KitManager {

	private static HashMap<UUID, Kit> choosedKit = new HashMap<>();
	private static HashMap<UUID, ArrayList<Kit>> playerKits = new HashMap<UUID, ArrayList<Kit>>();

	private static HashMap<UUID, HashMap<Kit, Integer>> kitLevels = new HashMap<UUID, HashMap<Kit, Integer>>();
	private static HashMap<UUID, HashMap<Kit, Integer>> kitKills = new HashMap<UUID, HashMap<Kit, Integer>>();

	public static Kit getSelectedKit(Player p) {
		if (choosedKit.containsKey(p.getUniqueId())) {
			return choosedKit.get(p.getUniqueId());
		} else {
			return null;
		}
	}

	public static void setKitLevel(Player p, Kit kit, int level) {
		if (kitLevels.containsKey(p.getUniqueId())) {
			kitLevels.get(p.getUniqueId()).put(kit, level);
		} else {
			HashMap<Kit, Integer> kitLevelHashMap = new HashMap<Kit, Integer>();
			kitLevelHashMap.put(kit, level);
			kitLevels.put(p.getUniqueId(), kitLevelHashMap);
		}
	}

	public static int getKitLevel(Player p, Kit kit) {
		if (kitLevels.containsKey(p.getUniqueId())) {
			if (kitLevels.get(p.getUniqueId()).containsKey(kit)) {
				return kitLevels.get(p.getUniqueId()).get(kit);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static void setKitKills(Player p, Kit kit, int kills) {
		if (kitKills.containsKey(p.getUniqueId())) {
			kitKills.get(p.getUniqueId()).put(kit, kills);
		} else {
			HashMap<Kit, Integer> kitKillsHashMap = new HashMap<Kit, Integer>();
			kitKillsHashMap.put(kit, kills);
			kitKills.put(p.getUniqueId(), kitKillsHashMap);
		}
	}

	public static int getKitKills(Player p, Kit kit) {
		if (kitKills.containsKey(p.getUniqueId())) {
			if (kitKills.get(p.getUniqueId()).containsKey(kit)) {
				return kitKills.get(p.getUniqueId()).get(kit);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static void addKitKill(Player p, Kit kit) {
		setKitKills(p, kit, getKitKills(p, kit) + 1);
	}

	public static void levelUp(Player p, Kit kit) {
		setKitLevel(p, kit, getKitLevel(p, kit) + 1);
	}

	public static void setKit(Player p, Kit kit) {
		if (hasKit(p, kit)) {
			choosedKit.put(p.getUniqueId(), kit);
			giveKitItems(p);
		}
	}
	
	public static void setKitForced(Player p, Kit kit) {
		choosedKit.put(p.getUniqueId(), kit);
		giveKitItemsForced(p);
		
	}

	public static boolean hasKitSelected(Player p) {
		return getSelectedKit(p) != null;
	}

	public static void registerKit(Kit kit) {
		if (!Variables.regeisterdKits.contains(kit)) {
			Variables.regeisterdKits.add(kit);
		}
	}

	public static void unregisterAllKits() {
		Variables.regeisterdKits.clear();
	}

	public static void unregisterKit(Kit kit) {
		if (Variables.regeisterdKits.contains(kit)) {
			Variables.regeisterdKits.remove(kit);
		}
	}

	public static ArrayList<Kit> getRegisteredKits() {
		return Variables.regeisterdKits;
	}

	public static void giveKitItems(Player p) {
		p.getInventory().clear();
		for (ItemStack is : p.getInventory().getArmorContents()) {
			p.getInventory().remove(is);
		}

		Kit kit = getSelectedKit(p);
		KitLevel level = kit.getKitLevelForLevel(getKitLevel(p, kit));
		Inventory inv = Variables.inventories.get(level.getInventoryName());

		p.getInventory().setContents(inv.getContents());

		ItemStack perform = new ItemStack(getSelectedKit(p).getDisplayType());
		ItemMeta performMeta = perform.getItemMeta();
		performMeta.setDisplayName("§2§lSuperkraft auslösen §7(Rechts Klick)");
		perform.setItemMeta(performMeta);

		p.getInventory().setItem(2, perform);
	}
	
	public static void giveKitItemsForced(Player p) {
		p.getInventory().clear();
		for (ItemStack is : p.getInventory().getArmorContents()) {
			p.getInventory().remove(is);
		}

		Kit kit = getSelectedKit(p);
		KitLevel level = kit.getKitLevelForLevel(1);
		Inventory inv = Variables.inventories.get(level.getInventoryName());

		p.getInventory().setContents(inv.getContents());

		ItemStack perform = new ItemStack(getSelectedKit(p).getDisplayType());
		ItemMeta performMeta = perform.getItemMeta();
		performMeta.setDisplayName("§2§lSuperkraft auslösen §7(Rechts Klick)");
		perform.setItemMeta(performMeta);

		p.getInventory().setItem(2, perform);
	}

	public static void addKit(Player p, Kit kit) {
		if (!hasKit(p, kit)) {
			playerKits.get(p.getUniqueId()).add(kit);
			setKitLevel(p, kit, 1);

		} else {
			ArrayList<Kit> kits = new ArrayList<Kit>();
			kits.add(kit);
			playerKits.put(p.getUniqueId(), kits);
		}
	}

	public static boolean hasKit(Player p, Kit kit) {
		if (playerKits.containsKey(p.getUniqueId())) {
			if (playerKits.get(p.getUniqueId()).contains(kit)) {
				return true;
			} else {
				return false;
			}
		} else {
			ArrayList<Kit> kits = new ArrayList<Kit>();
			playerKits.put(p.getUniqueId(), kits);
			return false;
		}
	}

	public static ArrayList<Kit> getBoughtKits(Player p) {
		return playerKits.containsKey(p.getUniqueId()) ? playerKits.get(p.getUniqueId()) : new ArrayList<Kit>();
	}

	public static Kit getKitByDispName(String dispName) {
		for (Kit kit : Variables.regeisterdKits) {
			if (kit.getDisplayName().equals(dispName)) {
				return kit;
			}
		}
		return null;
	}

	public static Kit getKitByUniqueName(String uniqueName) {
		for (Kit kit : Variables.regeisterdKits) {
			if (kit.getUniqueName().equals(uniqueName)) {
				return kit;
			}
		}
		return null;
	}

}
