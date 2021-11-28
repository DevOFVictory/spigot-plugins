/*    */ package de.chilipro.chiliitems.commands;
/*    */ import org.bukkit.Effect;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;

/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.PrefixStrings;
/*    */ import de.chilipro.chiliitems.main.Main;
/*    */ import de.chilipro.chiliitems.utils.TeleportManager;
/*    */ 
/*    */ public class TprCommand implements CommandExecutor
/*    */ {
/*    */   public static int tasklater;
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
/*    */   {
/* 21 */     if ((sender instanceof Player)) {
/* 22 */       final Player p = (Player)sender;
/* 23 */       if (args.length == 1) {
/* 24 */         if (args[0].equalsIgnoreCase("accept")) {
/* 25 */           if (TeleportManager.tpas.containsKey(p)) {
/* 26 */             ((Player)TeleportManager.tpas.get(p)).sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wirst in 5 Sekunden zum dem Spieler " + PrefixStrings.neutralColorCode + p.getName() + PrefixStrings.positiveColorCode + " teleportiert!");
/* 27 */             ((Player)TeleportManager.tpas.get(p)).sendMessage(PrefixStrings.main + PrefixStrings.neutralColorCode + " Bitte bewege dich währenddessen nicht!");
/* 28 */             TeleportManager.addPlayer((Player)TeleportManager.tpas.get(p));
/* 29 */             TeleportManager.teleportEffect((Player)TeleportManager.tpas.get(p), "red");
/* 30 */             tasklater = org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
/*    */             {
/*    */ 
/*    */               @SuppressWarnings("deprecation")
					public void run()
/*    */               {
	
/* 35 */                 TeleportManager.removePlayer((Player)TeleportManager.tpas.get(p));
/* 36 */                 ((Player)TeleportManager.tpas.get(p)).playSound(((Player)TeleportManager.tpas.get(p)).getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 10.0F);
/* 37 */                 ((Player)TeleportManager.tpas.get(p)).playEffect(((Player)TeleportManager.tpas.get(p)).getLocation(), Effect.SMOKE, 10);
/* 38 */                 ((Player)TeleportManager.tpas.get(p)).getLocation().getWorld().playSound(((Player)TeleportManager.tpas.get(p)).getLocation(), Sound.FIZZ, 10.0F, 10.0F);
/* 39 */                 ((Player)TeleportManager.tpas.get(p)).teleport(p.getLocation());
/* 40 */                 ((Player)TeleportManager.tpas.get(p)).playEffect(((Player)TeleportManager.tpas.get(p)).getLocation(), Effect.ENDER_SIGNAL, 10);
/* 41 */                 ((Player)TeleportManager.tpas.get(p)).sendMessage(PrefixStrings.main + PrefixStrings.positiveColorCode + " Du wurdest erfolgreich zu dem Spieler " + PrefixStrings.neutralColorCode + p.getName() + PrefixStrings.positiveColorCode + "teleportiert!");
/* 42 */                 TeleportManager.tpas.remove(p);
/*    */               }
/* 44 */             }, 100L);
/*    */           }
/*    */           else {
/* 47 */             p.sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Du hast keine Teleport Anfrage bekommen!");
/*    */           }
/* 49 */         } else if (args[0].equalsIgnoreCase("cancel")) {
/* 50 */           if (TeleportManager.tpas.containsKey(p)) {
/* 51 */             TeleportManager.tpas.remove(p);
/* 52 */             ((Player)TeleportManager.tpas.get(p)).sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Der Spieler " + PrefixStrings.neutralColorCode + p.getName() + PrefixStrings.negativeColorCode + " hat deine Teleport Anfrage abgelehnt!");
/*    */           }
/*    */           else {
/* 55 */             p.sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Du hast keine Teleport Anfrage bekommen!");
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\commands\TprCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */