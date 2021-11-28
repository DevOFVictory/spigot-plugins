/*     */ package de.chilipro.chiliitems.listeners;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;

/*     */ 
/*     */ import de.chilipro.chiliitems.configuration.ItemNames;
/*     */ import de.chilipro.chiliitems.configuration.PrefixStrings;
/*     */ import de.chilipro.chiliitems.main.Main;
import de.chilipro.chiliitems.utils.AnvilGui;
/*     */ import de.chilipro.chiliitems.utils.TeleportManager;
/*     */ 
/*     */ public class UseTeleportStickListener implements org.bukkit.event.Listener
/*     */ {
/*     */   public static int tasklater;
/*  27 */   private static String errorMessage = PrefixStrings.main + PrefixStrings.negativeColorCode + " Du hast deinen §bTeleport §6Stick " + PrefixStrings.negativeColorCode + " anscheinent nicht richtig konfiguriert!";
/*     */   
/*     */   @org.bukkit.event.EventHandler
/*     */   public void onInteract(final PlayerInteractEvent e) {
/*     */     try {
/*  32 */       if ((e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ItemNames.teleportStick)) && (
/*  33 */         (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK))) {
/*  34 */         if (!e.getPlayer().isSneaking()) {
/*  35 */           if (e.getItem().getItemMeta().getLore().contains(ItemNames.netherStickLore)) {
/*  36 */             if (!TeleportManager.containsPlayer(e.getPlayer())) {
/*  37 */               e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wirst in §65 Sekunden zum §5Nether " + PrefixStrings.positiveColorCode + "teleportiert!");
/*  38 */               e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.neutralColorCode + " Bitte bewege dich währenddessen nicht!");
/*  39 */               TeleportManager.addPlayer(e.getPlayer());
/*  40 */               TeleportManager.teleportEffect(e.getPlayer(), "red");
/*  41 */               tasklater = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
/*     */               {
/*     */ 
/*     */                 @SuppressWarnings("deprecation")
						public void run()
/*     */                 {
/*  46 */                   TeleportManager.removePlayer(e.getPlayer());
/*  47 */                   e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 10.0F);
/*  48 */                   e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 10);
/*  49 */                   Bukkit.getWorld("world_nether").playSound(e.getPlayer().getLocation(), Sound.FIZZ, 10.0F, 10.0F);
/*  50 */                   e.getPlayer().teleport(de.chilipro.chiliitems.configuration.Locations.nether);
/*  51 */                   e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 10);
/*  52 */                   e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wurdest erfolgreich zum §5Nether " + PrefixStrings.positiveColorCode + "teleportiert!");
/*     */                 }
/*     */                 
/*  55 */               }, 100L);
/*     */             } else {
/*  57 */               e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Du kannst immer nur einen Teleportvorgang auf einmal ausführen!");
/*     */             }
/*     */           }
/*     */           else {
/*     */             try
/*     */             {
/*  63 */               if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore() == null)
/*     */                 return;
/*  65 */               e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Du hast diesen §bTeleport §6Stick " + PrefixStrings.negativeColorCode + "anscheinend noch nicht richtig konfiguriert!");
/*     */               
/*  67 */               if (Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)) == null)
/*     */                 return;
/*  69 */               int x = 0;
/*  70 */               int y = 0;
/*  71 */               int z = 0;
/*     */               try
/*     */               {
/*  74 */                 x = Integer.parseInt((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(1));
/*     */               } catch (Exception e2) {
/*  76 */                 e.getPlayer().sendMessage("1");
/*  77 */                 e.getPlayer().sendMessage(errorMessage);
/*     */               }
/*     */               
/*     */               try
/*     */               {
/*  82 */                 y = Integer.parseInt((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(2));
/*     */               } catch (Exception e2) {
/*  84 */                 e.getPlayer().sendMessage("2");
/*  85 */                 e.getPlayer().sendMessage(errorMessage);
/*     */               }
/*     */               
/*     */               try
/*     */               {
/*  90 */                 z = Integer.parseInt((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(3));
/*     */               } catch (Exception e2) {
/*  92 */                 e.getPlayer().sendMessage("3");
/*  93 */                 e.getPlayer().sendMessage(errorMessage);
/*     */               }
/*     */               
/*     */ 
/*  97 */               if ((x != 0) && (y != 0) && (z != 0))
/*     */               {
/*  99 */                 int time = 10;
/*     */                 
/* 101 */                 Location redstone = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y - 1, z);
/* 102 */                 final Location beacon = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y, z);
/*     */                 
/* 104 */                 Location obsidian1 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x + 1, y, z);
/* 105 */                 Location obsidian2 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x - 1, y, z);
/* 106 */                 Location obsidian3 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y, z + 1);
/* 107 */                 Location obsidian4 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y, z - 1);
/*     */                 
/* 109 */                 Location dragon_egg1 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x + 1, y + 1, z);
/* 110 */                 Location dragon_egg2 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x - 1, y + 1, z);
/* 111 */                 Location dragon_egg3 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y + 1, z + 1);
/* 112 */                 Location dragon_egg4 = new Location(Bukkit.getWorld((String)e.getPlayer().getInventory().getItemInHand().getItemMeta().getLore().get(0)), x, y + 1, z - 1);
/*     */                 
/* 114 */                 if ((obsidian1.getBlock().getType() == Material.OBSIDIAN) && (obsidian2.getBlock().getType() == Material.OBSIDIAN) && (obsidian3.getBlock().getType() == Material.OBSIDIAN) && (obsidian4.getBlock().getType() == Material.OBSIDIAN) && (beacon.getBlock().getType() == Material.BEACON))
/*     */                 {
/* 116 */                   if (redstone.getBlock().getType() == Material.REDSTONE_BLOCK) {
/* 117 */                     time -= 2;
/*     */                   }
/* 119 */                   if (dragon_egg1.getBlock().getType() == Material.DRAGON_EGG) {
/* 120 */                     time -= 2;
/*     */                   }
/* 122 */                   if (dragon_egg2.getBlock().getType() == Material.DRAGON_EGG) {
/* 123 */                     time -= 2;
/*     */                   }
/* 125 */                   if (dragon_egg3.getBlock().getType() == Material.DRAGON_EGG) {
/* 126 */                     time -= 2;
/*     */                   }
/* 128 */                   if (dragon_egg4.getBlock().getType() == Material.DRAGON_EGG) {
/* 129 */                     time -= 2;
/*     */                   }
/*     */                   
/* 132 */                   e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wirst in §6" + time + " Sekunden zum §5Nether " + PrefixStrings.positiveColorCode + "teleportiert!");
/* 133 */                   e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.neutralColorCode + " Bitte bewege dich währenddessen nicht!");
/* 134 */                   TeleportManager.addPlayer(e.getPlayer());
/* 135 */                   TeleportManager.teleportEffect(e.getPlayer(), "red");
/* 136 */                   tasklater = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
/*     */                   {
/*     */ 
/*     */                     @SuppressWarnings("deprecation")
							public void run()
/*     */                     {
/* 141 */                       TeleportManager.removePlayer(e.getPlayer());
/* 142 */                       e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 10.0F);
/* 143 */                       e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 10);
/* 144 */                       e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 10.0F, 10.0F);
/* 145 */                       e.getPlayer().teleport(beacon);
/* 146 */                       e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 10);
/* 147 */                       e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wurdest erfolgreich zum §5Nether " + PrefixStrings.positiveColorCode + "teleportiert!");
/*     */                     }
/*     */                     
/* 150 */                   }, 20 * time);
/*     */                 }
/*     */                 else {
/* 153 */                   e.getPlayer().sendMessage(errorMessage);
/* 154 */                   e.getPlayer().sendMessage("0");
/*     */                 }
/*     */               }
/*     */               else {
/* 158 */                 e.getPlayer().sendMessage(errorMessage);
/* 159 */                 e.getPlayer().sendMessage("2");
/*     */ 
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 174 */           AnvilGui gui = new AnvilGui(e.getPlayer(), new AnvilGui.AnvilClickEventHandler()
/*     */           {
/*     */             String playerNameInput;
/*     */             
/*     */             public void onAnvilClick(AnvilGui.AnvilClickEvent event) {
/* 179 */               if (event.getSlot() == AnvilGui.AnvilSlot.OUTPUT) {
/* 180 */                 event.setWillClose(true);
/* 181 */                 event.setWillDestroy(true);
/*     */                 
/* 183 */                 this.playerNameInput = event.getOutPutMessage();
/* 184 */                 Player target = Bukkit.getPlayer(this.playerNameInput);
/* 185 */                 if (target != null) {
/* 186 */                   TeleportManager.playerTeleportAnfrage(e.getPlayer(), target);
/* 187 */                   TeleportManager.tpas.put(target, e.getPlayer());
/*     */                 } else {
/* 189 */                   e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Dieser Spieler ist zurzeit leider nicht online!");
/*     */                 }
/*     */               } else {
/* 192 */                 event.setWillClose(false);
/* 193 */                 event.setWillDestroy(false);
/*     */               }
/*     */               
/*     */             }
/*     */             
/* 198 */           });
/* 199 */           ItemStack item = new ItemStack(Material.NAME_TAG);
/* 200 */           ItemMeta itemMeta = item.getItemMeta();
/* 201 */           itemMeta.setDisplayName("§6Spielername");
/* 202 */           item.setItemMeta(itemMeta);
/*     */           
/* 204 */           gui.setSlot(AnvilGui.AnvilSlot.INPUT_LEFT, item);
/*     */           
/* 206 */           gui.open();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */   }
/*     */ }
