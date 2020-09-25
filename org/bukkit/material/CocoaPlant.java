/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class CocoaPlant
/*     */   extends MaterialData
/*     */   implements Directional, Attachable
/*     */ {
/*     */   public enum CocoaPlantSize
/*     */   {
/*  12 */     SMALL,
/*  13 */     MEDIUM,
/*  14 */     LARGE;
/*     */   }
/*     */   
/*     */   public CocoaPlant() {
/*  18 */     super(Material.COCOA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CocoaPlant(int type) {
/*  27 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CocoaPlant(int type, byte data) {
/*  36 */     super(type, data);
/*     */   }
/*     */   
/*     */   public CocoaPlant(CocoaPlantSize sz) {
/*  40 */     this();
/*  41 */     setSize(sz);
/*     */   }
/*     */   
/*     */   public CocoaPlant(CocoaPlantSize sz, BlockFace dir) {
/*  45 */     this();
/*  46 */     setSize(sz);
/*  47 */     setFacingDirection(dir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CocoaPlantSize getSize() {
/*  56 */     switch (getData() & 0xC) {
/*     */       case 0:
/*  58 */         return CocoaPlantSize.SMALL;
/*     */       case 4:
/*  60 */         return CocoaPlantSize.MEDIUM;
/*     */     } 
/*  62 */     return CocoaPlantSize.LARGE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(CocoaPlantSize sz) {
/*  72 */     int dat = getData() & 0x3;
/*  73 */     switch (sz) {
/*     */ 
/*     */       
/*     */       case WEST:
/*  77 */         dat |= 0x4;
/*     */         break;
/*     */       case NORTH:
/*  80 */         dat |= 0x8;
/*     */         break;
/*     */     } 
/*  83 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  87 */     return getFacing().getOppositeFace();
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  91 */     int dat = getData() & 0xC;
/*  92 */     switch (face) {
/*     */ 
/*     */ 
/*     */       
/*     */       case WEST:
/*  97 */         dat |= 0x1;
/*     */         break;
/*     */       case NORTH:
/* 100 */         dat |= 0x2;
/*     */         break;
/*     */       case EAST:
/* 103 */         dat |= 0x3;
/*     */         break;
/*     */     } 
/* 106 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/* 110 */     switch (getData() & 0x3) {
/*     */       case 0:
/* 112 */         return BlockFace.SOUTH;
/*     */       case 1:
/* 114 */         return BlockFace.WEST;
/*     */       case 2:
/* 116 */         return BlockFace.NORTH;
/*     */       case 3:
/* 118 */         return BlockFace.EAST;
/*     */     } 
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public CocoaPlant clone() {
/* 125 */     return (CocoaPlant)super.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return super.toString() + " facing " + getFacing() + " " + getSize();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\CocoaPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */