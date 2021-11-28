package de.devofvictory.wargame.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;

public class Code implements Listener{
	
	public static ArrayList<String> trusted = new ArrayList<>();
	
	public static void askForCode(Player p) {

		p.openInventory(getInv("§4Enter Code: "));

	}
	

	@EventHandler
	public void onCodeType(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory clickedInv = e.getClickedInventory();
		try {
			if (e.getClickedInventory().getTitle().startsWith("§4Enter Code: ")) {
				e.setCancelled(true);
				String input = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§7", "").replaceAll("§r", "");
				
				
				
				if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") || input.equalsIgnoreCase("3") || input.equalsIgnoreCase("4") || input.equalsIgnoreCase("5") || input.equalsIgnoreCase("6") || input.equalsIgnoreCase("7") || input.equalsIgnoreCase("8") || input.equalsIgnoreCase("9") || input.equalsIgnoreCase("*") || input.equalsIgnoreCase("#")) {
					
					
					
					if ((clickedInv.getName()+input).length() <= 14+6) {
						p.openInventory(getInv(clickedInv.getName()+input));
						return;
					}
					
				}else {
					if (input.equalsIgnoreCase("§a§lBestätigen")) {
						String tryed = e.getClickedInventory().getTitle().replaceAll("§4Enter Code: ", "");
						if (tryed.equalsIgnoreCase(Main.code)) {
							trusted.add(p.getName());
							p.sendMessage(Main.Prefix+"§aAccess allowed!");
						}else {
							p.sendMessage(Main.Prefix+"§cAccess denied!");
						}
						p.closeInventory();
						return;
					}else if (input.equalsIgnoreCase("§4§lAbbrechen")) {
						p.closeInventory();
						return;
					}else if (input.equalsIgnoreCase("§c§l<--")) {
						try {
							String before = clickedInv.getTitle().replaceAll("§4Enter Code: ", "");
							p.openInventory(getInv("§4Enter Code: "+removeLastChar(before)));
						}catch (StringIndexOutOfBoundsException ex) {
						}
					}else if (input.equalsIgnoreCase("§4§lEingabe löschen")) {
						p.openInventory(getInv("§4Enter Code: "));
						return;
					}
				}
				
				
				
				
				
			}
		}catch (NullPointerException ex) {
			
		}
	}
	
	

	static Inventory getInv(String title) {
		
		Inventory inv = Bukkit.createInventory(null, 4*9, title);

		
		ItemStack placeholder1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
		ItemMeta placeholder1Meta = placeholder1.getItemMeta();
		placeholder1Meta.setDisplayName("§r");
		placeholder1.setItemMeta(placeholder1Meta);
		inv.setItem(8, placeholder1);
		
		for (int i = 0; i<4*9; i++) {
			inv.setItem(i, placeholder1);
		}
		
		ItemStack one = new ItemStack(Material.STONE_BUTTON);
		ItemMeta oneMeta = one.getItemMeta();
		oneMeta.setDisplayName("§71");
		one.setItemMeta(oneMeta);
		inv.setItem(3, one);
		
		ItemStack two = new ItemStack(Material.STONE_BUTTON);
		ItemMeta twoMeta = two.getItemMeta();
		twoMeta.setDisplayName("§72");
		two.setItemMeta(twoMeta);
		inv.setItem(4, two);
		
		ItemStack three = new ItemStack(Material.STONE_BUTTON);
		ItemMeta threeMeta = three.getItemMeta();
		threeMeta.setDisplayName("§73");
		three.setItemMeta(threeMeta);
		inv.setItem(5, three);
		
		ItemStack four = new ItemStack(Material.STONE_BUTTON);
		ItemMeta fourMeta = four.getItemMeta();
		fourMeta.setDisplayName("§74");
		four.setItemMeta(fourMeta);
		inv.setItem(12, four);
		
		ItemStack five = new ItemStack(Material.STONE_BUTTON);
		ItemMeta fiveMeta = five.getItemMeta();
		fiveMeta.setDisplayName("§75");
		five.setItemMeta(fiveMeta);
		inv.setItem(13, five);
		
		ItemStack six = new ItemStack(Material.STONE_BUTTON);
		ItemMeta sixMeta = six.getItemMeta();
		sixMeta.setDisplayName("§76");
		six.setItemMeta(sixMeta);
		inv.setItem(14, six);
		
		ItemStack seven = new ItemStack(Material.STONE_BUTTON);
		ItemMeta sevenMeta = seven.getItemMeta();
		sevenMeta.setDisplayName("§77");
		seven.setItemMeta(sevenMeta);
		inv.setItem(21, seven);
		
		ItemStack eight = new ItemStack(Material.STONE_BUTTON);
		ItemMeta eightMeta = eight.getItemMeta();
		eightMeta.setDisplayName("§78");
		eight.setItemMeta(eightMeta);
		inv.setItem(22, eight);
		
		ItemStack nine = new ItemStack(Material.STONE_BUTTON);
		ItemMeta nineMeta = nine.getItemMeta();
		nineMeta.setDisplayName("§79");
		nine.setItemMeta(nineMeta);
		inv.setItem(23, nine);
		
		ItemStack hashtag = new ItemStack(Material.STONE_BUTTON);
		ItemMeta hashtagMeta = hashtag.getItemMeta();
		hashtagMeta.setDisplayName("§7#");
		hashtag.setItemMeta(hashtagMeta);
		inv.setItem(32, hashtag);
		
		ItemStack zero = new ItemStack(Material.STONE_BUTTON);
		ItemMeta zeroMeta = zero.getItemMeta();
		zeroMeta.setDisplayName("§70");
		zero.setItemMeta(zeroMeta);
		inv.setItem(31, zero);
		
		ItemStack star = new ItemStack(Material.STONE_BUTTON);
		ItemMeta starMeta = star.getItemMeta();
		starMeta.setDisplayName("§7*");
		star.setItemMeta(starMeta);
		inv.setItem(30, star);
		
		
		
		ItemStack placeholder2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15);
		ItemMeta placeholder2Meta = placeholder2.getItemMeta();
		placeholder2Meta.setDisplayName("§r");
		placeholder2.setItemMeta(placeholder2Meta);
		inv.setItem(2, placeholder2);
		inv.setItem(6, placeholder2);
		inv.setItem(11, placeholder2);
		inv.setItem(15, placeholder2);
		inv.setItem(20, placeholder2);
		inv.setItem(24, placeholder2);
		inv.setItem(29, placeholder2);
		inv.setItem(33, placeholder2);
//		
		ItemStack confirm = new ItemStack(Material.WOOL, 1, (byte)5);
		ItemMeta confirmMeta = confirm.getItemMeta();
		confirmMeta.setDisplayName("§a§lBestätigen");
		confirm.setItemMeta(confirmMeta);
		inv.setItem(1, confirm);
		
		ItemStack cancel = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName("§4§lAbbrechen");
		cancel.setItemMeta(cancelMeta);
		inv.setItem(28, cancel);
//		
		ItemStack backspace = new ItemStack(Material.ARROW, 1);
		ItemMeta backspaceMeta = backspace.getItemMeta();
		backspaceMeta.setDisplayName("§c§l<--");
		backspace.setItemMeta(backspaceMeta);
		inv.setItem(7, backspace);
		
		ItemStack clear = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta clearMeta = clear.getItemMeta();
		clearMeta.setDisplayName("§4§lEingabe löschen");
		clear.setItemMeta(clearMeta);
		inv.setItem(34, clear);
		
		return inv;
		
	}
	
	private static String removeLastChar(String str) {
	    return str.substring(0, str.length() - 1);
	}

}
