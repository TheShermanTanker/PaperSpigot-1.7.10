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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum GameMode
/*    */ {
/* 18 */   CREATIVE(1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   SURVIVAL(0),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   ADVENTURE(2);
/*    */   
/*    */   static {
/* 31 */     BY_ID = Maps.newHashMap();
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
/* 62 */     for (GameMode mode : values())
/* 63 */       BY_ID.put(Integer.valueOf(mode.getValue()), mode); 
/*    */   }
/*    */   
/*    */   private final int value;
/*    */   private static final Map<Integer, GameMode> BY_ID;
/*    */   
/*    */   GameMode(int value) {
/*    */     this.value = value;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getValue() {
/*    */     return this.value;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static GameMode getByValue(int value) {
/*    */     return BY_ID.get(Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\GameMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */