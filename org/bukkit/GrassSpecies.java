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
/*    */ public enum GrassSpecies
/*    */ {
/* 15 */   DEAD(0),
/*    */ 
/*    */ 
/*    */   
/* 19 */   NORMAL(1),
/*    */ 
/*    */ 
/*    */   
/* 23 */   FERN_LIKE(2);
/*    */   
/*    */   static {
/* 26 */     BY_DATA = Maps.newHashMap();
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
/* 57 */     for (GrassSpecies grassSpecies : values())
/* 58 */       BY_DATA.put(Byte.valueOf(grassSpecies.getData()), grassSpecies); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, GrassSpecies> BY_DATA;
/*    */   
/*    */   GrassSpecies(int data) {
/*    */     this.data = (byte)data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public byte getData() {
/*    */     return this.data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static GrassSpecies getByData(byte data) {
/*    */     return BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\GrassSpecies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */