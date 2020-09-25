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
/*     */ public class WorldGenStrongholdStairs2
/*     */   extends WorldGenStrongholdPiece
/*     */ {
/*     */   private boolean a;
/*     */   
/*     */   public WorldGenStrongholdStairs2() {}
/*     */   
/*     */   public WorldGenStrongholdStairs2(int paramInt1, Random paramRandom, int paramInt2, int paramInt3) {
/* 451 */     super(paramInt1);
/*     */     
/* 453 */     this.a = true;
/* 454 */     this.g = paramRandom.nextInt(4);
/* 455 */     this.d = WorldGenStrongholdDoorType.a;
/*     */     
/* 457 */     switch (this.g) {
/*     */       case 0:
/*     */       case 2:
/* 460 */         this.f = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
/*     */         return;
/*     */     } 
/* 463 */     this.f = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenStrongholdStairs2(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 469 */     super(paramInt1);
/*     */     
/* 471 */     this.a = false;
/* 472 */     this.g = paramInt2;
/* 473 */     this.d = a(paramRandom);
/* 474 */     this.f = paramStructureBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 479 */     super.a(paramNBTTagCompound);
/* 480 */     paramNBTTagCompound.setBoolean("Source", this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 485 */     super.b(paramNBTTagCompound);
/* 486 */     this.a = paramNBTTagCompound.getBoolean("Source");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 492 */     if (this.a)
/*     */     {
/* 494 */       WorldGenStrongholdPieces.a(WorldGenStrongholdCrossing.class);
/*     */     }
/* 496 */     a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldGenStrongholdStairs2 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 501 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -7, 0, 5, 11, 5, paramInt4);
/*     */     
/* 503 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 504 */       return null;
/*     */     }
/*     */     
/* 507 */     return new WorldGenStrongholdStairs2(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 512 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 513 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 517 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 10, 4, true, paramRandom, WorldGenStrongholdPieces.c());
/*     */     
/* 519 */     a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 7, 0);
/*     */     
/* 521 */     a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 4);
/*     */ 
/*     */     
/* 524 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 6, 1, paramStructureBoundingBox);
/* 525 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 5, 1, paramStructureBoundingBox);
/* 526 */     a(paramWorld, Blocks.STEP, 0, 1, 6, 1, paramStructureBoundingBox);
/* 527 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 5, 2, paramStructureBoundingBox);
/* 528 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 4, 3, paramStructureBoundingBox);
/* 529 */     a(paramWorld, Blocks.STEP, 0, 1, 5, 3, paramStructureBoundingBox);
/* 530 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 4, 3, paramStructureBoundingBox);
/* 531 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 3, 3, paramStructureBoundingBox);
/* 532 */     a(paramWorld, Blocks.STEP, 0, 3, 4, 3, paramStructureBoundingBox);
/* 533 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 3, 2, paramStructureBoundingBox);
/* 534 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 2, 1, paramStructureBoundingBox);
/* 535 */     a(paramWorld, Blocks.STEP, 0, 3, 3, 1, paramStructureBoundingBox);
/* 536 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 2, 1, paramStructureBoundingBox);
/* 537 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 1, 1, paramStructureBoundingBox);
/* 538 */     a(paramWorld, Blocks.STEP, 0, 1, 2, 1, paramStructureBoundingBox);
/* 539 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 1, 2, paramStructureBoundingBox);
/* 540 */     a(paramWorld, Blocks.STEP, 0, 1, 1, 3, paramStructureBoundingBox);
/*     */     
/* 542 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdStairs2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */