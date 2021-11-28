/*    */ package de.chilipro.chiliitems.craftingrecepies;
/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.ItemNames;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NetherStickRecipe
/*    */ {
/*    */   public static void registerRecipe()
/*    */   {
/* 17 */     ItemStack netherStick = new ItemStack(Material.STICK);
/* 18 */     ItemMeta netherStickMeta = netherStick.getItemMeta();
/* 19 */     netherStickMeta.setDisplayName(ItemNames.teleportStick);
/* 20 */     ArrayList<String> netherStickLore = new ArrayList<>();
/* 21 */     netherStickLore.add(ItemNames.netherStickLore);
/* 22 */     netherStickMeta.setLore(netherStickLore);
/* 23 */     netherStick.setItemMeta(netherStickMeta);
/*    */     
/* 25 */     ShapelessRecipe netherStickRecipe = new ShapelessRecipe(netherStick);
/* 26 */     netherStickRecipe.addIngredient(Material.BLAZE_ROD);
/* 27 */     netherStickRecipe.addIngredient(Material.ENDER_PEARL);
/*    */     
/* 29 */     Bukkit.addRecipe(netherStickRecipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\delfi\OneDrive\Desktop\ChiliItems.jar!\de\chilipro\chiliitems\craftingrecepies\NetherStickRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */