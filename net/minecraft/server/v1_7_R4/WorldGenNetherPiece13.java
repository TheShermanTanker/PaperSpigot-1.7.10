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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenNetherPiece13
/*     */   extends WorldGenNetherPiece
/*     */ {
/*     */   public WorldGenNetherPiece13() {}
/*     */   
/*     */   public WorldGenNetherPiece13(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 616 */     super(paramInt1);
/*     */     
/* 618 */     this.g = paramInt2;
/* 619 */     this.f = paramStructureBoundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 626 */     a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 2, 0, false);
/* 627 */     b((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
/* 628 */     c((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldGenNetherPiece13 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 634 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 9, 7, paramInt4);
/*     */     
/* 636 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 637 */       return null;
/*     */     }
/*     */     
/* 640 */     return new WorldGenNetherPiece13(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 647 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */     
/* 649 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR, Blocks.AIR, false);
/*     */ 
/*     */     
/* 652 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 653 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 654 */     a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 655 */     a(paramWorld, paramStructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 656 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 657 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 658 */     a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 659 */     a(paramWorld, paramStructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*     */ 
/*     */     
/* 662 */     a(paramWorld, paramStructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 663 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 664 */     a(paramWorld, paramStructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 665 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 666 */     a(paramWorld, paramStructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 667 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 668 */     a(paramWorld, paramStructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 669 */     a(paramWorld, paramStructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/*     */ 
/*     */     
/* 672 */     for (byte b = 0; b <= 6; b++) {
/* 673 */       for (byte b1 = 0; b1 <= 6; b1++) {
/* 674 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/*     */       }
/*     */     } 
/*     */     
/* 678 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPiece13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */