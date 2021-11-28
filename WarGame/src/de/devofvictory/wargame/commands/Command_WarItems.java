package de.devofvictory.wargame.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;

public class Command_WarItems implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("waritems")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission("wargame.waritems")) {
					
					if (Code.trusted.contains(p.getName())) {
					
					Inventory inv = Bukkit.createInventory(null, 18, "§3§lWar§b§lItems");
					
					ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, (short)1, (short)7);
					ItemMeta placeholderMeta = placeholder.getItemMeta();
					placeholderMeta.setDisplayName("§r");
					placeholder.setItemMeta(placeholderMeta);
					inv.setItem(11, placeholder);
					inv.setItem(12, placeholder);
					inv.setItem(13, placeholder);
					inv.setItem(14, placeholder);
					inv.setItem(15, placeholder);
					inv.setItem(16, placeholder);
					inv.setItem(17, placeholder);
					
					
					ItemStack wurfmine = new ItemStack(Material.STONE_PLATE, 64);
					ItemMeta wurfmineMeta= wurfmine.getItemMeta();
					wurfmineMeta.setDisplayName("§6§lWurfMine");
					wurfmine.setItemMeta(wurfmineMeta);
					inv.setItem(0, wurfmine);
					
					ItemStack pistol = new ItemStack(Material.WOOD_HOE);
					ItemMeta pistolMeta = pistol.getItemMeta();
					pistolMeta.setDisplayName("§6§lPistol");
					pistol.setItemMeta(pistolMeta);
					inv.setItem(1, pistol);
					
					ItemStack laubblaeser = new ItemStack(Material.GOLD_HOE);
					ItemMeta laubblaeserMeta = laubblaeser.getItemMeta();
					laubblaeserMeta.setDisplayName("§6§lLaubBläser");
					laubblaeser.setItemMeta(laubblaeserMeta);
					inv.setItem(2, laubblaeser);
					
					ItemStack rocketlauncher = new ItemStack(Material.STONE_HOE);
					ItemMeta rocketlauncherMeta = rocketlauncher.getItemMeta();
					rocketlauncherMeta.setDisplayName("§6§lRocketLauncher");
					rocketlauncher.setItemMeta(rocketlauncherMeta);
					inv.setItem(3, rocketlauncher);
					
					ItemStack ak = new ItemStack(Material.IRON_HOE);
					ItemMeta akMeta = ak.getItemMeta();
					akMeta.setDisplayName("§6§lAK");
					ak.setItemMeta(akMeta);
					inv.setItem(4, ak);
					
					ItemStack sniper = new ItemStack(Material.DIAMOND_HOE);
					ItemMeta sniperMeta = sniper.getItemMeta();
					sniperMeta.setDisplayName("§6§lSniper");
					sniper.setItemMeta(sniperMeta);
					inv.setItem(5, sniper);
					
					ItemStack schrotflinte = new ItemStack(Material.WOOD_SPADE);
					ItemMeta schrotflinteMeta = schrotflinte.getItemMeta();
					schrotflinteMeta.setDisplayName("§6§lSchrotFlinte");
					schrotflinte.setItemMeta(schrotflinteMeta);
					inv.setItem(6, schrotflinte);
					
					ItemStack granate = new ItemStack(Material.FIREWORK_CHARGE, 64);
					ItemMeta granateMeta = granate.getItemMeta();
					granateMeta.setDisplayName("§6§lGranate");
					granate.setItemMeta(granateMeta);
					inv.setItem(7, granate);
					
					ItemStack whiteride = new ItemStack(Material.BLAZE_ROD, 1);
					ItemMeta whiterideMeta = whiteride.getItemMeta();
					whiterideMeta.setDisplayName("§6§lWhiteRide");
					whiteride.setItemMeta(whiterideMeta);
					inv.setItem(8, whiteride);
					
					ItemStack machinegun = new ItemStack(Material.STONE_SPADE, 1);
					ItemMeta machinegunMeta = sniper.getItemMeta();
					machinegunMeta.setDisplayName("§6§lMachineGun");
					machinegun.setItemMeta(machinegunMeta);
					inv.setItem(9, machinegun);
					
					ItemStack multitool = new ItemStack(Material.DIAMOND_SPADE);
					ItemMeta multitoolMeta = multitool.getItemMeta();
					multitoolMeta.setDisplayName("§6§lMultiTool");
					multitool.setItemMeta(multitoolMeta);
					inv.setItem(10, multitool);
					
					ItemStack chickenwing = new ItemStack(Material.FEATHER);
					ItemMeta chickenwingMeta = chickenwing.getItemMeta();
					chickenwingMeta.setDisplayName("§6§lChickenWing");
					chickenwing.setItemMeta(chickenwingMeta);
					inv.setItem(11, chickenwing);
					
					ItemStack schildtrank = new ItemStack(Material.POTION);
					ItemMeta schildtrankMeta = schildtrank.getItemMeta();
					schildtrankMeta.setDisplayName("§6§lSchildTrank");
					schildtrank.setItemMeta(schildtrankMeta);
					inv.setItem(12, schildtrank);
					
					ItemStack crown = new ItemStack(Material.GOLD_HELMET);
					ItemMeta crownMeta = crown.getItemMeta();
					crownMeta.setDisplayName("§6§lCrown");
					crown.setItemMeta(crownMeta);
					inv.setItem(13, crown);
					
					ItemStack jetpack = new ItemStack(Material.GOLD_CHESTPLATE);
					ItemMeta jetpackMeta = jetpack.getItemMeta();
					jetpackMeta.setDisplayName("§6§lJetPack");
					jetpack.setItemMeta(jetpackMeta);
					inv.setItem(14, jetpack);
					
					ItemStack slimeboots = new ItemStack(Material.GOLD_BOOTS);
					ItemMeta slimebootsMeta = slimeboots.getItemMeta();
					slimebootsMeta.setDisplayName("§6§lSlimeBoots");
					slimeboots.setItemMeta(slimebootsMeta);
					inv.setItem(15, slimeboots);
					
					ItemStack c4 = new ItemStack(Material.STONE_BUTTON,64);
					ItemMeta c4Meta = c4.getItemMeta();
					c4Meta.setDisplayName("§6§lC4");
					c4.setItemMeta(c4Meta);
					inv.setItem(16, c4);
					
					ItemStack superladder = new ItemStack(Material.LADDER,64);
					ItemMeta superladderMeta = superladder.getItemMeta();
					superladderMeta.setDisplayName("§e§lSuperLeiter");
					superladder.setItemMeta(superladderMeta);
					inv.setItem(17, superladder);
					
					
					
					
					
					p.openInventory(inv);
					}else {
						Code.askForCode(p);
					}
				}else {
					p.sendMessage(Main.Prefix+"§cDafür hast du keine Rechte!");
					
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cFür diesen Befehl musst du ein Spieler sein!");
			}
		}
		return false;
	}
	

}
