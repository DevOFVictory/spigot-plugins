package de.devofvictory.wargame.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.wargame.main.Main;

public class Vote implements Listener{
	
	public static HashMap<String, Integer> mapVote = new HashMap<>();
	public static HashMap<Integer, Integer> livesVote = new HashMap<>();
	public static HashMap<String, Integer> timeVote = new HashMap<>();
	
	static ArrayList<String> mapVoted = new ArrayList<>();
	static ArrayList<String> livesVoted = new ArrayList<>();
	static ArrayList<String> timeVoted = new ArrayList<>();
	
	public static String votedTime;
	public static int votedLives;
	public static String votedMap;
	

	
	private static void generateRandomTime() {
		Random random = new Random();
		int r = random.nextInt(2);
		
		if (r == 0) {
			votedTime = "Tag";
		}else if (r == 1) {
			votedTime = "Nacht";
		}
	}
	
	static void generateRandomLives() {
		Random random = new Random();
		int r = random.nextInt(3);
		
		if (r == 0) {
			votedLives = 1;
		}else if (r == 1) {
			votedLives = 3;
		}else if (r == 2) {
			votedLives = 5;
		}
	}
	
	static void generateRandomLivesBetween(int a, int b) {
		Random random = new Random();
		int r = random.nextInt(2);
		
		if (r == 0) {
			votedLives = a;
		}else {
			votedLives = b;
		}
	}
	
	public static void setResult() {
			votedTime = Collections.max(Vote.timeVote.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			int votesForTime = timeVote.get(votedTime);
			
			if (votedTime == "Tag") {
				if (votesForTime == timeVote.get("Nacht")) {
					generateRandomTime();
				}
			}else if (votedTime == "Nacht") {
				if (votesForTime == timeVote.get("Tag")) {
					generateRandomTime();
				}
			}
			
			LinkedHashMap<Integer, Integer> sortedivesVoteMap = new LinkedHashMap<>();
			
			livesVote.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> sortedivesVoteMap.put(x.getKey(), x.getValue()));
			
			if ((Integer) sortedivesVoteMap.keySet().toArray()[0] == (Integer) sortedivesVoteMap.keySet().toArray()[1]) {
				if ((Integer) sortedivesVoteMap.keySet().toArray()[1] == (Integer) sortedivesVoteMap.keySet().toArray()[2]) {
					generateRandomLives();
				}else {
					generateRandomLivesBetween((Integer) sortedivesVoteMap.keySet().toArray()[0], (Integer) sortedivesVoteMap.keySet().toArray()[1]);
				}
			}else {
				votedLives = (Integer) sortedivesVoteMap.keySet().toArray()[0];
			}
			
			
	}
	
	
	
	@EventHandler
	public void onOpenVote(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			try {
				if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lVoten")) {
					callVoteInv(e.getPlayer());
					playClick(e.getPlayer());
				}
			}catch (NullPointerException ex) {
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			try {
				if (e.getInventory().getTitle().equalsIgnoreCase("§d§lVoten")) {
					
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lTageszeit")) {
						e.setCancelled(true);
						callTimeInv(p);
						playClick(p);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lKarte")) {
						callMapInv(p);
						playClick(p);
					}else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lLeben")) {
						callLivesInv(p);
						playClick(p);
						
					}
				}else {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Zurück")) {
						callVoteInv(p);
						playClick(p);
						return;
					}
					
					
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					
					if (e.getInventory().getTitle().equalsIgnoreCase("§d§lTageszeit")) {
						if (name.equalsIgnoreCase("§b§lTag")) {
							if (!timeVoted.contains(p.getName())) {
								timeVoted.add(p.getName());
									timeVote.put("Tag", timeVote.get("Tag")+1);
									p.sendMessage(Main.Prefix+"§aTageszeit erfolgreich für Tag abgestimmt!");
									playVoted(p);
									
							}else {
								p.sendMessage(Main.Prefix+"§cDu hast bereits für diese Kategorie abgestimmt!");
							}
						}else if (name.equalsIgnoreCase("§8§lNacht")) {
							if (!timeVoted.contains(p.getName())) {
								timeVoted.add(p.getName());
									timeVote.put("Nacht", timeVote.get("Nacht")+1);
									p.sendMessage(Main.Prefix+"§aTageszeit erfolgreich für Nacht abgestimmt!");
									playVoted(p);
									
								
							}else {
								p.sendMessage(Main.Prefix+"§cDu hast bereits für diese Kategorie abgestimmt!");
							}
						}
						
					}else if (e.getInventory().getTitle().equalsIgnoreCase("§d§lKarte")) {
						if (name.equalsIgnoreCase("§a§lTiltedTowers")) {
							p.sendMessage(Main.Prefix+"§cIn dieser Kategorie kann man noch nicht abstimmen!");
						}
					}else if (e.getInventory().getTitle().equalsIgnoreCase("§d§lLeben")) {
						System.out.println("test");
						if (name.equalsIgnoreCase("§4§l1")) {
							System.out.println("test2");
							if (!livesVoted.contains(p.getName())) {
								System.out.println("test3");
								livesVoted.add(p.getName());
								System.out.println("test4");
									livesVote.put(1, livesVote.get(1)+1);
									System.out.println("test5");
									p.sendMessage(Main.Prefix+"§aLebenanzahl erfolgreich für 1 abgestimmt!");
									playVoted(p);
									
							}else {
								p.sendMessage(Main.Prefix+"§cDu hast bereits für diese Kategorie abgestimmt!");
							}
							
							
						}else if (name.equalsIgnoreCase("§4§l3")) {
							if (!livesVoted.contains(p.getName())) {
								livesVoted.add(p.getName());
									livesVote.put(3, livesVote.get(3)+1);
									p.sendMessage(Main.Prefix+"§aLebenanzahl erfolgreich für 3 abgestimmt!");
									playVoted(p);
									
							}else {
								p.sendMessage(Main.Prefix+"§cDu hast bereits für diese Kategorie abgestimmt!");
							}
						}else if (name.equalsIgnoreCase("§4§l5")) {
							if (!livesVoted.contains(p.getName())) {
								livesVoted.add(p.getName());
									livesVote.put(5, livesVote.get(5)+1);
									p.sendMessage(Main.Prefix+"§aLebenanzahl erfolgreich für 5 abgestimmt!");
									playVoted(p);
									
							}else {
								p.sendMessage(Main.Prefix+"§cDu hast bereits für diese Kategorie abgestimmt!");
							}
						}
					}
					
				}
			}catch (NullPointerException ex) {
				
			}
		}
	}
	
	static void playClick(Player p) {
		p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
	}
	
	static void playVoted(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
	}
	
	public static void callVoteInv(Player p) {
		
		Inventory vote = Bukkit.createInventory(null, 3*9, "§d§lVoten");
		
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§r");
		placeholder.setItemMeta(placeholderMeta);
		
		for (int i = 0; i<27; i++) {
			vote.setItem(i, placeholder);
		}
		
		ItemStack time = new ItemStack(Material.WATCH);
		ItemMeta timeMeta = time.getItemMeta();
		timeMeta.setDisplayName("§d§lTageszeit");
		time.setItemMeta(timeMeta);
		vote.setItem(11, time);
		
		ItemStack map = new ItemStack(Material.GRASS);
		ItemMeta mapMeta = map.getItemMeta();
		mapMeta.setDisplayName("§d§lKarte");
		map.setItemMeta(mapMeta);
		vote.setItem(13, map);
		
		ItemStack lives = new ItemStack(Material.RED_ROSE);
		ItemMeta livesMeta = lives.getItemMeta();
		livesMeta.setDisplayName("§d§lLeben");
		lives.setItemMeta(livesMeta);
		vote.setItem(15, lives);
		
		
		p.openInventory(vote);
		
	}
	
	public static void callTimeInv(Player p) {
		
		Inventory time = Bukkit.createInventory(null, 3*9, "§d§lTageszeit");
		
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§r");
		placeholder.setItemMeta(placeholderMeta);
		
		for (int i = 0; i<27; i++) {
			time.setItem(i, placeholder);
		}
		
		ItemStack Tag = new ItemStack(Material.WOOL,1,(byte)3);
		ItemMeta TagMeta = Tag.getItemMeta();
		TagMeta.setDisplayName("§b§lTag");
		Tag.setItemMeta(TagMeta);
		time.setItem(12, Tag);
		
		ItemStack Nacht = new ItemStack(Material.WOOL,1,(byte)7);
		ItemMeta NachtMeta = Nacht.getItemMeta();
		NachtMeta.setDisplayName("§8§lNacht");
		Nacht.setItemMeta(NachtMeta);
		time.setItem(14, Nacht);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName("§4Zurück");
		back.setItemMeta(backMeta);
		time.setItem(18, back);
		
		p.openInventory(time);
		
	}
	
	public static void callMapInv(Player p) {
		
		Inventory map = Bukkit.createInventory(null, 3*9, "§d§lKarte");
		
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§r");
		placeholder.setItemMeta(placeholderMeta);
		
		for (int i = 0; i<27; i++) {
			map.setItem(i, placeholder);
		}
		
		ItemStack tilted = new ItemStack(Material.STONE,1,(byte)3);
		ItemMeta tiltedMeta = tilted.getItemMeta();
		tiltedMeta.setDisplayName("§a§lTiltedTowers");
		tilted.setItemMeta(tiltedMeta);
		map.setItem(13, tilted);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName("§4Zurück");
		back.setItemMeta(backMeta);
		map.setItem(18, back);
		
		
		p.openInventory(map);
		
	}
	
	public static void callLivesInv(Player p) {
		
		Inventory livesinv = Bukkit.createInventory(null, 3*9, "§d§lLeben");
		
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§r");
		placeholder.setItemMeta(placeholderMeta);
		
		for (int i = 0; i<27; i++) {
			livesinv.setItem(i, placeholder);
		}
		
		ItemStack one = new ItemStack(Material.RED_MUSHROOM);
		ItemMeta oneMeta = one.getItemMeta();
		oneMeta.setDisplayName("§4§l1");
		one.setItemMeta(oneMeta);
		livesinv.setItem(11, one);
		
		ItemStack three = new ItemStack(Material.RED_MUSHROOM);
		ItemMeta threeMeta = three.getItemMeta();
		threeMeta.setDisplayName("§4§l3");
		three.setItemMeta(threeMeta);
		livesinv.setItem(13, three);
		
		ItemStack five = new ItemStack(Material.RED_MUSHROOM);
		ItemMeta fiveMeta = five.getItemMeta();
		fiveMeta.setDisplayName("§4§l5");
		five.setItemMeta(fiveMeta);
		livesinv.setItem(15, five);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName("§4Zurück");
		back.setItemMeta(backMeta);
		livesinv.setItem(18, back);
		
		p.openInventory(livesinv);
		
	}
	
	
	


}
