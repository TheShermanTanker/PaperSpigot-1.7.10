/*    */ package org.bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Rotation
/*    */ {
/* 13 */   NONE,
/*    */ 
/*    */ 
/*    */   
/* 17 */   CLOCKWISE,
/*    */ 
/*    */ 
/*    */   
/* 21 */   FLIPPED,
/*    */ 
/*    */ 
/*    */   
/* 25 */   COUNTER_CLOCKWISE;
/*    */   
/*    */   static {
/* 28 */     rotations = values();
/*    */   }
/*    */ 
/*    */   
/*    */   private static final Rotation[] rotations;
/*    */ 
/*    */   
/*    */   public Rotation rotateClockwise() {
/* 36 */     return rotations[ordinal() + 1 & 0x3];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Rotation rotateCounterClockwise() {
/* 45 */     return rotations[ordinal() - 1 & 0x3];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Rotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */