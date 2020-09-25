/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ public class Gate
/*    */   extends MaterialData
/*    */   implements Directional, Openable
/*    */ {
/*    */   private static final byte OPEN_BIT = 4;
/*    */   private static final byte DIR_BIT = 3;
/*    */   private static final byte GATE_SOUTH = 0;
/*    */   private static final byte GATE_WEST = 1;
/*    */   private static final byte GATE_NORTH = 2;
/*    */   private static final byte GATE_EAST = 3;
/*    */   
/*    */   public Gate() {
/* 18 */     super(Material.FENCE_GATE);
/*    */   }
/*    */   
/*    */   public Gate(int type, byte data) {
/* 22 */     super(type, data);
/*    */   }
/*    */   
/*    */   public Gate(byte data) {
/* 26 */     super(Material.FENCE_GATE, data);
/*    */   }
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 30 */     byte data = (byte)(getData() & 0xFFFFFFFC);
/*    */     
/* 32 */     switch (face) {
/*    */       
/*    */       default:
/* 35 */         data = (byte)(data | 0x0);
/*    */         break;
/*    */       case SOUTH:
/* 38 */         data = (byte)(data | 0x1);
/*    */         break;
/*    */       case WEST:
/* 41 */         data = (byte)(data | 0x2);
/*    */         break;
/*    */       case NORTH:
/* 44 */         data = (byte)(data | 0x3);
/*    */         break;
/*    */     } 
/*    */     
/* 48 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 52 */     switch (getData() & 0x3) {
/*    */       case 0:
/* 54 */         return BlockFace.EAST;
/*    */       case 1:
/* 56 */         return BlockFace.SOUTH;
/*    */       case 2:
/* 58 */         return BlockFace.WEST;
/*    */       case 3:
/* 60 */         return BlockFace.NORTH;
/*    */     } 
/*    */     
/* 63 */     return BlockFace.EAST;
/*    */   }
/*    */   
/*    */   public boolean isOpen() {
/* 67 */     return ((getData() & 0x4) > 0);
/*    */   }
/*    */   
/*    */   public void setOpen(boolean isOpen) {
/* 71 */     byte data = getData();
/*    */     
/* 73 */     if (isOpen) {
/* 74 */       data = (byte)(data | 0x4);
/*    */     } else {
/* 76 */       data = (byte)(data & 0xFFFFFFFB);
/*    */     } 
/*    */     
/* 79 */     setData(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return (isOpen() ? "OPEN " : "CLOSED ") + " facing and opening " + getFacing();
/*    */   }
/*    */ 
/*    */   
/*    */   public Gate clone() {
/* 89 */     return (Gate)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Gate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */