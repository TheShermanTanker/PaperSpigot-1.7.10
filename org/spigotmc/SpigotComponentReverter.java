/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.ChatModifier;
/*     */ import net.minecraft.server.v1_7_R4.EnumChatFormat;
/*     */ import net.minecraft.server.v1_7_R4.IChatBaseComponent;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpigotComponentReverter
/*     */ {
/*     */   public static String toLegacy(IChatBaseComponent s) {
/*  16 */     StringBuilder builder = new StringBuilder();
/*  17 */     legacy(builder, s);
/*  18 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void legacy(StringBuilder builder, IChatBaseComponent s) {
/*  23 */     ChatModifier modifier = s.getChatModifier();
/*  24 */     colorize(builder, modifier);
/*  25 */     if (s instanceof net.minecraft.server.v1_7_R4.ChatComponentText) {
/*     */       
/*  27 */       builder.append(s.e());
/*     */     } else {
/*  29 */       throw new RuntimeException("Unhandled type: " + s.getClass().getSimpleName());
/*     */     } 
/*     */     
/*  32 */     for (IChatBaseComponent c : getExtra(s)) {
/*  33 */       legacy(builder, c);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void colorize(StringBuilder builder, ChatModifier modifier) {
/*  39 */     if (modifier == null)
/*     */       return; 
/*  41 */     EnumChatFormat color = getColor(modifier);
/*  42 */     if (color == null)
/*     */     {
/*  44 */       color = EnumChatFormat.BLACK;
/*     */     }
/*  46 */     builder.append(color.toString());
/*     */     
/*  48 */     if (isBold(modifier))
/*     */     {
/*  50 */       builder.append(ChatColor.BOLD);
/*     */     }
/*  52 */     if (isItalic(modifier))
/*     */     {
/*  54 */       builder.append(ChatColor.ITALIC);
/*     */     }
/*  56 */     if (isRandom(modifier))
/*     */     {
/*  58 */       builder.append(ChatColor.MAGIC);
/*     */     }
/*  60 */     if (isStrikethrough(modifier))
/*     */     {
/*  62 */       builder.append(ChatColor.STRIKETHROUGH);
/*     */     }
/*  64 */     if (isUnderline(modifier))
/*     */     {
/*  66 */       builder.append(ChatColor.UNDERLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<IChatBaseComponent> getExtra(IChatBaseComponent c) {
/*  73 */     return c.a();
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumChatFormat getColor(ChatModifier c) {
/*  78 */     return c.a();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isBold(ChatModifier c) {
/*  83 */     return c.b();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isItalic(ChatModifier c) {
/*  88 */     return c.c();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isStrikethrough(ChatModifier c) {
/*  93 */     return c.d();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isUnderline(ChatModifier c) {
/*  98 */     return c.e();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRandom(ChatModifier c) {
/* 103 */     return c.f();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotComponentReverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */