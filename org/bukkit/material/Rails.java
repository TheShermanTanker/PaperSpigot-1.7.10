/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rails
/*     */   extends MaterialData
/*     */ {
/*     */   public Rails() {
/*  12 */     super(Material.RAILS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Rails(int type) {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */   public Rails(Material type) {
/*  25 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Rails(int type, byte data) {
/*  34 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Rails(Material type, byte data) {
/*  43 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSlope() {
/*  50 */     byte d = getConvertedData();
/*     */     
/*  52 */     return (d == 2 || d == 3 || d == 4 || d == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCurve() {
/*  59 */     byte d = getConvertedData();
/*     */     
/*  61 */     return (d == 6 || d == 7 || d == 8 || d == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getDirection() {
/*  72 */     byte d = getConvertedData();
/*     */     
/*  74 */     switch (d) {
/*     */       
/*     */       default:
/*  77 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 1:
/*  80 */         return BlockFace.EAST;
/*     */       
/*     */       case 2:
/*  83 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/*  86 */         return BlockFace.WEST;
/*     */       
/*     */       case 4:
/*  89 */         return BlockFace.NORTH;
/*     */       
/*     */       case 5:
/*  92 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 6:
/*  95 */         return BlockFace.NORTH_WEST;
/*     */       
/*     */       case 7:
/*  98 */         return BlockFace.NORTH_EAST;
/*     */       
/*     */       case 8:
/* 101 */         return BlockFace.SOUTH_EAST;
/*     */       case 9:
/*     */         break;
/* 104 */     }  return BlockFace.SOUTH_WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return super.toString() + " facing " + getDirection() + (isCurve() ? " on a curve" : (isOnSlope() ? " on a slope" : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected byte getConvertedData() {
/* 123 */     return getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(BlockFace face, boolean isOnSlope) {
/* 137 */     switch (face) {
/*     */       case EAST:
/* 139 */         setData((byte)(isOnSlope ? 2 : 1));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 143 */         setData((byte)(isOnSlope ? 3 : 1));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 147 */         setData((byte)(isOnSlope ? 4 : 0));
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 151 */         setData((byte)(isOnSlope ? 5 : 0));
/*     */         break;
/*     */       
/*     */       case NORTH_WEST:
/* 155 */         setData((byte)6);
/*     */         break;
/*     */       
/*     */       case NORTH_EAST:
/* 159 */         setData((byte)7);
/*     */         break;
/*     */       
/*     */       case SOUTH_EAST:
/* 163 */         setData((byte)8);
/*     */         break;
/*     */       
/*     */       case SOUTH_WEST:
/* 167 */         setData((byte)9);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Rails clone() {
/* 174 */     return (Rails)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Rails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */