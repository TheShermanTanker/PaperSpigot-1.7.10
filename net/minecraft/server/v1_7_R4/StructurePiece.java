/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructurePiece
/*     */ {
/*     */   protected StructureBoundingBox f;
/*     */   protected int g;
/*     */   protected int h;
/*     */   
/*     */   public StructurePiece() {}
/*     */   
/*     */   protected StructurePiece(int paramInt) {
/*  52 */     this.h = paramInt;
/*  53 */     this.g = -1;
/*     */   }
/*     */   
/*     */   public NBTTagCompound b() {
/*  57 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */     
/*  59 */     nBTTagCompound.setString("id", WorldGenFactory.a(this));
/*  60 */     nBTTagCompound.set("BB", this.f.h());
/*  61 */     nBTTagCompound.setInt("O", this.g);
/*  62 */     nBTTagCompound.setInt("GD", this.h);
/*     */     
/*  64 */     a(nBTTagCompound);
/*     */     
/*  66 */     return nBTTagCompound;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void a(NBTTagCompound paramNBTTagCompound);
/*     */   
/*     */   public void a(World paramWorld, NBTTagCompound paramNBTTagCompound) {
/*  73 */     if (paramNBTTagCompound.hasKey("BB")) {
/*  74 */       this.f = new StructureBoundingBox(paramNBTTagCompound.getIntArray("BB"));
/*     */     }
/*  76 */     this.g = paramNBTTagCompound.getInt("O");
/*  77 */     this.h = paramNBTTagCompound.getInt("GD");
/*     */     
/*  79 */     b(paramNBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void b(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
/*     */   
/*     */   public abstract boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */   
/*     */   public StructureBoundingBox c() {
/*  91 */     return this.f;
/*     */   }
/*     */   
/*     */   public int d() {
/*  95 */     return this.h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructurePiece a(List paramList, StructureBoundingBox paramStructureBoundingBox) {
/* 106 */     for (StructurePiece structurePiece : paramList) {
/* 107 */       if (structurePiece.c() != null && structurePiece.c().a(paramStructureBoundingBox)) {
/* 108 */         return structurePiece;
/*     */       }
/*     */     } 
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public ChunkPosition a() {
/* 115 */     return new ChunkPosition(this.f.e(), this.f.f(), this.f.g());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox) {
/* 120 */     int i = Math.max(this.f.a - 1, paramStructureBoundingBox.a);
/* 121 */     int j = Math.max(this.f.b - 1, paramStructureBoundingBox.b);
/* 122 */     int k = Math.max(this.f.c - 1, paramStructureBoundingBox.c);
/* 123 */     int m = Math.min(this.f.d + 1, paramStructureBoundingBox.d);
/* 124 */     int n = Math.min(this.f.e + 1, paramStructureBoundingBox.e);
/* 125 */     int i1 = Math.min(this.f.f + 1, paramStructureBoundingBox.f);
/*     */     
/*     */     int i2;
/* 128 */     for (i2 = i; i2 <= m; i2++) {
/* 129 */       for (int i3 = k; i3 <= i1; i3++) {
/* 130 */         if (paramWorld.getType(i2, j, i3).getMaterial().isLiquid()) {
/* 131 */           return true;
/*     */         }
/* 133 */         if (paramWorld.getType(i2, n, i3).getMaterial().isLiquid()) {
/* 134 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     for (i2 = i; i2 <= m; i2++) {
/* 140 */       for (int i3 = j; i3 <= n; i3++) {
/* 141 */         if (paramWorld.getType(i2, i3, k).getMaterial().isLiquid()) {
/* 142 */           return true;
/*     */         }
/* 144 */         if (paramWorld.getType(i2, i3, i1).getMaterial().isLiquid()) {
/* 145 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     for (i2 = k; i2 <= i1; i2++) {
/* 151 */       for (int i3 = j; i3 <= n; i3++) {
/* 152 */         if (paramWorld.getType(i, i3, i2).getMaterial().isLiquid()) {
/* 153 */           return true;
/*     */         }
/* 155 */         if (paramWorld.getType(m, i3, i2).getMaterial().isLiquid()) {
/* 156 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 160 */     return false;
/*     */   }
/*     */   
/*     */   protected int a(int paramInt1, int paramInt2) {
/* 164 */     switch (this.g) {
/*     */       case 0:
/*     */       case 2:
/* 167 */         return this.f.a + paramInt1;
/*     */       case 1:
/* 169 */         return this.f.d - paramInt2;
/*     */       case 3:
/* 171 */         return this.f.a + paramInt2;
/*     */     } 
/* 173 */     return paramInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int a(int paramInt) {
/* 178 */     if (this.g == -1) {
/* 179 */       return paramInt;
/*     */     }
/* 181 */     return paramInt + this.f.b;
/*     */   }
/*     */   
/*     */   protected int b(int paramInt1, int paramInt2) {
/* 185 */     switch (this.g) {
/*     */       case 2:
/* 187 */         return this.f.f - paramInt2;
/*     */       case 0:
/* 189 */         return this.f.c + paramInt2;
/*     */       case 1:
/*     */       case 3:
/* 192 */         return this.f.c + paramInt1;
/*     */     } 
/* 194 */     return paramInt2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int a(Block paramBlock, int paramInt) {
/* 199 */     if (paramBlock == Blocks.RAILS) {
/* 200 */       if (this.g == 1 || this.g == 3) {
/* 201 */         if (paramInt == 1) {
/* 202 */           return 0;
/*     */         }
/* 204 */         return 1;
/*     */       }
/*     */     
/* 207 */     } else if (paramBlock == Blocks.WOODEN_DOOR || paramBlock == Blocks.IRON_DOOR_BLOCK) {
/* 208 */       if (this.g == 0) {
/* 209 */         if (paramInt == 0) {
/* 210 */           return 2;
/*     */         }
/* 212 */         if (paramInt == 2)
/* 213 */           return 0; 
/*     */       } else {
/* 215 */         if (this.g == 1)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 220 */           return paramInt + 1 & 0x3; } 
/* 221 */         if (this.g == 3)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 226 */           return paramInt + 3 & 0x3; } 
/*     */       } 
/* 228 */     } else if (paramBlock == Blocks.COBBLESTONE_STAIRS || paramBlock == Blocks.WOOD_STAIRS || paramBlock == Blocks.NETHER_BRICK_STAIRS || paramBlock == Blocks.STONE_STAIRS || paramBlock == Blocks.SANDSTONE_STAIRS) {
/* 229 */       if (this.g == 0) {
/* 230 */         if (paramInt == 2) {
/* 231 */           return 3;
/*     */         }
/* 233 */         if (paramInt == 3) {
/* 234 */           return 2;
/*     */         }
/* 236 */       } else if (this.g == 1) {
/* 237 */         if (paramInt == 0) {
/* 238 */           return 2;
/*     */         }
/* 240 */         if (paramInt == 1) {
/* 241 */           return 3;
/*     */         }
/* 243 */         if (paramInt == 2) {
/* 244 */           return 0;
/*     */         }
/* 246 */         if (paramInt == 3) {
/* 247 */           return 1;
/*     */         }
/* 249 */       } else if (this.g == 3) {
/* 250 */         if (paramInt == 0) {
/* 251 */           return 2;
/*     */         }
/* 253 */         if (paramInt == 1) {
/* 254 */           return 3;
/*     */         }
/* 256 */         if (paramInt == 2) {
/* 257 */           return 1;
/*     */         }
/* 259 */         if (paramInt == 3) {
/* 260 */           return 0;
/*     */         }
/*     */       } 
/* 263 */     } else if (paramBlock == Blocks.LADDER) {
/* 264 */       if (this.g == 0) {
/* 265 */         if (paramInt == 2) {
/* 266 */           return 3;
/*     */         }
/* 268 */         if (paramInt == 3) {
/* 269 */           return 2;
/*     */         }
/* 271 */       } else if (this.g == 1) {
/* 272 */         if (paramInt == 2) {
/* 273 */           return 4;
/*     */         }
/* 275 */         if (paramInt == 3) {
/* 276 */           return 5;
/*     */         }
/* 278 */         if (paramInt == 4) {
/* 279 */           return 2;
/*     */         }
/* 281 */         if (paramInt == 5) {
/* 282 */           return 3;
/*     */         }
/* 284 */       } else if (this.g == 3) {
/* 285 */         if (paramInt == 2) {
/* 286 */           return 5;
/*     */         }
/* 288 */         if (paramInt == 3) {
/* 289 */           return 4;
/*     */         }
/* 291 */         if (paramInt == 4) {
/* 292 */           return 2;
/*     */         }
/* 294 */         if (paramInt == 5) {
/* 295 */           return 3;
/*     */         }
/*     */       }
/*     */     
/* 299 */     } else if (paramBlock == Blocks.STONE_BUTTON) {
/* 300 */       if (this.g == 0) {
/* 301 */         if (paramInt == 3) {
/* 302 */           return 4;
/*     */         }
/* 304 */         if (paramInt == 4) {
/* 305 */           return 3;
/*     */         }
/* 307 */       } else if (this.g == 1) {
/* 308 */         if (paramInt == 3) {
/* 309 */           return 1;
/*     */         }
/* 311 */         if (paramInt == 4) {
/* 312 */           return 2;
/*     */         }
/* 314 */         if (paramInt == 2) {
/* 315 */           return 3;
/*     */         }
/* 317 */         if (paramInt == 1) {
/* 318 */           return 4;
/*     */         }
/* 320 */       } else if (this.g == 3) {
/* 321 */         if (paramInt == 3) {
/* 322 */           return 2;
/*     */         }
/* 324 */         if (paramInt == 4) {
/* 325 */           return 1;
/*     */         }
/* 327 */         if (paramInt == 2) {
/* 328 */           return 3;
/*     */         }
/* 330 */         if (paramInt == 1) {
/* 331 */           return 4;
/*     */         }
/*     */       } 
/* 334 */     } else if (paramBlock == Blocks.TRIPWIRE_SOURCE || paramBlock instanceof BlockDirectional) {
/* 335 */       if (this.g == 0) {
/* 336 */         if (paramInt == 0 || paramInt == 2) {
/* 337 */           return Direction.f[paramInt];
/*     */         }
/* 339 */       } else if (this.g == 1) {
/* 340 */         if (paramInt == 2) {
/* 341 */           return 1;
/*     */         }
/* 343 */         if (paramInt == 0) {
/* 344 */           return 3;
/*     */         }
/* 346 */         if (paramInt == 1) {
/* 347 */           return 2;
/*     */         }
/* 349 */         if (paramInt == 3) {
/* 350 */           return 0;
/*     */         }
/* 352 */       } else if (this.g == 3) {
/* 353 */         if (paramInt == 2) {
/* 354 */           return 3;
/*     */         }
/* 356 */         if (paramInt == 0) {
/* 357 */           return 1;
/*     */         }
/* 359 */         if (paramInt == 1) {
/* 360 */           return 2;
/*     */         }
/* 362 */         if (paramInt == 3) {
/* 363 */           return 0;
/*     */         }
/*     */       } 
/* 366 */     } else if (paramBlock == Blocks.PISTON || paramBlock == Blocks.PISTON_STICKY || paramBlock == Blocks.LEVER || paramBlock == Blocks.DISPENSER) {
/* 367 */       if (this.g == 0) {
/* 368 */         if (paramInt == 2 || paramInt == 3) {
/* 369 */           return Facing.OPPOSITE_FACING[paramInt];
/*     */         }
/* 371 */       } else if (this.g == 1) {
/* 372 */         if (paramInt == 2) {
/* 373 */           return 4;
/*     */         }
/* 375 */         if (paramInt == 3) {
/* 376 */           return 5;
/*     */         }
/* 378 */         if (paramInt == 4) {
/* 379 */           return 2;
/*     */         }
/* 381 */         if (paramInt == 5) {
/* 382 */           return 3;
/*     */         }
/* 384 */       } else if (this.g == 3) {
/* 385 */         if (paramInt == 2) {
/* 386 */           return 5;
/*     */         }
/* 388 */         if (paramInt == 3) {
/* 389 */           return 4;
/*     */         }
/* 391 */         if (paramInt == 4) {
/* 392 */           return 2;
/*     */         }
/* 394 */         if (paramInt == 5) {
/* 395 */           return 3;
/*     */         }
/*     */       } 
/*     */     } 
/* 399 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4, StructureBoundingBox paramStructureBoundingBox) {
/* 403 */     int i = a(paramInt2, paramInt4);
/* 404 */     int j = a(paramInt3);
/* 405 */     int k = b(paramInt2, paramInt4);
/*     */     
/* 407 */     if (!paramStructureBoundingBox.b(i, j, k)) {
/*     */       return;
/*     */     }
/*     */     
/* 411 */     paramWorld.setTypeAndData(i, j, k, paramBlock, paramInt1, 2);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected Block a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox) {
/* 428 */     int i = a(paramInt1, paramInt3);
/* 429 */     int j = a(paramInt2);
/* 430 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 432 */     if (!paramStructureBoundingBox.b(i, j, k)) {
/* 433 */       return Blocks.AIR;
/*     */     }
/*     */     
/* 436 */     return paramWorld.getType(i, j, k);
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 440 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 441 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 442 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 443 */           a(paramWorld, Blocks.AIR, 0, j, i, k, paramStructureBoundingBox);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Block paramBlock1, Block paramBlock2, boolean paramBoolean) {
/* 450 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 451 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 452 */         for (int k = paramInt3; k <= paramInt6; k++) {
/*     */           
/* 454 */           if (!paramBoolean || a(paramWorld, j, i, k, paramStructureBoundingBox).getMaterial() != Material.AIR)
/*     */           {
/*     */             
/* 457 */             if (i == paramInt2 || i == paramInt5 || j == paramInt1 || j == paramInt4 || k == paramInt3 || k == paramInt6) {
/* 458 */               a(paramWorld, paramBlock1, 0, j, i, k, paramStructureBoundingBox);
/*     */             } else {
/* 460 */               a(paramWorld, paramBlock2, 0, j, i, k, paramStructureBoundingBox);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Block paramBlock1, int paramInt7, Block paramBlock2, int paramInt8, boolean paramBoolean) {
/* 469 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 470 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 471 */         for (int k = paramInt3; k <= paramInt6; k++) {
/*     */           
/* 473 */           if (!paramBoolean || a(paramWorld, j, i, k, paramStructureBoundingBox).getMaterial() != Material.AIR)
/*     */           {
/*     */             
/* 476 */             if (i == paramInt2 || i == paramInt5 || j == paramInt1 || j == paramInt4 || k == paramInt3 || k == paramInt6) {
/* 477 */               a(paramWorld, paramBlock1, paramInt7, j, i, k, paramStructureBoundingBox);
/*     */             } else {
/* 479 */               a(paramWorld, paramBlock2, paramInt8, j, i, k, paramStructureBoundingBox);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, Random paramRandom, StructurePieceBlockSelector paramStructurePieceBlockSelector) {
/* 492 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 493 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 494 */         for (int k = paramInt3; k <= paramInt6; k++) {
/*     */           
/* 496 */           if (!paramBoolean || a(paramWorld, j, i, k, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/*     */ 
/*     */             
/* 499 */             paramStructurePieceBlockSelector.a(paramRandom, j, i, k, (i == paramInt2 || i == paramInt5 || j == paramInt1 || j == paramInt4 || k == paramInt3 || k == paramInt6));
/* 500 */             a(paramWorld, paramStructurePieceBlockSelector.a(), paramStructurePieceBlockSelector.b(), j, i, k, paramStructureBoundingBox);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Block paramBlock1, Block paramBlock2, boolean paramBoolean) {
/* 512 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 513 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 514 */         for (int k = paramInt3; k <= paramInt6; k++) {
/*     */           
/* 516 */           if (paramRandom.nextFloat() <= paramFloat)
/*     */           {
/*     */             
/* 519 */             if (!paramBoolean || a(paramWorld, j, i, k, paramStructureBoundingBox).getMaterial() != Material.AIR)
/*     */             {
/*     */               
/* 522 */               if (i == paramInt2 || i == paramInt5 || j == paramInt1 || j == paramInt4 || k == paramInt3 || k == paramInt6) {
/* 523 */                 a(paramWorld, paramBlock1, 0, j, i, k, paramStructureBoundingBox);
/*     */               } else {
/* 525 */                 a(paramWorld, paramBlock2, 0, j, i, k, paramStructureBoundingBox);
/*     */               }  } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 534 */     if (paramRandom.nextFloat() < paramFloat) {
/* 535 */       a(paramWorld, paramBlock, paramInt4, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Block paramBlock, boolean paramBoolean) {
/* 540 */     float f1 = (paramInt4 - paramInt1 + 1);
/* 541 */     float f2 = (paramInt5 - paramInt2 + 1);
/* 542 */     float f3 = (paramInt6 - paramInt3 + 1);
/* 543 */     float f4 = paramInt1 + f1 / 2.0F;
/* 544 */     float f5 = paramInt3 + f3 / 2.0F;
/*     */     
/* 546 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 547 */       float f = (i - paramInt2) / f2;
/*     */       
/* 549 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 550 */         float f6 = (j - f4) / f1 * 0.5F;
/*     */         
/* 552 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 553 */           float f7 = (k - f5) / f3 * 0.5F;
/*     */           
/* 555 */           if (!paramBoolean || a(paramWorld, j, i, k, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/*     */ 
/*     */ 
/*     */             
/* 559 */             float f8 = f6 * f6 + f * f + f7 * f7;
/*     */             
/* 561 */             if (f8 <= 1.05F) {
/* 562 */               a(paramWorld, paramBlock, 0, j, i, k, paramStructureBoundingBox);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox) {
/* 572 */     int i = a(paramInt1, paramInt3);
/* 573 */     int j = a(paramInt2);
/* 574 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 576 */     if (!paramStructureBoundingBox.b(i, j, k)) {
/*     */       return;
/*     */     }
/*     */     
/* 580 */     while (!paramWorld.isEmpty(i, j, k) && j < 255) {
/* 581 */       paramWorld.setTypeAndData(i, j, k, Blocks.AIR, 0, 2);
/* 582 */       j++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4, StructureBoundingBox paramStructureBoundingBox) {
/* 588 */     int i = a(paramInt2, paramInt4);
/* 589 */     int j = a(paramInt3);
/* 590 */     int k = b(paramInt2, paramInt4);
/*     */     
/* 592 */     if (!paramStructureBoundingBox.b(i, j, k)) {
/*     */       return;
/*     */     }
/*     */     
/* 596 */     while ((paramWorld.isEmpty(i, j, k) || paramWorld.getType(i, j, k).getMaterial().isLiquid()) && j > 1) {
/* 597 */       paramWorld.setTypeAndData(i, j, k, paramBlock, paramInt1, 2);
/* 598 */       j--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt4) {
/* 604 */     int i = a(paramInt1, paramInt3);
/* 605 */     int j = a(paramInt2);
/* 606 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 608 */     if (paramStructureBoundingBox.b(i, j, k) && 
/* 609 */       paramWorld.getType(i, j, k) != Blocks.CHEST) {
/* 610 */       paramWorld.setTypeAndData(i, j, k, Blocks.CHEST, 0, 2);
/* 611 */       TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(i, j, k);
/* 612 */       if (tileEntityChest != null) StructurePieceTreasure.a(paramRandom, paramArrayOfStructurePieceTreasure, tileEntityChest, paramInt4); 
/* 613 */       return true;
/*     */     } 
/*     */     
/* 616 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt5) {
/* 621 */     int i = a(paramInt1, paramInt3);
/* 622 */     int j = a(paramInt2);
/* 623 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 625 */     if (paramStructureBoundingBox.b(i, j, k) && 
/* 626 */       paramWorld.getType(i, j, k) != Blocks.DISPENSER) {
/* 627 */       paramWorld.setTypeAndData(i, j, k, Blocks.DISPENSER, a(Blocks.DISPENSER, paramInt4), 2);
/* 628 */       TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)paramWorld.getTileEntity(i, j, k);
/* 629 */       if (tileEntityDispenser != null) StructurePieceTreasure.a(paramRandom, paramArrayOfStructurePieceTreasure, tileEntityDispenser, paramInt5); 
/* 630 */       return true;
/*     */     } 
/*     */     
/* 633 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 638 */     int i = a(paramInt1, paramInt3);
/* 639 */     int j = a(paramInt2);
/* 640 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 642 */     if (paramStructureBoundingBox.b(i, j, k))
/* 643 */       ItemDoor.place(paramWorld, i, j, k, paramInt4, Blocks.WOODEN_DOOR); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StructurePiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */