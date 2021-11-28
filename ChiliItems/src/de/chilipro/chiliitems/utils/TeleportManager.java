/*    */ package de.chilipro.chiliitems.utils;
/*    */ import java.util.HashMap;

/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;

/*    */ 
/*    */ import de.chilipro.chiliitems.configuration.PrefixStrings;
/*    */ import de.chilipro.chiliitems.main.Main;
/*    */ import de.slikey.effectlib.effect.WarpEffect;
/*    */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
/*    */ 
/*    */ public class TeleportManager
/*    */ {
/* 19 */   public static HashMap<Player, WarpEffect> teleport = new HashMap<>();
/* 20 */   public static HashMap<Player, Player> tpas = new HashMap<>();
/*    */   
/*    */   public static void addPlayer(Player p) {
/* 23 */     if (!teleport.containsKey(p)) {
/* 24 */       teleport.put(p, new WarpEffect(Main.effectManager));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void removePlayer(Player p)
/*    */   {
/* 30 */     if (teleport.containsKey(p)) {
/* 31 */       teleport.remove(p);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean containsPlayer(Player p) {
/* 36 */     if (teleport.containsKey(p)) {
/* 37 */       return true;
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public static void teleportEffect(Player p, String color)
/*    */   {
/* 45 */     Location loc = p.getLocation();
/* 46 */     loc.setY(loc.getY() - 0.25D);
/* 47 */     ((WarpEffect)teleport.get(p)).setLocation(loc);
/* 48 */     ((WarpEffect)teleport.get(p)).period = 8;
/* 49 */     if (color == "red") {
/* 50 */       ((WarpEffect)teleport.get(p)).color = Color.RED;
/* 51 */     } else if (color == "green") {
/* 52 */       ((WarpEffect)teleport.get(p)).color = Color.GREEN;
/*    */     }
/* 54 */     ((WarpEffect)teleport.get(p)).start();
/*    */   }
/*    */   
/*    */   public static void playerTeleportAnfrage(Player p, Player target) {
/* 58 */     if (tpas.get(p) == target) {
/* 59 */       p.sendMessage(PrefixStrings.main + PrefixStrings.neutralColorCode + " Du hast dem Spieler bereits eine Anfrage gesendet!");
/*    */     } else {
/* 61 */       IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + PrefixStrings.main + PrefixStrings.positiveColorCode + " Tp Anfrage von\", \"extra\":[{\"text\":\" " + PrefixStrings.neutralColorCode + p.getName() + "\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"§7Klick hier um die Anfrage anzunehmen\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpr accept\"   }   }]}");
/*    */       
/* 63 */       PacketPlayOutChat packet = new PacketPlayOutChat(chat);
/* 64 */       ((CraftPlayer)target).getHandle().playerConnection.sendPacket(packet);
/*    */     }
/*    */   }
/*    */ }
