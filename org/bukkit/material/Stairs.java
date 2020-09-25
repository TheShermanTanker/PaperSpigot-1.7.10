/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Stairs
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   @Deprecated
/*     */   public Stairs(int type) {
/*  17 */     super(type);
/*     */   }
/*     */   
/*     */   public Stairs(Material type) {
/*  21 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Stairs(int type, byte data) {
/*  30 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Stairs(Material type, byte data) {
/*  39 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAscendingDirection() {
/*  46 */     byte data = getData();
/*     */     
/*  48 */     switch (data & 0x3) {
/*     */       
/*     */       default:
/*  51 */         return BlockFace.EAST;
/*     */       
/*     */       case 1:
/*  54 */         return BlockFace.WEST;
/*     */       
/*     */       case 2:
/*  57 */         return BlockFace.SOUTH;
/*     */       case 3:
/*     */         break;
/*  60 */     }  return BlockFace.NORTH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getDescendingDirection() {
/*  68 */     return getAscendingDirection().getOppositeFace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  77 */     switch (face) {
/*     */       case NORTH:
/*  79 */         data = 3;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  83 */         data = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  88 */         data = 0;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  92 */         data = 1;
/*     */         break;
/*     */     } 
/*     */     
/*  96 */     setData((byte)(getData() & 0xC | data));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/* 103 */     return getDescendingDirection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInverted() {
/* 112 */     return ((getData() & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverted(boolean inv) {
/* 122 */     int dat = getData() & 0x3;
/* 123 */     if (inv) {
/* 124 */       dat |= 0x4;
/*     */     }
/* 126 */     setData((byte)dat);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     return super.toString() + " facing " + getFacing() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */ 
/*     */   
/*     */   public Stairs clone() {
/* 136 */     return (Stairs)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Stairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */