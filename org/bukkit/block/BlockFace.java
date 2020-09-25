/*     */ package org.bukkit.block;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum BlockFace
/*     */ {
/*   7 */   NORTH(0, 0, -1),
/*   8 */   EAST(1, 0, 0),
/*   9 */   SOUTH(0, 0, 1),
/*  10 */   WEST(-1, 0, 0),
/*  11 */   UP(0, 1, 0),
/*  12 */   DOWN(0, -1, 0),
/*  13 */   NORTH_EAST(NORTH, EAST),
/*  14 */   NORTH_WEST(NORTH, WEST),
/*  15 */   SOUTH_EAST(SOUTH, EAST),
/*  16 */   SOUTH_WEST(SOUTH, WEST),
/*  17 */   WEST_NORTH_WEST(WEST, NORTH_WEST),
/*  18 */   NORTH_NORTH_WEST(NORTH, NORTH_WEST),
/*  19 */   NORTH_NORTH_EAST(NORTH, NORTH_EAST),
/*  20 */   EAST_NORTH_EAST(EAST, NORTH_EAST),
/*  21 */   EAST_SOUTH_EAST(EAST, SOUTH_EAST),
/*  22 */   SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
/*  23 */   SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
/*  24 */   WEST_SOUTH_WEST(WEST, SOUTH_WEST),
/*  25 */   SELF(0, 0, 0);
/*     */   
/*     */   private final int modX;
/*     */   private final int modY;
/*     */   private final int modZ;
/*     */   
/*     */   BlockFace(int modX, int modY, int modZ) {
/*  32 */     this.modX = modX;
/*  33 */     this.modY = modY;
/*  34 */     this.modZ = modZ;
/*     */   }
/*     */   
/*     */   BlockFace(BlockFace face1, BlockFace face2) {
/*  38 */     this.modX = face1.getModX() + face2.getModX();
/*  39 */     this.modY = face1.getModY() + face2.getModY();
/*  40 */     this.modZ = face1.getModZ() + face2.getModZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModX() {
/*  49 */     return this.modX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModY() {
/*  58 */     return this.modY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModZ() {
/*  67 */     return this.modZ;
/*     */   }
/*     */   
/*     */   public BlockFace getOppositeFace() {
/*  71 */     switch (this) {
/*     */       case NORTH:
/*  73 */         return SOUTH;
/*     */       
/*     */       case SOUTH:
/*  76 */         return NORTH;
/*     */       
/*     */       case EAST:
/*  79 */         return WEST;
/*     */       
/*     */       case WEST:
/*  82 */         return EAST;
/*     */       
/*     */       case UP:
/*  85 */         return DOWN;
/*     */       
/*     */       case DOWN:
/*  88 */         return UP;
/*     */       
/*     */       case NORTH_EAST:
/*  91 */         return SOUTH_WEST;
/*     */       
/*     */       case NORTH_WEST:
/*  94 */         return SOUTH_EAST;
/*     */       
/*     */       case SOUTH_EAST:
/*  97 */         return NORTH_WEST;
/*     */       
/*     */       case SOUTH_WEST:
/* 100 */         return NORTH_EAST;
/*     */       
/*     */       case WEST_NORTH_WEST:
/* 103 */         return EAST_SOUTH_EAST;
/*     */       
/*     */       case NORTH_NORTH_WEST:
/* 106 */         return SOUTH_SOUTH_EAST;
/*     */       
/*     */       case NORTH_NORTH_EAST:
/* 109 */         return SOUTH_SOUTH_WEST;
/*     */       
/*     */       case EAST_NORTH_EAST:
/* 112 */         return WEST_SOUTH_WEST;
/*     */       
/*     */       case EAST_SOUTH_EAST:
/* 115 */         return WEST_NORTH_WEST;
/*     */       
/*     */       case SOUTH_SOUTH_EAST:
/* 118 */         return NORTH_NORTH_WEST;
/*     */       
/*     */       case SOUTH_SOUTH_WEST:
/* 121 */         return NORTH_NORTH_EAST;
/*     */       
/*     */       case WEST_SOUTH_WEST:
/* 124 */         return EAST_NORTH_EAST;
/*     */       
/*     */       case SELF:
/* 127 */         return SELF;
/*     */     } 
/*     */     
/* 130 */     return SELF;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\BlockFace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */