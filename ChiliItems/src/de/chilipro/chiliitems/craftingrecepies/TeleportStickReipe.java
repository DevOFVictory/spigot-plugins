/*    */ package de.chilipro.chiliitems.craftingrecepies;
/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.ItemNames;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ public class TeleportStickReipe
/*    */ {
/*    */   public static void registerRecipe()
/*    */   {
/* 15 */     ItemStack netherStick = new ItemStack(Material.STICK);
/* 16 */     ItemMeta netherStickMeta = netherStick.getItemMeta();
/* 17 */     netherStickMeta.setDisplayName(ItemNames.teleportStick);
/* 18 */     netherStick.setItemMeta(netherStickMeta);
/*    */     
/* 20 */     ShapelessRecipe netherStickRecipe = new ShapelessRecipe(netherStick);
/* 21 */     netherStickRecipe.addIngredient(Material.STICK);
/* 22 */     netherStickRecipe.addIngredient(Material.ENDER_PEARL);
/*    */     
/* 24 */     Bukkit.addRecipe(netherStickRecipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\craftingrecepies\TeleportStickReipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */