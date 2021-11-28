package de.devofvictory.invanimation.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.devofvictory.invanimation.main.InvAnimation;


public class AnimatedInv{
	
	private Inventory bukkitInv;
	private AnimationType animationType;
	private int delayTicks;
	private Sound tickSound;
	private Runnable finished;
	private String invName;
	
	public AnimatedInv(Inventory bukkitInv, String invName) {
		this.bukkitInv = bukkitInv;
		this.animationType = AnimationType.FADE_IN_LEFT_TO_RIGHT;
		this.delayTicks = 2;
		this.tickSound = Sound.UI_BUTTON_CLICK;
		this.invName = invName;
	}

	public Inventory getBukkitInv() {
		return bukkitInv;
	}

	public void setBukkitInv(Inventory bukkitInv) {
		this.bukkitInv = bukkitInv;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}

	public int getDelayTicks() {
		return delayTicks;
	}

	public void setDelayTicks(int delayTicks) {
		this.delayTicks = delayTicks;
	}

	public Sound getTickSound() {
		return tickSound;
	}

	public void setTickSound(Sound tickSound) {
		this.tickSound = tickSound;
	}

	public Runnable getFinished() {
		return finished;
	}

	public void setFinished(Runnable finished) {
		this.finished = finished;
	}
	
	public void play(Player p) {
		
		int size = bukkitInv.getSize();
		Inventory inv = Bukkit.createInventory(null, size, invName);
		
		if (animationType == AnimationType.FADE_IN_LEFT_TO_RIGHT) {
			
			p.openInventory(inv);
			
			new BukkitRunnable() {
				
				int count = 0;
				
				@Override
				public void run() {
					
					List<Integer> contents = new ArrayList<Integer>();
					
					for (int i = 0; i<size-1;i+=9) {
						for (int j = 1; j<count; j++) {
							int number = i+j-1;
							contents.add(number);
						}
					}
					
					for (int i = 0; i<size; i++) {
						
						if (contents.contains(i)) { 
							inv.setItem(i, bukkitInv.getItem(i+9-count+1));
							
						}else {
							inv.setItem(i, new ItemStack(Material.AIR));
						}
					}
					
					
					if (tickSound != null) {
						p.playSound(p.getLocation(), tickSound, 1, 1);
					}
					count++;
					
					if (count == 11) {
						cancel();
					}
					
				}
			}.runTaskTimer(InvAnimation.getInstance(), 0, delayTicks);
			
			
		}else if (animationType == AnimationType.FADE_IN_TOP_TO_BUTTOM) {
			p.openInventory(inv);
			
			new BukkitRunnable() {
				
				int count = 1;
				
				@Override
				public void run() {
					
					List<Integer> contents = new ArrayList<Integer>();
					
					int from = 0;
					int to = count*9;
					
					for (int i = from; i<to; i++) {
						contents.add(i);
					}
					
					
					// size - round*9
						
					
					for (int i = 0; i<size; i++) {
						
						
						if (contents.contains(i)) {
							inv.setItem(i, bukkitInv.getItem(size-count*9+i));
						}else {
							inv.setItem(i, new ItemStack(Material.AIR));
						}
									
					}
					
					if (tickSound != null) {
						p.playSound(p.getLocation(), tickSound, 1, 1);
					}
					count++;
					if (count==size/9+1) {
						cancel();
					}
					
				}
			}.runTaskTimer(InvAnimation.getInstance(), 0, delayTicks);
		}else if (animationType == AnimationType.RANDOM_SPREAD) {
			
			p.openInventory(inv);
			
			Random random = new Random();
			
			new BukkitRunnable() {
				
				List<Integer> loadedSlots = new ArrayList<Integer>();
				
				@Override
				public void run() {
					
					int randomSlot = random.nextInt(size);
					
					while(loadedSlots.contains(randomSlot)) {
						randomSlot = random.nextInt(size);
					}
					
					loadedSlots.add(randomSlot);
					inv.setItem(randomSlot, bukkitInv.getItem(randomSlot));
					
					
					if (tickSound != null) {
						p.playSound(p.getLocation(), tickSound, 1, 1);
					}
					
					if (loadedSlots.size() >= size) {
						cancel();
					}
				}
			}.runTaskTimer(InvAnimation.getInstance(), 0, delayTicks);
			
			
		}else if (animationType == AnimationType.SLOT_FOR_SLOT) {
			
			p.openInventory(inv);
			
			
			new BukkitRunnable() {
				int count = 0;
				
				@Override
				public void run() {
					
					inv.setItem(count, bukkitInv.getItem(count));
					
					
					if (tickSound != null) {
						p.playSound(p.getLocation(), tickSound, 1, 1);
					}
					count++;
					if (count == size) {
						cancel();
					}
					
				}
			}.runTaskTimer(InvAnimation.getInstance(), 0, delayTicks);
			
		}else if (animationType == AnimationType.GROWING_BARS) {
			p.openInventory(inv);
			
			
			for (int i = 0; i<size/9; i++) {
				animateRow(p, inv, bukkitInv, i, delayTicks, tickSound);
			}
			
			
		}else if (animationType == AnimationType.SPIRAL) {
			p.openInventory(inv);
			
			HashMap<Integer, ArrayList<Integer>> slots = new HashMap<Integer, ArrayList<Integer>>();
			
			ArrayList<Integer> one = getLine(0, 8);
			ArrayList<Integer> two = getLine(0, 8);
			two.addAll(getLine(17, 9));
			ArrayList<Integer> three = getLine(0, 8);
			three.add(17);
			three.addAll(getLine(26, 18));
			three.add(9);
			three.addAll(getLine(10, 16));
			ArrayList<Integer> four = getLine(0, 8);
			four.add(17);
			four.add(26);
			four.addAll(getLine(35, 27));
			four.add(18);
			four.addAll(getLine(15, 19));
			ArrayList<Integer> five = getLine(0, 8);
			five.add(17);
			five.add(26);
			five.add(35);
			five.addAll(getLine(44, 36));
			five.add(27);
			five.add(18);
			five.addAll(getLine(9, 16));
			five.add(25);
			five.addAll(getLine(34, 28));
			five.addAll(getLine(19, 25));
			
			
			slots.put(1, one);
			slots.put(2, two);
			slots.put(3, three);
			slots.put(4, four);
			slots.put(5, five);
			
			
			new BukkitRunnable() {
				
				int count = 0;
				
				@Override
				public void run() {
					
					int slot = slots.get(size/9).get(count);
					
					inv.setItem(slot, bukkitInv.getItem(slot));
					
					
					
					if (tickSound != null) {
						p.playSound(p.getLocation(), tickSound, 1, 1);
					}
					count++;
					if (count == size) {
						cancel();
					}
					
				}
			}.runTaskTimer(InvAnimation.getInstance(), 0, delayTicks);
			
		}
		
		
	}
	
	
	
	public static void animateRow(Player p, Inventory inv, Inventory defaultInv , int row, int delayTicks, Sound tickSound) {
		new BukkitRunnable() {
			
			int count = row*9;
			
			@Override
			public void run() {
				
				inv.setItem(count, defaultInv.getItem(count));
				
				if (tickSound != null) {
					p.playSound(p.getLocation(), tickSound, 1, 1);
				}
				
				count++;
				
				if (count == row*9+9) {
					cancel();
				}
				
			}
		}.runTaskTimer(InvAnimation.getInstance(), delayTicks*row, delayTicks);
	}
	
	public static ArrayList<Integer> getLine(int from, int to) {
		ArrayList<Integer> arraylist = new ArrayList<Integer>();
		
		if (from < to) {
			for (int i = from; i<=to; i++) {
				arraylist.add(i);
			}
		}else {
			for (int i = from; i>=to; i--) {
				arraylist.add(i);
			}
		}
		return arraylist;
		
	}
	
	

}
