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
/*     */ public class WorldGenVillageWell
/*     */   extends WorldGenVillagePiece
/*     */ {
/*     */   public WorldGenVillageWell() {}
/*     */   
/*     */   public WorldGenVillageWell(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, int paramInt2, int paramInt3) {
/* 409 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*     */     
/* 411 */     this.g = paramRandom.nextInt(4);
/*     */     
/* 413 */     switch (this.g) {
/*     */       case 0:
/*     */       case 2:
/* 416 */         this.f = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 6 - 1, 78, paramInt3 + 6 - 1);
/*     */         return;
/*     */     } 
/* 419 */     this.f = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 6 - 1, 78, paramInt3 + 6 - 1);
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
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 434 */     WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.e - 4, this.f.c + 1, 1, d());
/* 435 */     WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.e - 4, this.f.c + 1, 3, d());
/* 436 */     WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.e - 4, this.f.c - 1, 2, d());
/* 437 */     WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.e - 4, this.f.f + 1, 0, d());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 444 */     if (this.k < 0) {
/* 445 */       this.k = b(paramWorld, paramStructureBoundingBox);
/* 446 */       if (this.k < 0) {
/* 447 */         return true;
/*     */       }
/* 449 */       this.f.a(0, this.k - this.f.e + 3, 0);
/*     */     } 
/*     */     
/* 452 */     a(paramWorld, paramStructureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.COBBLESTONE, Blocks.WATER, false);
/* 453 */     a(paramWorld, Blocks.AIR, 0, 2, 12, 2, paramStructureBoundingBox);
/* 454 */     a(paramWorld, Blocks.AIR, 0, 3, 12, 2, paramStructureBoundingBox);
/* 455 */     a(paramWorld, Blocks.AIR, 0, 2, 12, 3, paramStructureBoundingBox);
/* 456 */     a(paramWorld, Blocks.AIR, 0, 3, 12, 3, paramStructureBoundingBox);
/*     */     
/* 458 */     a(paramWorld, Blocks.FENCE, 0, 1, 13, 1, paramStructureBoundingBox);
/* 459 */     a(paramWorld, Blocks.FENCE, 0, 1, 14, 1, paramStructureBoundingBox);
/* 460 */     a(paramWorld, Blocks.FENCE, 0, 4, 13, 1, paramStructureBoundingBox);
/* 461 */     a(paramWorld, Blocks.FENCE, 0, 4, 14, 1, paramStructureBoundingBox);
/* 462 */     a(paramWorld, Blocks.FENCE, 0, 1, 13, 4, paramStructureBoundingBox);
/* 463 */     a(paramWorld, Blocks.FENCE, 0, 1, 14, 4, paramStructureBoundingBox);
/* 464 */     a(paramWorld, Blocks.FENCE, 0, 4, 13, 4, paramStructureBoundingBox);
/* 465 */     a(paramWorld, Blocks.FENCE, 0, 4, 14, 4, paramStructureBoundingBox);
/* 466 */     a(paramWorld, paramStructureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 468 */     for (byte b = 0; b <= 5; b++) {
/* 469 */       for (byte b1 = 0; b1 <= 5; b1++) {
/*     */         
/* 471 */         if (b1 == 0 || b1 == 5 || b == 0 || b == 5) {
/*     */ 
/*     */           
/* 474 */           a(paramWorld, Blocks.GRAVEL, 0, b1, 11, b, paramStructureBoundingBox);
/* 475 */           b(paramWorld, b1, 12, b, paramStructureBoundingBox);
/*     */         } 
/*     */       } 
/*     */     } 
/* 479 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageWell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */