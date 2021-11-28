/*    */ package de.chilipro.chiliitems.main;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.plugin.java.JavaPlugin;

/*    */ 
/*    */ import de.chilipro.chiliitems.commands.TprCommand;
/*    */ import de.chilipro.chiliitems.craftingrecepies.NetherStickRecipe;
/*    */ import de.chilipro.chiliitems.craftingrecepies.TeleportStickReipe;
/*    */ import de.chilipro.chiliitems.listeners.CancelTeleportListener;
/*    */ import de.chilipro.chiliitems.listeners.ConfigureTeleportStickListener;
/*    */ import de.chilipro.chiliitems.listeners.UseTeleportStickListener;
/*    */ import de.slikey.effectlib.EffectManager;
/*    */ 
/*    */ public class Main extends JavaPlugin
/*    */ {
/*    */   static Main plugin;
/*    */   public static EffectManager effectManager;
/*    */   
/*    */   public void onEnable()
/*    */   {
/* 22 */     PluginManager pluginManager = Bukkit.getPluginManager();
/* 23 */     plugin = this;
/* 24 */     effectManager = new EffectManager(this);
/*    */     
/* 26 */     getCommand("tpr").setExecutor(new TprCommand());
/*    */     
/* 28 */     NetherStickRecipe.registerRecipe();
/* 29 */     TeleportStickReipe.registerRecipe();
/*    */     
/* 31 */     pluginManager.registerEvents(new UseTeleportStickListener(), this);
/* 32 */     pluginManager.registerEvents(new CancelTeleportListener(), this);
/* 33 */     pluginManager.registerEvents(new ConfigureTeleportStickListener(), this);
/*    */   }
/*    */   
/*    */ 
/*    */   public void onDisable()
/*    */   {
/* 39 */     effectManager.dispose();
/*    */   }
/*    */   
/*    */   public static Main getInstance() {
/* 43 */     return plugin;
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\main\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */