/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bed
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   public Bed() {
/*  15 */     super(Material.BED_BLOCK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bed(BlockFace direction) {
/*  24 */     this();
/*  25 */     setFacingDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Bed(int type) {
/*  34 */     super(type);
/*     */   }
/*     */   
/*     */   public Bed(Material type) {
/*  38 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Bed(int type, byte data) {
/*  47 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Bed(Material type, byte data) {
/*  56 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHeadOfBed() {
/*  65 */     return ((getData() & 0x8) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeadOfBed(boolean isHeadOfBed) {
/*  74 */     setData((byte)(isHeadOfBed ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  84 */     switch (face) {
/*     */       case SOUTH:
/*  86 */         data = 0;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  90 */         data = 1;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  94 */         data = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  99 */         data = 3;
/*     */         break;
/*     */     } 
/* 102 */     if (isHeadOfBed()) {
/* 103 */       data = (byte)(data | 0x8);
/*     */     }
/*     */     
/* 106 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/* 115 */     byte data = (byte)(getData() & 0x7);
/*     */     
/* 117 */     switch (data) {
/*     */       case 0:
/* 119 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 1:
/* 122 */         return BlockFace.WEST;
/*     */       
/*     */       case 2:
/* 125 */         return BlockFace.NORTH;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return BlockFace.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     return (isHeadOfBed() ? "HEAD" : "FOOT") + " of " + super.toString() + " facing " + getFacing();
/*     */   }
/*     */ 
/*     */   
/*     */   public Bed clone() {
/* 140 */     return (Bed)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Bed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */