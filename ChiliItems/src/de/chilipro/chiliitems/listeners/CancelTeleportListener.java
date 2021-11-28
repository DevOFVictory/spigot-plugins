/*    */ package de.chilipro.chiliitems.listeners;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;

/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.PrefixStrings;
/*    */ import de.chilipro.chiliitems.utils.TeleportManager;
/*    */ import de.slikey.effectlib.effect.WarpEffect;
/*    */ 
/*    */ public class CancelTeleportListener implements org.bukkit.event.Listener
/*    */ {
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onMove(PlayerMoveEvent e)
/*    */   {
/* 16 */     if ((TeleportManager.containsPlayer(e.getPlayer())) && 
/* 17 */       (e.getFrom().getYaw() == e.getTo().getYaw())) {
/* 18 */       ((WarpEffect)TeleportManager.teleport.get(e.getPlayer())).cancel();
/* 19 */       TeleportManager.removePlayer(e.getPlayer());
/* 20 */       e.getPlayer().sendMessage(PrefixStrings.main + PrefixStrings.negativeColorCode + " Der Teleport Vorgang wurde abgebrochen!");
/* 21 */       Bukkit.getScheduler().cancelTask(UseTeleportStickListener.tasklater);
/* 22 */       Bukkit.getScheduler().cancelTask(de.chilipro.chiliitems.commands.TprCommand.tasklater);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\listeners\CancelTeleportListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */