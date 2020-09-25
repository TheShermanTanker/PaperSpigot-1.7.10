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
/*    */ public enum CropState
/*    */ {
/* 15 */   SEEDED(0),
/*    */ 
/*    */ 
/*    */   
/* 19 */   GERMINATED(1),
/*    */ 
/*    */ 
/*    */   
/* 23 */   VERY_SMALL(2),
/*    */ 
/*    */ 
/*    */   
/* 27 */   SMALL(3),
/*    */ 
/*    */ 
/*    */   
/* 31 */   MEDIUM(4),
/*    */ 
/*    */ 
/*    */   
/* 35 */   TALL(5),
/*    */ 
/*    */ 
/*    */   
/* 39 */   VERY_TALL(6),
/*    */ 
/*    */ 
/*    */   
/* 43 */   RIPE(7);
/*    */   
/*    */   static {
/* 46 */     BY_DATA = Maps.newHashMap();
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
/* 77 */     for (CropState cropState : values())
/* 78 */       BY_DATA.put(Byte.valueOf(cropState.getData()), cropState); 
/*    */   }
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, CropState> BY_DATA;
/*    */   
/*    */   CropState(int data) {
/*    */     this.data = (byte)data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public byte getData() {
/*    */     return this.data;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static CropState getByData(byte data) {
/*    */     return BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\CropState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */