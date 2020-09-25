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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenNetherPiece1
/*     */   extends WorldGenNetherPiece
/*     */ {
/*     */   public WorldGenNetherPiece1() {}
/*     */   
/*     */   public WorldGenNetherPiece1(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 515 */     super(paramInt1);
/*     */     
/* 517 */     this.g = paramInt2;
/* 518 */     this.f = paramStructureBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WorldGenNetherPiece1(Random paramRandom, int paramInt1, int paramInt2) {
/* 523 */     super(0);
/*     */     
/* 525 */     this.g = paramRandom.nextInt(4);
/*     */     
/* 527 */     switch (this.g) {
/*     */       case 0:
/*     */       case 2:
/* 530 */         this.f = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
/*     */         return;
/*     */     } 
/* 533 */     this.f = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 541 */     a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 8, 3, false);
/* 542 */     b((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
/* 543 */     c((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldGenNetherPiece1 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 549 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -8, -3, 0, 19, 10, 19, paramInt4);
/*     */     
/* 551 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 552 */       return null;
/*     */     }
/*     */     
/* 555 */     return new WorldGenNetherPiece1(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 562 */     a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 563 */     a(paramWorld, paramStructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */     
/* 565 */     a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR, Blocks.AIR, false);
/* 566 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR, Blocks.AIR, false);
/*     */     
/* 568 */     a(paramWorld, paramStructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 569 */     a(paramWorld, paramStructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 570 */     a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 571 */     a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 572 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 573 */     a(paramWorld, paramStructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 574 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 575 */     a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */ 
/*     */     
/* 578 */     a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 579 */     a(paramWorld, paramStructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 580 */     a(paramWorld, paramStructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 581 */     a(paramWorld, paramStructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false); byte b;
/* 582 */     for (b = 7; b <= 11; b++) {
/* 583 */       for (byte b1 = 0; b1 <= 2; b1++) {
/* 584 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/* 585 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, 18 - b1, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 589 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 590 */     a(paramWorld, paramStructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 591 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 592 */     a(paramWorld, paramStructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 593 */     for (b = 0; b <= 2; b++) {
/* 594 */       for (byte b1 = 7; b1 <= 11; b1++) {
/* 595 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/* 596 */         b(paramWorld, Blocks.NETHER_BRICK, 0, 18 - b, -1, b1, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 600 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPiece1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */