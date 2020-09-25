/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Art
/*     */ {
/*  13 */   KEBAB(0, 1, 1),
/*  14 */   AZTEC(1, 1, 1),
/*  15 */   ALBAN(2, 1, 1),
/*  16 */   AZTEC2(3, 1, 1),
/*  17 */   BOMB(4, 1, 1),
/*  18 */   PLANT(5, 1, 1),
/*  19 */   WASTELAND(6, 1, 1),
/*  20 */   POOL(7, 2, 1),
/*  21 */   COURBET(8, 2, 1),
/*  22 */   SEA(9, 2, 1),
/*  23 */   SUNSET(10, 2, 1),
/*  24 */   CREEBET(11, 2, 1),
/*  25 */   WANDERER(12, 1, 2),
/*  26 */   GRAHAM(13, 1, 2),
/*  27 */   MATCH(14, 2, 2),
/*  28 */   BUST(15, 2, 2),
/*  29 */   STAGE(16, 2, 2),
/*  30 */   VOID(17, 2, 2),
/*  31 */   SKULL_AND_ROSES(18, 2, 2),
/*  32 */   WITHER(19, 2, 2),
/*  33 */   FIGHTERS(20, 4, 2),
/*  34 */   POINTER(21, 4, 4),
/*  35 */   PIGSCENE(22, 4, 4),
/*  36 */   BURNINGSKULL(23, 4, 4),
/*  37 */   SKELETON(24, 4, 3),
/*  38 */   DONKEYKONG(25, 4, 3);
/*     */   
/*     */   static {
/*  41 */     BY_NAME = Maps.newHashMap();
/*  42 */     BY_ID = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     for (Art art : values()) {
/* 107 */       BY_ID.put(Integer.valueOf(art.id), art);
/* 108 */       BY_NAME.put(art.toString().toLowerCase().replaceAll("_", ""), art);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int id;
/*     */   private int width;
/*     */   private int height;
/*     */   private static final HashMap<String, Art> BY_NAME;
/*     */   private static final HashMap<Integer, Art> BY_ID;
/*     */   
/*     */   Art(int id, int width, int height) {
/*     */     this.id = id;
/*     */     this.width = width;
/*     */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getBlockWidth() {
/*     */     return this.width;
/*     */   }
/*     */   
/*     */   public int getBlockHeight() {
/*     */     return this.height;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int getId() {
/*     */     return this.id;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static Art getById(int id) {
/*     */     return BY_ID.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   public static Art getByName(String name) {
/*     */     Validate.notNull(name, "Name cannot be null");
/*     */     return BY_NAME.get(name.toLowerCase().replaceAll("_", ""));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Art.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */