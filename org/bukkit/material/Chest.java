/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Chest
/*    */   extends DirectionalContainer
/*    */ {
/*    */   public Chest() {
/* 12 */     super(Material.CHEST);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Chest(BlockFace direction) {
/* 21 */     this();
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Chest(int type) {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public Chest(Material type) {
/* 35 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Chest(int type, byte data) {
/* 44 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Chest(Material type, byte data) {
/* 53 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public Chest clone() {
/* 58 */     return (Chest)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Chest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */