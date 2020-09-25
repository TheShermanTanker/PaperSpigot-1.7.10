/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SandstoneType
/*    */ {
/* 11 */   CRACKED(0),
/* 12 */   GLYPHED(1),
/* 13 */   SMOOTH(2);
/*    */   
/*    */   static {
/* 16 */     BY_DATA = Maps.newHashMap();
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
/* 47 */     for (SandstoneType type : values())
/* 48 */       BY_DATA.put(Byte.valueOf(type.data), type); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, SandstoneType> BY_DATA;
/*    */   
/*    */   SandstoneType(int data) {
/*    */     this.data = (byte)data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public byte getData() {
/*    */     return this.data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static SandstoneType getByData(byte data) {
/*    */     return BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\SandstoneType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */