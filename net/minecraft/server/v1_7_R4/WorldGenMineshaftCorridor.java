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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenMineshaftCorridor
/*     */   extends StructurePiece
/*     */ {
/*     */   private boolean a;
/*     */   private boolean b;
/*     */   private boolean c;
/*     */   private int d;
/*     */   
/*     */   public WorldGenMineshaftCorridor() {}
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 210 */     paramNBTTagCompound.setBoolean("hr", this.a);
/* 211 */     paramNBTTagCompound.setBoolean("sc", this.b);
/* 212 */     paramNBTTagCompound.setBoolean("hps", this.c);
/* 213 */     paramNBTTagCompound.setInt("Num", this.d);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 218 */     this.a = paramNBTTagCompound.getBoolean("hr");
/* 219 */     this.b = paramNBTTagCompound.getBoolean("sc");
/* 220 */     this.c = paramNBTTagCompound.getBoolean("hps");
/* 221 */     this.d = paramNBTTagCompound.getInt("Num");
/*     */   }
/*     */   
/*     */   public WorldGenMineshaftCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 225 */     super(paramInt1);
/* 226 */     this.g = paramInt2;
/* 227 */     this.f = paramStructureBoundingBox;
/* 228 */     this.a = (paramRandom.nextInt(3) == 0);
/* 229 */     this.b = (!this.a && paramRandom.nextInt(23) == 0);
/*     */     
/* 231 */     if (this.g == 2 || this.g == 0) {
/* 232 */       this.d = paramStructureBoundingBox.d() / 5;
/*     */     } else {
/* 234 */       this.d = paramStructureBoundingBox.b() / 5;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 240 */     StructureBoundingBox structureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
/*     */     
/* 242 */     int i = paramRandom.nextInt(3) + 2;
/* 243 */     while (i > 0) {
/* 244 */       int j = i * 5;
/*     */       
/* 246 */       switch (paramInt4) {
/*     */         case 2:
/* 248 */           structureBoundingBox.d = paramInt1 + 2;
/* 249 */           structureBoundingBox.c = paramInt3 - j - 1;
/*     */           break;
/*     */         case 0:
/* 252 */           structureBoundingBox.d = paramInt1 + 2;
/* 253 */           structureBoundingBox.f = paramInt3 + j - 1;
/*     */           break;
/*     */         case 1:
/* 256 */           structureBoundingBox.a = paramInt1 - j - 1;
/* 257 */           structureBoundingBox.f = paramInt3 + 2;
/*     */           break;
/*     */         case 3:
/* 260 */           structureBoundingBox.d = paramInt1 + j - 1;
/* 261 */           structureBoundingBox.f = paramInt3 + 2;
/*     */           break;
/*     */       } 
/*     */       
/* 265 */       if (StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 266 */         i--;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (i > 0) {
/* 273 */       return structureBoundingBox;
/*     */     }
/*     */     
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 281 */     int i = d();
/* 282 */     int j = paramRandom.nextInt(4);
/* 283 */     switch (this.g) {
/*     */       case 2:
/* 285 */         if (j <= 1) {
/* 286 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, this.g, i); break;
/* 287 */         }  if (j == 2) {
/* 288 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, 1, i); break;
/*     */         } 
/* 290 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, 3, i);
/*     */         break;
/*     */       
/*     */       case 0:
/* 294 */         if (j <= 1) {
/* 295 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, this.g, i); break;
/* 296 */         }  if (j == 2) {
/* 297 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.f - 3, 1, i); break;
/*     */         } 
/* 299 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.f - 3, 3, i);
/*     */         break;
/*     */       
/*     */       case 1:
/* 303 */         if (j <= 1) {
/* 304 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, this.g, i); break;
/* 305 */         }  if (j == 2) {
/* 306 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, 2, i); break;
/*     */         } 
/* 308 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, 0, i);
/*     */         break;
/*     */       
/*     */       case 3:
/* 312 */         if (j <= 1) {
/* 313 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, this.g, i); break;
/* 314 */         }  if (j == 2) {
/* 315 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d - 3, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, 2, i); break;
/*     */         } 
/* 317 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d - 3, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, 0, i);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 323 */     if (i < 8) {
/* 324 */       if (this.g == 2 || this.g == 0) {
/* 325 */         for (int k = this.f.c + 3; k + 3 <= this.f.f; k += 5) {
/* 326 */           int m = paramRandom.nextInt(5);
/* 327 */           if (m == 0) {
/* 328 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, k, 1, i + 1);
/* 329 */           } else if (m == 1) {
/* 330 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, k, 3, i + 1);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 334 */         for (int k = this.f.a + 3; k + 3 <= this.f.d; k += 5) {
/* 335 */           int m = paramRandom.nextInt(5);
/* 336 */           if (m == 0) {
/* 337 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.f.b, this.f.c - 1, 2, i + 1);
/* 338 */           } else if (m == 1) {
/* 339 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.f.b, this.f.f + 1, 0, i + 1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt4) {
/* 348 */     int i = a(paramInt1, paramInt3);
/* 349 */     int j = a(paramInt2);
/* 350 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 352 */     if (paramStructureBoundingBox.b(i, j, k) && 
/* 353 */       paramWorld.getType(i, j, k).getMaterial() == Material.AIR) {
/* 354 */       boolean bool = paramRandom.nextBoolean() ? true : false;
/* 355 */       paramWorld.setTypeAndData(i, j, k, Blocks.RAILS, a(Blocks.RAILS, bool), 2);
/* 356 */       EntityMinecartChest entityMinecartChest = new EntityMinecartChest(paramWorld, (i + 0.5F), (j + 0.5F), (k + 0.5F));
/* 357 */       StructurePieceTreasure.a(paramRandom, paramArrayOfStructurePieceTreasure, entityMinecartChest, paramInt4);
/* 358 */       paramWorld.addEntity(entityMinecartChest);
/* 359 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 369 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 370 */       return false;
/*     */     }
/*     */     
/* 373 */     boolean bool1 = false;
/* 374 */     byte b1 = 2;
/* 375 */     boolean bool2 = false;
/* 376 */     byte b2 = 2;
/* 377 */     int i = this.d * 5 - 1;
/*     */ 
/*     */     
/* 380 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 2, 1, i, Blocks.AIR, Blocks.AIR, false);
/* 381 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.8F, 0, 2, 0, 2, 2, i, Blocks.AIR, Blocks.AIR, false);
/*     */     
/* 383 */     if (this.b) {
/* 384 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.6F, 0, 0, 0, 2, 1, i, Blocks.WEB, Blocks.AIR, false);
/*     */     }
/*     */     
/*     */     byte b3;
/* 388 */     for (b3 = 0; b3 < this.d; b3++) {
/*     */       
/* 390 */       int j = 2 + b3 * 5;
/*     */       
/* 392 */       a(paramWorld, paramStructureBoundingBox, 0, 0, j, 0, 1, j, Blocks.FENCE, Blocks.AIR, false);
/* 393 */       a(paramWorld, paramStructureBoundingBox, 2, 0, j, 2, 1, j, Blocks.FENCE, Blocks.AIR, false);
/* 394 */       if (paramRandom.nextInt(4) == 0) {
/* 395 */         a(paramWorld, paramStructureBoundingBox, 0, 2, j, 0, 2, j, Blocks.WOOD, Blocks.AIR, false);
/* 396 */         a(paramWorld, paramStructureBoundingBox, 2, 2, j, 2, 2, j, Blocks.WOOD, Blocks.AIR, false);
/*     */       } else {
/* 398 */         a(paramWorld, paramStructureBoundingBox, 0, 2, j, 2, 2, j, Blocks.WOOD, Blocks.AIR, false);
/*     */       } 
/* 400 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, j - 1, Blocks.WEB, 0);
/* 401 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, j - 1, Blocks.WEB, 0);
/* 402 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, j + 1, Blocks.WEB, 0);
/* 403 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, j + 1, Blocks.WEB, 0);
/* 404 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, j - 2, Blocks.WEB, 0);
/* 405 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, j - 2, Blocks.WEB, 0);
/* 406 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, j + 2, Blocks.WEB, 0);
/* 407 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, j + 2, Blocks.WEB, 0);
/*     */       
/* 409 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, j - 1, Blocks.TORCH, 0);
/* 410 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, j + 1, Blocks.TORCH, 0);
/*     */       
/* 412 */       if (paramRandom.nextInt(100) == 0) {
/* 413 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 0, j - 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
/*     */       }
/* 415 */       if (paramRandom.nextInt(100) == 0) {
/* 416 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0, 0, j + 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
/*     */       }
/* 418 */       if (this.b && !this.c) {
/* 419 */         int k = a(0), m = j - 1 + paramRandom.nextInt(3);
/* 420 */         int n = a(1, m);
/* 421 */         m = b(1, m);
/* 422 */         if (paramStructureBoundingBox.b(n, k, m)) {
/* 423 */           this.c = true;
/* 424 */           paramWorld.setTypeAndData(n, k, m, Blocks.MOB_SPAWNER, 0, 2);
/* 425 */           TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(n, k, m);
/* 426 */           if (tileEntityMobSpawner != null) tileEntityMobSpawner.getSpawner().setMobName("CaveSpider");
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 432 */     for (b3 = 0; b3 <= 2; b3++) {
/* 433 */       for (byte b = 0; b <= i; b++) {
/* 434 */         byte b4 = -1;
/* 435 */         Block block = a(paramWorld, b3, b4, b, paramStructureBoundingBox);
/* 436 */         if (block.getMaterial() == Material.AIR) {
/* 437 */           byte b5 = -1;
/* 438 */           a(paramWorld, Blocks.WOOD, 0, b3, b5, b, paramStructureBoundingBox);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 443 */     if (this.a) {
/* 444 */       for (b3 = 0; b3 <= i; b3++) {
/* 445 */         Block block = a(paramWorld, 1, -1, b3, paramStructureBoundingBox);
/* 446 */         if (block.getMaterial() != Material.AIR && block.j()) {
/* 447 */           a(paramWorld, paramStructureBoundingBox, paramRandom, 0.7F, 1, 0, b3, Blocks.RAILS, a(Blocks.RAILS, 0));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 452 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMineshaftCorridor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */