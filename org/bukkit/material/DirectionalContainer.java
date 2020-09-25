/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectionalContainer
/*    */   extends MaterialData
/*    */   implements Directional
/*    */ {
/*    */   @Deprecated
/*    */   public DirectionalContainer(int type) {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public DirectionalContainer(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public DirectionalContainer(int type, byte data) {
/* 29 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public DirectionalContainer(Material type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/*    */     byte data;
/* 44 */     switch (face) {
/*    */       case NORTH:
/* 46 */         data = 2;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 50 */         data = 3;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 54 */         data = 4;
/*    */         break;
/*    */ 
/*    */       
/*    */       default:
/* 59 */         data = 5;
/*    */         break;
/*    */     } 
/* 62 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 66 */     byte data = getData();
/*    */     
/* 68 */     switch (data) {
/*    */       case 2:
/* 70 */         return BlockFace.NORTH;
/*    */       
/*    */       case 3:
/* 73 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 4:
/* 76 */         return BlockFace.WEST;
/*    */     } 
/*    */ 
/*    */     
/* 80 */     return BlockFace.EAST;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return super.toString() + " facing " + getFacing();
/*    */   }
/*    */ 
/*    */   
/*    */   public DirectionalContainer clone() {
/* 91 */     return (DirectionalContainer)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\DirectionalContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */