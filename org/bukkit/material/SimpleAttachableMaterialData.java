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
/*    */ public abstract class SimpleAttachableMaterialData
/*    */   extends MaterialData
/*    */   implements Attachable
/*    */ {
/*    */   @Deprecated
/*    */   public SimpleAttachableMaterialData(int type) {
/* 17 */     super(type);
/*    */   }
/*    */   
/*    */   public SimpleAttachableMaterialData(int type, BlockFace direction) {
/* 21 */     this(type);
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */   public SimpleAttachableMaterialData(Material type, BlockFace direction) {
/* 26 */     this(type);
/* 27 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */   public SimpleAttachableMaterialData(Material type) {
/* 31 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public SimpleAttachableMaterialData(int type, byte data) {
/* 40 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public SimpleAttachableMaterialData(Material type, byte data) {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 53 */     BlockFace attachedFace = getAttachedFace();
/* 54 */     return (attachedFace == null) ? null : attachedFace.getOppositeFace();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return super.toString() + " facing " + getFacing();
/*    */   }
/*    */ 
/*    */   
/*    */   public SimpleAttachableMaterialData clone() {
/* 64 */     return (SimpleAttachableMaterialData)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\SimpleAttachableMaterialData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */