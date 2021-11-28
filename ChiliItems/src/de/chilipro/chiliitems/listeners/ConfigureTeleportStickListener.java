/*    */ package de.chilipro.chiliitems.listeners;
/*    */ import java.util.ArrayList;

/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;

/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.ItemNames;
/*    */ import de.chilipro.chiliitems.configuration.PrefixStrings;
/*    */ 
/*    */ public class ConfigureTeleportStickListener implements org.bukkit.event.Listener
/*    */ {
/* 19 */   private static String errorMessage = PrefixStrings.main + PrefixStrings.negativeColorCode + " Die Teleport Struktur wurde nicht korrekt nachgebaut!";
/*    */   
/*    */   @EventHandler
/*    */   public void onInteract(PlayerInteractEvent e) {
/* 23 */     if (e.getPlayer().isSneaking()) {
/*    */       try {
/* 25 */         if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ItemNames.teleportStick)) {
/* 26 */           if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
/* 27 */             if (e.getClickedBlock().getType() == Material.BEACON)
/*    */             {
/* 29 */               Location blockLocation0 = e.getClickedBlock().getLocation();
/* 30 */               Location blockLocation1 = e.getClickedBlock().getLocation();
/* 31 */               Location blockLocation2 = e.getClickedBlock().getLocation();
/* 32 */               Location blockLocation3 = e.getClickedBlock().getLocation();
/* 33 */               Location blockLocation4 = e.getClickedBlock().getLocation();
/*    */               
/* 35 */               if ((blockLocation0.add(1.0D, 0.0D, 0.0D).getBlock().getType() == Material.OBSIDIAN) && (blockLocation1.add(0.0D, 0.0D, 1.0D).getBlock().getType() == Material.OBSIDIAN) && 
/* 36 */                 (blockLocation2.subtract(1.0D, 0.0D, 0.0D).getBlock().getType() == Material.OBSIDIAN) && (blockLocation3.subtract(0.0D, 0.0D, 1.0D).getBlock().getType() == Material.OBSIDIAN))
/*    */               {
/* 38 */                 ItemStack teleportStick = new ItemStack(Material.STICK);
/* 39 */                 ItemMeta teleportStickMeta = teleportStick.getItemMeta();
/* 40 */                 teleportStickMeta.setDisplayName(ItemNames.teleportStick);
/*    */                 
/* 42 */                 ArrayList<String> lore = new ArrayList<>();
/* 43 */                 lore.add("§a" + blockLocation4.getWorld().getName().toString());
/* 44 */                 lore.add("§a" + String.valueOf(blockLocation4.getBlockX()));
/* 45 */                 lore.add("§a" + String.valueOf(blockLocation4.getBlockY()));
/* 46 */                 lore.add("§a" + String.valueOf(blockLocation4.getBlockZ()));
/* 47 */                 teleportStickMeta.setLore(lore);
/* 48 */                 e.getPlayer().getInventory().getItemInHand().setItemMeta(teleportStickMeta);
/* 49 */                 e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du hast deinen §bTeleport §6Stick" + PrefixStrings.positiveColorCode + " erfolgreich konfiguriert!");
/*    */               }
/*    */               else {
/* 52 */                 e.getPlayer().sendMessage(errorMessage);
/*    */               }
/*    */               
/* 55 */               e.setCancelled(true);
/*    */             }
/*    */             else {
/* 58 */               e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Bitte klicke den Beacon in einer Teleport Struktur an!");
/*    */             }
/* 60 */           } else if (e.getAction() == Action.LEFT_CLICK_AIR) {
/* 61 */             e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Um dein Item zu Konfigurieren, musst du einen Block anklicken!");
/*    */           }
/*    */         }
/*    */       }
/*    */       catch (Exception localException) {}
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\listeners\ConfigureTeleportStickListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */