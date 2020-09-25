/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Sign
/*     */   extends MaterialData
/*     */   implements Attachable
/*     */ {
/*     */   public Sign() {
/*  11 */     super(Material.SIGN_POST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Sign(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Sign(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Sign(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Sign(Material type, byte data) {
/*  42 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWallSign() {
/*  52 */     return (getItemType() == Material.WALL_SIGN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  61 */     if (isWallSign()) {
/*  62 */       byte data = getData();
/*     */       
/*  64 */       switch (data) {
/*     */         case 2:
/*  66 */           return BlockFace.SOUTH;
/*     */         
/*     */         case 3:
/*  69 */           return BlockFace.NORTH;
/*     */         
/*     */         case 4:
/*  72 */           return BlockFace.EAST;
/*     */         
/*     */         case 5:
/*  75 */           return BlockFace.WEST;
/*     */       } 
/*     */       
/*  78 */       return null;
/*     */     } 
/*  80 */     return BlockFace.DOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getFacing() {
/*  90 */     byte data = getData();
/*     */     
/*  92 */     if (!isWallSign()) {
/*  93 */       switch (data) {
/*     */         case 0:
/*  95 */           return BlockFace.SOUTH;
/*     */         
/*     */         case 1:
/*  98 */           return BlockFace.SOUTH_SOUTH_WEST;
/*     */         
/*     */         case 2:
/* 101 */           return BlockFace.SOUTH_WEST;
/*     */         
/*     */         case 3:
/* 104 */           return BlockFace.WEST_SOUTH_WEST;
/*     */         
/*     */         case 4:
/* 107 */           return BlockFace.WEST;
/*     */         
/*     */         case 5:
/* 110 */           return BlockFace.WEST_NORTH_WEST;
/*     */         
/*     */         case 6:
/* 113 */           return BlockFace.NORTH_WEST;
/*     */         
/*     */         case 7:
/* 116 */           return BlockFace.NORTH_NORTH_WEST;
/*     */         
/*     */         case 8:
/* 119 */           return BlockFace.NORTH;
/*     */         
/*     */         case 9:
/* 122 */           return BlockFace.NORTH_NORTH_EAST;
/*     */         
/*     */         case 10:
/* 125 */           return BlockFace.NORTH_EAST;
/*     */         
/*     */         case 11:
/* 128 */           return BlockFace.EAST_NORTH_EAST;
/*     */         
/*     */         case 12:
/* 131 */           return BlockFace.EAST;
/*     */         
/*     */         case 13:
/* 134 */           return BlockFace.EAST_SOUTH_EAST;
/*     */         
/*     */         case 14:
/* 137 */           return BlockFace.SOUTH_EAST;
/*     */         
/*     */         case 15:
/* 140 */           return BlockFace.SOUTH_SOUTH_EAST;
/*     */       } 
/*     */       
/* 143 */       return null;
/*     */     } 
/* 145 */     return getAttachedFace().getOppositeFace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/* 152 */     if (isWallSign()) {
/* 153 */       switch (face) {
/*     */         case NORTH:
/* 155 */           data = 2;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 159 */           data = 3;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 163 */           data = 4;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 168 */           data = 5; break;
/*     */       } 
/*     */     } else {
/* 171 */       switch (face) {
/*     */         case SOUTH:
/* 173 */           data = 0;
/*     */           break;
/*     */         
/*     */         case SOUTH_SOUTH_WEST:
/* 177 */           data = 1;
/*     */           break;
/*     */         
/*     */         case SOUTH_WEST:
/* 181 */           data = 2;
/*     */           break;
/*     */         
/*     */         case WEST_SOUTH_WEST:
/* 185 */           data = 3;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 189 */           data = 4;
/*     */           break;
/*     */         
/*     */         case WEST_NORTH_WEST:
/* 193 */           data = 5;
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 197 */           data = 6;
/*     */           break;
/*     */         
/*     */         case NORTH_NORTH_WEST:
/* 201 */           data = 7;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 205 */           data = 8;
/*     */           break;
/*     */         
/*     */         case NORTH_NORTH_EAST:
/* 209 */           data = 9;
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 213 */           data = 10;
/*     */           break;
/*     */         
/*     */         case EAST_NORTH_EAST:
/* 217 */           data = 11;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 221 */           data = 12;
/*     */           break;
/*     */         
/*     */         case EAST_SOUTH_EAST:
/* 225 */           data = 13;
/*     */           break;
/*     */         
/*     */         case SOUTH_SOUTH_EAST:
/* 229 */           data = 15;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 234 */           data = 14;
/*     */           break;
/*     */       } 
/*     */     } 
/* 238 */     setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 243 */     return super.toString() + " facing " + getFacing();
/*     */   }
/*     */ 
/*     */   
/*     */   public Sign clone() {
/* 248 */     return (Sign)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Sign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */