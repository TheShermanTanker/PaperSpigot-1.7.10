/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum TreeSpecies
/*    */ {
/* 15 */   GENERIC(0),
/*    */ 
/*    */ 
/*    */   
/* 19 */   REDWOOD(1),
/*    */ 
/*    */ 
/*    */   
/* 23 */   BIRCH(2),
/*    */ 
/*    */ 
/*    */   
/* 27 */   JUNGLE(3),
/*    */ 
/*    */ 
/*    */   
/* 31 */   ACACIA(4),
/*    */ 
/*    */ 
/*    */   
/* 35 */   DARK_OAK(5);
/*    */   private final byte data;
/*    */   
/*    */   static {
/* 39 */     BY_DATA = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     for (TreeSpecies species : values())
/* 71 */       BY_DATA.put(Byte.valueOf(species.data), species); 
/*    */   }
/*    */   
/*    */   private static final Map<Byte, TreeSpecies> BY_DATA;
/*    */   
/*    */   TreeSpecies(int data) {
/*    */     this.data = (byte)data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public byte getData() {
/*    */     return this.data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static TreeSpecies getByData(byte data) {
/*    */     return BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\TreeSpecies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */