/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum WorldType
/*    */ {
/* 10 */   NORMAL("DEFAULT"),
/* 11 */   FLAT("FLAT"),
/* 12 */   VERSION_1_1("DEFAULT_1_1"),
/* 13 */   LARGE_BIOMES("LARGEBIOMES"),
/* 14 */   AMPLIFIED("AMPLIFIED");
/*    */   static {
/* 16 */     BY_NAME = Maps.newHashMap();
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
/* 43 */     for (WorldType type : values())
/* 44 */       BY_NAME.put(type.name, type); 
/*    */   }
/*    */   
/*    */   private static final Map<String, WorldType> BY_NAME;
/*    */   private final String name;
/*    */   
/*    */   WorldType(String name) {
/*    */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/*    */     return this.name;
/*    */   }
/*    */   
/*    */   public static WorldType getByName(String name) {
/*    */     return BY_NAME.get(name.toUpperCase());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\WorldType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */