/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EntityEffect
/*     */ {
/*  15 */   HURT(2),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   DEATH(3),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   WOLF_SMOKE(6),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   WOLF_HEARTS(7),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   WOLF_SHAKE(8),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   SHEEP_EAT(10),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   IRON_GOLEM_ROSE(11),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   VILLAGER_HEART(12),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   VILLAGER_ANGRY(13),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   VILLAGER_HAPPY(14),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   WITCH_MAGIC(15),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   ZOMBIE_TRANSFORM(16),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   FIREWORK_EXPLODE(17);
/*     */   
/*     */   static {
/* 100 */     BY_DATA = Maps.newHashMap();
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
/* 132 */     for (EntityEffect entityEffect : values())
/* 133 */       BY_DATA.put(Byte.valueOf(entityEffect.data), entityEffect); 
/*     */   }
/*     */   
/*     */   private final byte data;
/*     */   private static final Map<Byte, EntityEffect> BY_DATA;
/*     */   
/*     */   EntityEffect(int data) {
/*     */     this.data = (byte)data;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public byte getData() {
/*     */     return this.data;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static EntityEffect getByData(byte data) {
/*     */     return BY_DATA.get(Byte.valueOf(data));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\EntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */