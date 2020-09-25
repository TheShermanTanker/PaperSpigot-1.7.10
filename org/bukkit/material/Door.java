/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Door
/*     */   extends MaterialData
/*     */   implements Directional, Openable
/*     */ {
/*     */   public Door() {
/*  14 */     super(Material.WOODEN_DOOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Door(int type) {
/*  23 */     super(type);
/*     */   }
/*     */   
/*     */   public Door(Material type) {
/*  27 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Door(int type, byte data) {
/*  36 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Door(Material type, byte data) {
/*  45 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isOpen() {
/*  53 */     return ((getData() & 0x4) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setOpen(boolean isOpen) {
/*  61 */     setData((byte)(isOpen ? (getData() | 0x4) : (getData() & 0xFFFFFFFB)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTopHalf() {
/*  68 */     return ((getData() & 0x8) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setTopHalf(boolean isTopHalf) {
/*  79 */     setData((byte)(isTopHalf ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BlockFace getHingeCorner() {
/*  88 */     byte d = getData();
/*     */     
/*  90 */     if ((d & 0x3) == 3)
/*  91 */       return BlockFace.NORTH_WEST; 
/*  92 */     if ((d & 0x1) == 1)
/*  93 */       return BlockFace.SOUTH_EAST; 
/*  94 */     if ((d & 0x2) == 2) {
/*  95 */       return BlockFace.SOUTH_WEST;
/*     */     }
/*     */     
/*  98 */     return BlockFace.NORTH_EAST;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return (isTopHalf() ? "TOP" : "BOTTOM") + " half of " + super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setFacingDirection(BlockFace face) {
/* 114 */     byte data = (byte)(getData() & 0x12);
/* 115 */     switch (face) {
/*     */       case NORTH:
/* 117 */         data = (byte)(data | 0x1);
/*     */         break;
/*     */       
/*     */       case EAST:
/* 121 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 125 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */     } 
/* 128 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BlockFace getFacing() {
/* 139 */     byte data = (byte)(getData() & 0x3);
/* 140 */     switch (data) {
/*     */       case 0:
/* 142 */         return BlockFace.WEST;
/*     */       
/*     */       case 1:
/* 145 */         return BlockFace.NORTH;
/*     */       
/*     */       case 2:
/* 148 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/* 151 */         return BlockFace.SOUTH;
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Door clone() {
/* 158 */     return (Door)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Door.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */