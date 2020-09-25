/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedRails
/*    */   extends Rails
/*    */ {
/*    */   @Deprecated
/*    */   public ExtendedRails(int type) {
/* 17 */     super(type);
/*    */   }
/*    */   
/*    */   public ExtendedRails(Material type) {
/* 21 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public ExtendedRails(int type, byte data) {
/* 30 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public ExtendedRails(Material type, byte data) {
/* 39 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCurve() {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   protected byte getConvertedData() {
/* 54 */     return (byte)(getData() & 0x7);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDirection(BlockFace face, boolean isOnSlope) {
/* 59 */     boolean extraBitSet = ((getData() & 0x8) == 8);
/*    */     
/* 61 */     if (face != BlockFace.WEST && face != BlockFace.EAST && face != BlockFace.NORTH && face != BlockFace.SOUTH) {
/* 62 */       throw new IllegalArgumentException("Detector rails and powered rails cannot be set on a curve!");
/*    */     }
/*    */     
/* 65 */     super.setDirection(face, isOnSlope);
/* 66 */     setData((byte)(extraBitSet ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*    */   }
/*    */ 
/*    */   
/*    */   public ExtendedRails clone() {
/* 71 */     return (ExtendedRails)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\ExtendedRails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */