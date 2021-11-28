package de.devofvictory.skykitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.ItemFrameManager;
import de.devofvictory.skykitpvp.utils.StatsManager;
import de.devofvictory.skykitpvp.utils.Variables;

public class Command_Setup implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (p.hasPermission("skykitpvp.cmd.setup")) {

				if (args.length == 1) {
					
					if (args[0].equalsIgnoreCase("setspawn")) {
						Variables.spawnLocation = p.getLocation();
						p.sendMessage(Main.Prefix+"§aSpawn wurde gesetzt!");
					}else if (args[0].equalsIgnoreCase("registerframes")) {
						ItemFrameManager.registerItemFrames();
						
						if (Variables.itemFrames.size() > Variables.itemFrameAmount) {
							ItemFrameManager.spawnFirstGold();
							p.sendMessage(Main.Prefix+"§aEs wurden §2"+Variables.itemFrames.size()+" §aRahmen regristriert!");
						}else {
							p.sendMessage(Main.Prefix+"§cEs wurden nur §6"+Variables.itemFrames.size()+" §cRahmen gefunden, aber es sind mind. §6"+(Variables.itemFrameAmount+1)+" §cbenötigt!");
						}
						
					}else if (args[0].equalsIgnoreCase("reloadConfig")) {
						Main.reloadCfg();
						p.sendMessage(Main.Prefix+"§aDie Config wurde neu geladen!");
						
					}else if (args[0].equalsIgnoreCase("reloadKits")) {
						Main.reloadKits();
						p.sendMessage(Main.Prefix+"§aDie Kits wurden neu geladen!");
						
					}else if (args[0].equalsIgnoreCase("help")) {
						
						p.sendMessage(Main.Prefix+"§aAnleitung: §9https://workupload.com/file/2vHrngwvcGw");
						
					}else if (args[0].equalsIgnoreCase("reloadStats")) {
						
						StatsManager.importTheFiveBest();
						
						p.sendMessage(Main.Prefix+"§aStatistiken wurden neu geladen!");
						
					}else if (args[0].equalsIgnoreCase("setDailyChest")) {
						
						Block block = p.getTargetBlockExact(4);
						
						Variables.dailyloot_location = block.getLocation();
						
						p.sendMessage(Main.Prefix+"§aDie tägliche Belohnungskiste wurde gesetzt!");
						
					}else {
						p.sendMessage(Main.Prefix+"§cUnbekannte Parameter! Mit §6/help §cbekommst du Hilfe!");
					}
					
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("spawnNPC")) {
						
						if (args[1].equalsIgnoreCase("villager")) {
							WanderingTrader villager = (WanderingTrader) p.getWorld().spawnEntity(p.getLocation(), EntityType.WANDERING_TRADER);
							villager.setInvulnerable(true);
							villager.setAI(false);
							villager.setCustomNameVisible(true);
							villager.setCustomName(Variables.villagerName);
							villager.setSilent(true);
							villager.setCanPickupItems(false);
							villager.setPersistent(true);
							villager.setRemoveWhenFarAway(false);
							
							
							p.sendMessage(Main.Prefix+"§aDer Villager wurde erfolgreich gespawnt!");
						}else if (args[1].equalsIgnoreCase("witch")) {
							Witch witch = (Witch) p.getWorld().spawnEntity(p.getLocation(), EntityType.WITCH);
							witch.setInvulnerable(true);
							witch.setAI(false);
							witch.setCustomNameVisible(true);
							witch.setCustomName(Variables.witchName);
							witch.setSilent(true);
							witch.setCanPickupItems(false);
							witch.setPersistent(true);
							witch.setRemoveWhenFarAway(false);
							
							p.sendMessage(Main.Prefix+"§aDie Hexe wurde erfolgreich gespawnt!");
							
							
						}else {
							p.sendMessage(Main.Prefix+"§cUnbekannte Parameter! Mit §6/help §cbekommst du Hilfe!");
						}
						
					}else if (args[0].equalsIgnoreCase("removeNPC")) {
						if (args[1].equalsIgnoreCase("villager")) {
							if (!p.getNearbyEntities(5, 5, 5).isEmpty()) {
								for (Entity en : p.getNearbyEntities(5, 5, 5)) {
									if (en.getCustomName() != null && en.getCustomName().equals(Variables.villagerName)) {
										en.remove();
									}
								}
								p.sendMessage(Main.Prefix+"§aAlle §e§lKit §aVillager wurden entfernt!");
							}else {
								p.sendMessage(Main.Prefix+"§cEs befinden sich keine NPCs in deiner Nähe!");
							}
						}else if (args[1].equalsIgnoreCase("witch")) {
							if (!p.getNearbyEntities(5, 5, 5).isEmpty()) {
								for (Entity en : p.getNearbyEntities(5, 5, 5)) {
									if (en.getCustomName() != null && en.getCustomName().equals(Variables.witchName)) {
										en.remove();
									}
								}
								p.sendMessage(Main.Prefix+"§aAlle §d§lItems §aHexen wurden entfernt!");
							}else {
								p.sendMessage(Main.Prefix+"§cEs befinden sich keine NPCs in deiner Nähe!");
							}
						}else {
							p.sendMessage(Main.Prefix+"§cUnbekannte Parameter! Mit §6/help §cbekommst du Hilfe!");
						}
					}else if (args[0].equalsIgnoreCase("setskull")) {
						
						Block target = p.getTargetBlockExact(4);
						
						try {
						
						int number = Integer.parseUnsignedInt(args[1]);
						
						if (number >= 1 && number <= 5) {
						
						if (target.getType() == Material.PLAYER_WALL_HEAD) {
							
							Variables.skullLocs[number-1] = target.getLocation();
							
							StatsManager.reloadStatsWall();
							
							p.sendMessage(Main.Prefix+"§aDer Kopf für Platz Nummer "+number+" §awurde gesetzt!");
							
						}else {
							p.sendMessage(Main.Prefix+"§cDer Block, den du anguckst ist kein Kopf!");
						}
						}else {
							p.sendMessage(Main.Prefix+"§cGebe eine Zahl von 1-5 an!");
						}
						
						}catch (NumberFormatException ex) {
							p.sendMessage(Main.Prefix+"§cBitte gebe eine gültige Zahl an!");
						}
						
					}else if (args[0].equalsIgnoreCase("setsign")) {
						Block target = p.getTargetBlockExact(4);
						try {
						
						int number = Integer.parseUnsignedInt(args[1]);
						
						if (number >= 1 && number <= 5) {
						
						if (target.getType() == Material.OAK_WALL_SIGN) {
							
							Variables.signLocs[number-1] = target.getLocation();
							
							StatsManager.reloadStatsWall();
							
							p.sendMessage(Main.Prefix+"§aDas Schild für Platz Nummer "+number+" §awurde gesetzt!");
							
						}else {
							p.sendMessage(Main.Prefix+"§cDer Blöck, den du anguckst ist kein Eichen-Schild!");
						}
						}else {
							p.sendMessage(Main.Prefix+"§cGebe eine Zahl von 1-5 an!");
						}
						
						}catch (NumberFormatException ex) {
							p.sendMessage(Main.Prefix+"§cBitte gebe eine gültige Zahl an!");
						}
					}else if (args[0].equalsIgnoreCase("saveInv")) {
						
						PlayerInventory inv  = p.getInventory();
						
						Inventory inv2 = Bukkit.createInventory(null, inv.getType());
						
						inv2.setContents(inv.getContents());
						
						Variables.inventories.put(args[1], inv2);
						
						
						p.sendMessage(Main.Prefix+"§aDas Inventar wurde erfolgreich unter dem Namen §2"+args[1]+" §agespeichert!");
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
						
					}else if (args[0].equalsIgnoreCase("loadinv")) {
						
						if (Variables.inventories.containsKey(args[1])) {
							
							Inventory inv = Variables.inventories.get(args[1]);
							
							p.getInventory().setContents(inv.getContents());
							
							p.sendMessage(Main.Prefix+"§aDas Inventar mit dem Namen §2"+args[1]+" §awurde erfolgreich geladen!");
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
							
						}else {
							p.sendMessage(Main.Prefix+"§cEs konnte kein Inventar namens §4"+args[1]+" §cgefunden werden!");
							p.sendMessage(Main.Prefix+"§cVerfügbare Invetare:");
							for (String key : Variables.inventories.keySet()) {
								p.sendMessage(Main.Prefix+"§6- §e"+key);
							}
							p.sendMessage(Main.Prefix+"§cInventare kannst du mit §6/setup saveInv <Name> §cspeichern!");
						}
						
						
					}else {
						p.sendMessage(Main.Prefix+"§cUnbekannte Parameter! Mit §6/help §cbekommst du Hilfe!");
					}
				}else {
					p.sendMessage(Main.Prefix+"§cUnbekannte Parameter! Mit §6/help §cbekommst du Hilfe!");
				}

			} else {
				p.sendMessage(Main.Prefix + "§cDafür hast du keine Rechte!");
			}

		} else {
			sender.sendMessage("§cDu musst ein Spieler sein!");
		}

		return true;

	}

}
