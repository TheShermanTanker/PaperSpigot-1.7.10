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
/*     */ public class WorldGenNetherPiece3
/*     */   extends WorldGenNetherPiece
/*     */ {
/*     */   public WorldGenNetherPiece3() {}
/*     */   
/*     */   public WorldGenNetherPiece3(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 355 */     super(paramInt1);
/*     */     
/* 357 */     this.g = paramInt2;
/* 358 */     this.f = paramStructureBoundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 365 */     a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 3, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldGenNetherPiece3 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 371 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -3, 0, 5, 10, 19, paramInt4);
/*     */     
/* 373 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 374 */       return null;
/*     */     }
/*     */     
/* 377 */     return new WorldGenNetherPiece3(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 384 */     a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */     
/* 386 */     a(paramWorld, paramStructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR, Blocks.AIR, false);
/*     */ 
/*     */     
/* 389 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 390 */     a(paramWorld, paramStructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */ 
/*     */     
/* 393 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 394 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 395 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 396 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */     
/* 398 */     for (byte b = 0; b <= 4; b++) {
/* 399 */       for (byte b1 = 0; b1 <= 2; b1++) {
/* 400 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/* 401 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, 18 - b1, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 406 */     a(paramWorld, paramStructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 407 */     a(paramWorld, paramStructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 408 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 409 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 410 */     a(paramWorld, paramStructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 411 */     a(paramWorld, paramStructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 412 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/*     */     
/* 414 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPiece3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */