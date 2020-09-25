/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Furnace
/*    */   extends FurnaceAndDispenser
/*    */ {
/*    */   public Furnace() {
/* 12 */     super(Material.FURNACE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Furnace(BlockFace direction) {
/* 21 */     this();
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Furnace(int type) {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public Furnace(Material type) {
/* 35 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Furnace(int type, byte data) {
/* 44 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Furnace(Material type, byte data) {
/* 53 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public Furnace clone() {
/* 58 */     return (Furnace)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Furnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */